#include "lib/list.h"
#include "lib/memb.h"
#include "lib/random.h"
#include "net/rime/rime.h"
#include "dev/button-sensor.h"
#include "node-id.h"
#include <stdio.h>

// This is the amount of seconds before restarting the network
#define AMOUNT_OF_SECS_BETWEEN_RESTARTS 45

/* This is the structure of broadcast messages. */
struct broadcast_message {
	uint8_t seqno;
	int restartNo;
};

/* This is the structure of unicast ping messages. */
struct unicast_message {
	int data;
};

/* These hold the broadcast and unicast structures, respectively. */
static struct broadcast_conn broadcast;
static struct unicast_conn unicast;

/*---------------------------------------------------------------------------*/
PROCESS(broadcast_process, "Broadcast process");

/* The AUTOSTART_PROCESSES() definition specifices what processes to
   start when this module is loaded. We put our processes
   there. */
AUTOSTART_PROCESSES(&broadcast_process);

static const struct broadcast_callbacks broadcast_call = {};
/*---------------------------------------------------------------------------*/
/* This function is called for every incoming unicast packet. */
static void 
recv_uc(struct unicast_conn *c, const linkaddr_t *from)
{
	static struct unicast_message *msg;

	/* Grab the pointer to the incoming data. */
	msg = packetbuf_dataptr();
  
	printf("I am Sink. Got message from %d.%d = %d\n", 
					from->u8[0], from->u8[1], msg->data);
  
}
static const struct unicast_callbacks unicast_callbacks = {recv_uc};
/*---------------------------------------------------------------------------*/
PROCESS_THREAD(broadcast_process, ev, data)
{
	static struct etimer et;
	static struct broadcast_message msg;
	/*
	This is the number that will keep track of the network version.
	If the number reachs the limit of an integer, then an integer overflow will occur
	and it will begin from a negative number and increment its way back to the top.
	*/
	static int restartTimes = 0;

	PROCESS_EXITHANDLER(broadcast_close(&broadcast);)

	PROCESS_BEGIN();

	broadcast_open(&broadcast, 129, &broadcast_call);
	unicast_open(&unicast, 146, &unicast_callbacks);

	/* Activate the button sensor. We use the button to drive traffic -
	when the button is pressed, a packet is sent. */
	SENSORS_ACTIVATE(button_sensor);

	printf("I am Sink. Click me to begin.\n");

	/* Wait until we get a sensor event with the button sensor as data. */
	PROCESS_WAIT_EVENT_UNTIL(ev == sensors_event &&
	data == &button_sensor);

	while(1) {

		printf("I am Sink. Sending broadcast to restart network.\n");
		msg.seqno = 0;
		msg.restartNo = restartTimes;
		packetbuf_copyfrom(&msg, sizeof(struct broadcast_message));
		broadcast_send(&broadcast);

		restartTimes++;

		/* wait for X seconds */
		etimer_set(&et, CLOCK_SECOND * AMOUNT_OF_SECS_BETWEEN_RESTARTS);

		PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&et));
	}
  
  PROCESS_END();
}

