import java.util.Random;
import java.util.Scanner;


public class ProducerThread implements Runnable
{
	/*
	 * 
	 * will need to get rid of .join() command if the pool executer creates these threads
	 */
	
	static Scanner keyboard = new Scanner(System.in);
	private Buffer buf ;
	private Thread th ;
	private String threadName ;
	private int newNumber ;
	
	public ProducerThread(Buffer b, String name)
	{
		System.out.println("Creating Thread: Producer");
		buf = b ;
		threadName = name ;
	}
	
	public void run() 
	{
		System.out.println("Running Producer Thread");
		
		
		while (true) 
		{
			if (buf.finish() == true)// if sleep = true, try to shut down 
			{
					try 
					{
						System.out.println("PRODUCER THREAD: Shutting down ");
						th.join();
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			
			if(buf.getValueSwitch() == false)
			{// 0 = value was consumed and no new value was given therefore consumer needs new value
				
				System.out.print("PRODUCER THREAD here ");
				System.out.println(" Buffer indicates that it needs new value...");
				System.out.println("please enter a positive number (1-10)\n "
						+ "for the producer to guess or a negative to stop all threads");
				
				newNumber = getInt() ;
				if(newNumber < 0)// if negative number will shut down the threads
				{
					System.out.println("PRODUCER THREAD: Setting sleep to true in buffer");
					buf.setFinish();
				}
				else
				{
					buf.setNumber(newNumber);
					buf.setValueSwitch(true);// will show that value was given
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
			}
			else
			{
				try 
				{
					th.sleep(100) ;
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
		System.out.println("Starting Thread: Producer");
		if(th == null)
		{
			th = new Thread(this, threadName);
			th.start();
		}
	}
	
	public int getInt()
	{
		int answer = 0 ;
		
		while (! keyboard.hasNextInt ())
		{
			keyboard.nextLine();
			System.out.println("That is not a number!");
			System.out.println("Please choose a number");
		}
		answer = keyboard.nextInt();	
		keyboard.nextLine();
		
		return answer ;
	}
}
