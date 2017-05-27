#include "sensor.h"

/* Declare and autostart the process */

PROCESS(sensor1, "");
AUTOSTART_PROCESSES(&sensor1, &sensor2);

static int Temps[GO_AROUND];
static struct etimer et;
static linkaddr_t addr;
static int sendBack;
static int sampleAmounts;
static int collectorsAddress1, collectorsAddress2;

/*---------------------------------------------------------------------------*/
// Broadcast callback. Check to see if from Collector, 
// get the amount to sample and set global flag
static void
broadcast_recv(struct broadcast_conn *c, const linkaddr_t *from)
{
  printf("%d) broadcast message received from %d.%d: '%d'\n",
         node_id,from->u8[0], from->u8[1], *(int*)packetbuf_dataptr());

  if(from->u8[0] == FROM_SENSOR)
  {
	  sendBack = 1;
	  sampleAmounts = *(int*)packetbuf_dataptr();
	  collectorsAddress1 = from->u8[0];
	  collectorsAddress2 = from->u8[1];
  }
}
static const struct broadcast_callbacks broadcast_call = {broadcast_recv};
static struct broadcast_conn broadcast;
/*---------------------------------------------------------------------------*/

PROCESS_THREAD(sensor1, ev, data)
{
	static struct etimer et;
	static int TimesRound, T, TotalTemp;

	PROCESS_EXITHANDLER(broadcast_close(&broadcast);)
	
	PROCESS_BEGIN();

	broadcast_open(&broadcast, 129, &broadcast_call);

	etimer_set(&et, CLOCK_SECOND);
	TimesRound = 0;
	T = 0;
	TotalTemp = 0;
	

	while (1){
		linkaddr_t addr;
		/* Yield control, wait for an event */
		PROCESS_YIELD();

		// if global flag set get average and send unicast
		if(sendBack == 1)
		{ 
			int x;
			for(x = 0; x < GO_AROUND && x < sampleAmounts; x++)
			{
				TotalTemp+=Temps[x];
			}
			TotalTemp = TotalTemp/sampleAmounts;
			// Wait randomly up to 5 seconds
			etimer_set(&et, CLOCK_SECOND * 5 + random_rand() % (CLOCK_SECOND * 5));
			// Broadcast the custom event to all processes
    			process_post(PROCESS_BROADCAST, custom_event, TotalTemp);
			sendBack = 0;
			TotalTemp = 0;
		}

		/* An event happened - only interested in the timer event */
		if (ev == PROCESS_EVENT_TIMER){ // should also check if it's OUR timer!
			/* Switch light sensor ON */
			/* It's important to keep sensors active
			* for as little time as possible.
			*/

			if(TimesRound >= GO_AROUND)
			{
				TimesRound = 0;
			}

			SENSORS_ACTIVATE(sht11_sensor);
			/* Read light sensor */
			int temp = sht11_sensor.value(SHT11_SENSOR_TEMP);
			//int temp = random_rand();
			etimer_reset(&et);
			/* Switch light sensor OFF again to conserve energy */
			SENSORS_DEACTIVATE(sht11_sensor);

			T = -39.60 + 0.01*(int)temp;
			Temps[TimesRound] = T;
			TimesRound = TimesRound + 1;
		}
	}
	PROCESS_END();
}
