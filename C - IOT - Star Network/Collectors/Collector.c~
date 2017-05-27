#include "contiki.h"
#include "net/rime/rime.h"
#include "random.h"
#include "dev/button-sensor.h"
#include "dev/leds.h"
#include <stdio.h>
#include "node-id.h"

#define FROM_SENSOR 1 // The id of the collector
#define FROM_COLLECTOR 3 // The id from sensor -> Not used in this assingment
/*---------------------------------------------------------------------------*/
PROCESS(Collector, "");
AUTOSTART_PROCESSES(&Collector);


/*---------------------------------------------------------------------------*/
// unicast callback: will print the temperature from the node
static void
recv_uc(struct unicast_conn *c, const linkaddr_t *from)
{
  printf("Collector) unicast message received from %d.%d: temp = %dC\n",
	 from->u8[0], from->u8[1], *(int*)packetbuf_dataptr());
}
static const struct unicast_callbacks unicast_callbacks = {recv_uc};
static struct unicast_conn uc;
/*---------------------------------------------------------------------------*/

//Empty boradcast callback. Dont need one because collecter will not receive boradcasts
/*---------------------------------------------------------------------------*/
static const struct broadcast_callbacks broadcast_call = {};
static struct broadcast_conn broadcast;
/*---------------------------------------------------------------------------*/


PROCESS_THREAD(Collector, ev, data)
{
  static struct etimer et;
  static int AmountOfSamples = 4; // amount of samples for the sensor to take

  PROCESS_EXITHANDLER(unicast_close(&uc);)
    
  PROCESS_BEGIN();

  unicast_open(&uc, 146, &unicast_callbacks);
  broadcast_open(&broadcast, 129, &broadcast_call);

  while(1) {
   /* Broadcast every 6 seconds */
    etimer_set(&et, CLOCK_SECOND * 6 );

    PROCESS_WAIT_EVENT_UNTIL(etimer_expired(&et));

    packetbuf_copyfrom(&AmountOfSamples, 1);
    broadcast_send(&broadcast);
    printf("Collector) broadcast message sent\n");
  }

  PROCESS_END();
}
/*---------------------------------------------------------------------------*/
