#include "contiki.h"
#include "lib/list.h"
#include "lib/memb.h"
#include "lib/random.h"
#include "sys/etimer.h" // The etimer
#include "net/rime/rime.h"
#include "dev/button-sensor.h"
#include "node-id.h"
#include <stdio.h>
#include "dev/watchdog.h"

/* This #define defines the maximum amount of neighbors we can remember. */
#define MAX_NEIGHBORS 16

/* This defines the minimum number for the RSSI. If a nodes RSSI is below this, we ignore it */
#define MIN_RSSI -95

//flag to send broadcast message
static int sendBroadcast = 0;
static int restartField = 0;
static int bestHopIHave = MAX_NEIGHBORS+1;
static struct neighbor *bestNeighbourIHave;

/* This is the structure of broadcast messages. */
struct broadcast_message {
	uint8_t seqno;
	int restartNo;
};

/* This is the structure of unicast ping messages. */
struct unicast_message {
	int data;
};

/* This structure holds information about neighbors. */
struct neighbor {
	/* The ->next pointer is needed since we are placing these on a
	 Contiki list. */
	struct neighbor *next;

	/* The ->addr field holds the Rime address of the neighbor. */
	linkaddr_t addr;

	/* The ->last_rssi and ->last_lqi fields hold the Received Signal
	 Strength Indicator (RSSI) and CC2420 Link Quality Indicator (LQI)
	 values that are received for the incoming broadcast packets. */
	uint16_t last_rssi, last_lqi;

	/* Each broadcast packet contains a sequence number (seqno). The
	 ->last_seqno field holds the last sequenuce number we saw from
	 this neighbor. */
	uint8_t last_seqno;

	/* The ->avg_gap contains the average seqno gap that we have seen
	 from this neighbor. */
	uint32_t avg_seqno_gap;
};

/* This MEMB() definition defines a memory pool from which we allocate
   neighbor entries. */
MEMB(neighbors_memb, struct neighbor, MAX_NEIGHBORS);

/* The neighbors_list is a Contiki list that holds the neighbors we
   have seen thus far. */
LIST(neighbors_list);

/* These hold the broadcast and unicast structures, respectively. */
static struct broadcast_conn broadcast;
static struct unicast_conn unicast;

/* These two defines are used for computing the moving average for the
   broadcast sequence number gaps. */
#define SEQNO_EWMA_UNITY 0x100
#define SEQNO_EWMA_ALPHA 0x040

/*---------------------------------------------------------------------------*/
/* We first declare our two processes. */
PROCESS(broadcast_process, "Broadcast process");
PROCESS(button_process, "Button process");

/* The AUTOSTART_PROCESSES() definition specifices what processes to
   start when this module is loaded. We put both our processes
   there. */
