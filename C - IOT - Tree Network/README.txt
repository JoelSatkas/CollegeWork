IOT Assignment 2:

Many-to-one routing. V1.3 FINAL
Contiki 3.0

Sink Node:
-> Acts as a sink. Will broadcast a network reset every 40 seconds 
after the button is clicked.

-> Will receive the messages from all other nodes.

Source Node:
-> Will send out and receive broadcasts to build a network.

-> Source will send out a broadcast if it receives a new neighbor as that
	neighbor might not know about the source.

-> Clicking button will send out its rime id using unicast.

-> Gettign a unicast message it will push it to the next node that is closest to 
the sink.