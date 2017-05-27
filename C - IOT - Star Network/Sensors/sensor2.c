#include "sensor.h"

PROCESS(sensor2, "");


/*---------------------------------------------------------------------------*/
// Empty unicast callback because sensor does not need it.
static const struct unicast_callbacks unicast_callbacks = {};
static struct unicast_conn uc;
/*---------------------------------------------------------------------------*/

PROCESS_THREAD(sensor2, ev, data)
{
  PROCESS_EXITHANDLER(unicast_close(&uc);)
    
  PROCESS_BEGIN();

  unicast_open(&uc, 146, &unicast_callbacks);

  while(1) {
    linkaddr_t addr;
    // Wake up only for the custom event
    PROCESS_WAIT_EVENT_UNTIL(ev == custom_event);
 
    packetbuf_copyfrom(&data, 1);
    addr.u8[0] = FROM_SENSOR;
    addr.u8[1] = 0;

    if(!linkaddr_cmp(&addr, &linkaddr_node_addr)) {
      unicast_send(&uc, &addr);
    }

  }

  PROCESS_END();
}