AUTOSTART_PROCESSES(&broadcast_process, &button_process);
/*---------------------------------------------------------------------------*/
/* This function is called whenever a broadcast message is received. */
static void
broadcast_recv(struct broadcast_conn *c, const linkaddr_t *from)
{
	struct neighbor *n;
	struct neighbor *nextN;
	struct broadcast_message *m;
	uint8_t seqno_gap;
	static int i;

	/* The packetbuf_dataptr() returns a pointer to the first data byte
	in the received packet. */
	m = packetbuf_dataptr();

	printf("%d) Braodcast gotten, sequence number = %d, restartNo = %d\n", node_id, m->seqno, m->restartNo);	
	
	printf("My list is \n");
	for(n = list_head(neighbors_list); n != NULL; n = list_item_next(n)) {
		printf("%d \n",n->addr.u8[0]);
	}
	printf("-----\n");	

	//Check to see if we need to restart the network.
	if(m->restartNo != restartField)
	{
		//Reset the nieghbours list
		printf("Resetting neighbours, amount = %d\n",list_length(neighbors_list));

		n = list_head(neighbors_list);

		for(i = 0; n != NULL; i++) {
			nextN = list_item_next(n);
			printf("Removing %d from list \n", n->addr.u8[0]);
			list_remove(neighbors_list, n);
			n = nextN;
		}
		i = 0;
		bestNeighbourIHave = NULL;

		restartField = m->restartNo;
	} 

	/* Check if we already know this neighbor. */
	for(n = list_head(neighbors_list); n != NULL; n = list_item_next(n)) {

		/* We break out of the loop if the address of the neighbor matches
		the address of the neighbor from which we received this
		broadcast message. */
		if(linkaddr_cmp(&n->addr, from)) {
			printf("Already have this neighbour %d\n", n->addr.u8[0]);
			break;
		}
	}

	/* If n is NULL, this neighbor was not found in our list, and we
	allocate a new struct neighbor from the neighbors_memb memory
	pool. */
	if(n == NULL) {
		n = memb_alloc(&neighbors_memb);

		/* If we could not allocate a new neighbor entry, we give up. We
		could have reused an old neighbor entry, but we do not do this
		for now. */
		if(n == NULL) {
			return;
		}

		if(packetbuf_attr(PACKETBUF_ATTR_RSSI) < MIN_RSSI) {
			printf("This neighbours rssi = %d is unacceptability low. Ignoring them\n", 
					packetbuf_attr(PACKETBUF_ATTR_RSSI));
			return;
		}

		/* Initialize the fields. */
		linkaddr_copy(&n->addr, from);
		n->last_seqno = m->seqno;
		n->avg_seqno_gap = SEQNO_EWMA_UNITY;

		/* Place the neighbor on the neighbor list. */
		list_add(neighbors_list, n);

		if(m->seqno < bestHopIHave)
		{
			bestHopIHave = m->seqno;
			bestNeighbourIHave = n;
		}

		printf("the best hop i have is %d\n", bestHopIHave);

		sendBroadcast = 1;
	}

	/* We can now fill in the fields in our neighbor entry. */
	n->last_rssi = packetbuf_attr(PACKETBUF_ATTR_RSSI);
	n->last_lqi = packetbuf_attr(PACKETBUF_ATTR_LINK_QUALITY);

	/* Compute the average sequence number gap we have seen from this neighbor. */
	seqno_gap = m->seqno - n->last_seqno;
	n->avg_seqno_gap = (((uint32_t)seqno_gap * SEQNO_EWMA_UNITY) *
							SEQNO_EWMA_ALPHA) / SEQNO_EWMA_UNITY +
							((uint32_t)n->avg_seqno_gap * (SEQNO_EWMA_UNITY -
							SEQNO_EWMA_ALPHA)) / SEQNO_EWMA_UNITY;

	/* Remember last seqno we heard. */
	n->last_seqno = m->seqno;

	/* Print out a message. */
	printf("broadcast message received from %d.%d with seqno %d, RSSI %u, LQI %u, avg seqno gap %d.%02d\n",
			from->u8[0], from->u8[1],
			m->seqno,
			packetbuf_attr(PACKETBUF_ATTR_RSSI),
			packetbuf_attr(PACKETBUF_ATTR_LINK_QUALITY),
			(int)(n->avg_seqno_gap / SEQNO_EWMA_UNITY),
			(int)(((100UL * n->avg_seqno_gap) / SEQNO_EWMA_UNITY) % 100));

	printf("My list is \n");
	for(n = list_head(neighbors_list); n != NULL; n = list_item_next(n)) {
		printf("%d \n",n->addr.u8[0]);
	}
	printf("-----\n");	
}

/* This is where we define what function to be called when a broadcast
   is received. We pass a pointer to this structure in the
   broadcast_open() call below. */
