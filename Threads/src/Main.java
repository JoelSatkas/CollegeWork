import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main 
{
	/**
	 * 
	 * Assignment 2. Finish by Week 5
	 * Develop a multithreaded producer-consumer system where a producer
	 * produces a random integer number (range 1 to 10). 
	 * A consumer guesses this number (5 Guesses), sees if its guess is correct.
	 * The consumer counts the number of correct guesses,
	 * and clicks a next button in order for the producer to produce
	 * another integer so that it can try guessing again.  
	 * Note the shared buffer holds only one integer at a time and the
	 * number of correct guesses. 
	 * The producer and consumer outputs the number of correct/incorrect
	 * before finishing.
	 * Make sure that the consumer does not consume the same item
	 * twice and that the producer does not overwrite an unconsumed item.
	 * Do not use an array-blocking Queue.
	 * 
	 *  
	 */
	
	public static void main(String[] args) 
	{
		Buffer buf = new Buffer() ;
		
		ExecutorService threadExecutor = Executors.newCachedThreadPool();
		
		ConsumerThread consumer = new ConsumerThread(buf,"ConsumerThread") ;
		ProducerThread producer = new ProducerThread(buf, "ProducerThread") ;
		
		threadExecutor.execute(consumer);
		threadExecutor.execute(producer);
		
//		consumer.start() ;
//		producer.start();
	}
}
