#ifndef __PROC_EVENT_H_
#define __PROC_EVENT_H_

#include "contiki.h" // Always need to have contiki.h
#include <stdio.h> // In case you use printf
#include "lib/sensors.h" // For sensing macros
#include "dev/light-sensor.h" // To access the light sensor (hamamatsu)
#include "dev/sht11/sht11-sensor.h" // To access the temp sensor
#include "sys/etimer.h" // The etimer
#include "random.h" // Generate random number
#include <stdlib.h>
#include "net/rime/rime.h" // Inport Rime for broadcasts and unicasts
#include "node-id.h" // Get the nodes id

#define GO_AROUND 5 // Amount of temperature samples a sensor stores
#define FROM_SENSOR 1 // The id of the collector
#define FROM_COLLECTOR 3 // The id from sensor -> Not used in this assingment

// Defines the second processes and the custom event
PROCESS_NAME(sensor2);
process_event_t custom_event;

#endif