static const struct broadcast_callbacks broadcast_call = {broadcast_recv};
/*---------------------------------------------------------------------------*/
/* This function is called for every incoming unicast packet. */
static void
recv_uc(struct unicast_conn *c, const linkaddr_t *from)
{
	struct unicast_message *msg;

	/* Grab the pointer to the incoming data. */
	msg = packetbuf_dataptr();
	printf("%d) Unicast got, message = %d\n", node_id, msg->data);
  
    /* Check that we at least have 1 other neighbour then the one we got the message from.*/
	if(list_length(neighbors_list) > 1) {
	  
		printf("sending unicast to %d.%d\n", bestNeighbourIHave->addr.u8[0], bestNeighbourIHave->addr.u8[1]);
		packetbuf_copyfrom(msg, sizeof(msg));
		unicast_send(&unicast, &bestNeighbourIHave->addr);
	}
	else
	{
		printf("Have no neighbours to send to. Sorry.\n");
	}
}
static const struct unicast_callbacks unicast_callbacks = {recv_uc};
/*---------------------------------------------------------------------------*/
PROCESS_THREAD(broadcast_process, ev, data)
{
	static struct etimer etWait;
	static struct broadcast_message msg;
	static struct etimer et;

	PROCESS_EXITHANDLER(broadcast_close(&broadcast);)

	PROCESS_BEGIN();

	broadcast_open(&broadcast, 129, &broadcast_call);

	etimer_set(&etWait, CLOCK_SECOND);

	printf("I am a node. Click me to send data.\n");

	while(1) {
		
	/* Yield control, wait for an event */
	PROCESS_PAUSE();

	if(sendBroadcast == 1)    
	{
		if(bestHopIHave < MAX_NEIGHBORS+1) {

			//reset flag to turn off.
			sendBroadcast = 0;

			/*Braodcast with the hop count incremented*/
		  	printf("sending broadcast with hop of: %d \n", bestHopIHave + 1);

		  	/* Send a broadcast after 1 - 8 seconds */
			etimer_set(&et, CLOCK_SECOND * 1 + random_rand() % (CLOCK_SECOND * 7));

			PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&et));

			msg.seqno = bestHopIHave + 1;
			msg.restartNo = restartField;
			packetbuf_copyfrom(&msg, sizeof(struct broadcast_message));
			broadcast_send(&broadcast);
		}
		else
		{
			printf("Holding no neighbours. Something Seriously has gone wrong here\n");
		}
	}
  }

  PROCESS_END();
}

/*---------------------------------------------------------------------------*/
PROCESS_THREAD(button_process, ev, data)
{    
	PROCESS_EXITHANDLER(unicast_close(&unicast);)
    
	PROCESS_BEGIN();

	unicast_open(&unicast, 146, &unicast_callbacks);

	/* Activate the button sensor. We use the button to drive traffic -
	when the button is pressed, a packet is sent. */
	SENSORS_ACTIVATE(button_sensor);

	static struct unicast_message *msg;
	static struct neighbor *n;
	static int i;

	while(1) {
		/* Wait until we get a sensor event with the button sensor as data. */
		PROCESS_WAIT_EVENT_UNTIL(ev == sensors_event &&
		data == &button_sensor);

		printf("%d) Button pressed \n", node_id);
		msg->data = linkaddr_node_addr.u8[0];
		/* Pick the best neighbour based on hop count and link quality.*/
		if(list_length(neighbors_list) > 0) {
		  
			//Show the neighbors we have
			n = list_head(neighbors_list);
			printf("my list contains\n");
			for(i = 0; i < list_length(neighbors_list); i++) {
				printf("neighbour %d.%d hops = %d\n",
					 n->addr.u8[0], n->addr.u8[1], n->last_seqno);
				n = list_item_next(n);
			}

			printf("sending unicast to %d.%d message = %d\n",
					 bestNeighbourIHave->addr.u8[0], bestNeighbourIHave->addr.u8[1], msg->data);

			packetbuf_copyfrom(msg, sizeof(struct unicast_message));
			unicast_send(&unicast, &bestNeighbourIHave->addr);
			
			i = 0;
			n = NULL;
			msg = NULL;
		}
		else
		{
			printf("Have no neighbours to send to. Sorry.\n");
		}
	}

	PROCESS_END();
}
