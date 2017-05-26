import java.util.Random;


public class ConsumerThread implements Runnable
{
	/*
	 * 
	 * will need to get rid of .join() command if the pool executer creates these threads
	 */
	
	static Random RNG = new Random() ;
	private Buffer buf ;
	private Thread th ;
	private String threadName ;
	private int correctGuess ;
	private final int GUESS_AMOUNT = 5 ;
	private final int MAXIMUM_NUMBER = 10 ;
	private final int MINIMUM_NUMBER = 1 ;
	
	public ConsumerThread(Buffer b, String name) // consumes the randomInt from buffer and will try to guess 5 times
	{
		System.out.println("Creating Thread: Consumer") ;
		buf = b ;
		threadName = name ;
	}

	public void run() 
	{
		System.out.println("Starting Thread: Consumer") ;
		
		boolean keepGuessing = true ;
		int guessed = 0 ;
		int[] alreadyGuessed = new int[GUESS_AMOUNT];
		int randomNumber;
		boolean valid = true ;
		int valueUsed = 0 ;

		while (true) 
		{		
			if (buf.finish() == true) 
			{
				try 
				{
					System.out.println("CONSUMER THREAD: Shutting down thread");
					th.join();
				} 
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			
			valueUsed = 0;
			
			if(buf.getValueSwitch() == true) //1 = value was given so consumer can take it
			{
				System.out.println("CONSUMER THREAD: New value found, will try guessing");
				while(keepGuessing == true)
				{
					randomNumber = RNG.nextInt(( MAXIMUM_NUMBER + 1 ) - MINIMUM_NUMBER ) + MINIMUM_NUMBER ;
					
					while(valid)
					{
						valid = false ;
						
						for (int x = 0; x < GUESS_AMOUNT; x++) 
						{
							if (alreadyGuessed[x] == randomNumber) 
								
							{
								valid = true ;
							} 
						}
						
						if(valid == true)
						{
							randomNumber = RNG.nextInt(( MAXIMUM_NUMBER + 1 ) - MINIMUM_NUMBER ) + MINIMUM_NUMBER ;
						}
						else
						{
							alreadyGuessed[valueUsed] = randomNumber;
							valueUsed++ ;
							
						}
					}
					
					valid = true ;
					
					System.out.println("CONSUMER THREAD: I am guessing "+randomNumber);
					
					if(buf.getNumber() == randomNumber)
					{
						correctGuess++ ;
						
						System.out.println("CONSUMER THREAD: Correct!\n"
								+ "So Far I have guessed "+correctGuess+" times correctly");
						
						keepGuessing = false ;
					}
					else
					{
						System.out.println("CONSUMER THREAD: Wrong");
						guessed++ ;
					}
					
					if(guessed >= GUESS_AMOUNT)
					{
						keepGuessing = false;
					}
				}
				
				System.out.println("CONSUMER THEAD: Have consumed number, will ask for new number...");
				buf.setValueSwitch(false);
				keepGuessing = true ;
				alreadyGuessed = new int[GUESS_AMOUNT];
				guessed = 0 ;
				
				try 
				{
					th.sleep(200);
				} 
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try 
				{
					//System.out.println("CONSUMER THREAD: No new value... will sleep for 1000");
					th.sleep(1000);
				} 
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void start()
	{
		System.out.println("Starting Thread: Consumer") ;
		if(th == null)
		{
			th = new Thread(this, threadName);
			th.start() ;
		}
	}	
}
