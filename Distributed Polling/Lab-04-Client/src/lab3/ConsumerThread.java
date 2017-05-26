package lab3;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.Scanner;


public class ConsumerThread implements Runnable
{
	static Scanner keyboard = new Scanner(System.in);
	private Random generator = new Random();
	private IBuffer buf ;
	private Thread th ;
	private String threadName ;
	
	public ConsumerThread(String name, IBuffer b) // consumes the randomInt from buffer and will try to guess 5 times
	{
		System.out.println("Creating Thread: Consumer") ;
		threadName = name ;
		buf = b;
	}

	public void run() 
	{
		System.out.println("Starting Thread: Consumer") ;
		int serverAnswer = 0;		
		
		while(true)
		{
			try 
			{
				if(buf.available() == true)
				{
					System.out.println("Input a number for the server to calculate") ;
					buf.sendServerInput(getInt());
					System.out.println("Requesting server to perform calculation") ;
					buf.requestWork();
					
					boolean workDone = false;
					
					while (workDone == false)
					{
						th.sleep(10);
						System.out.print("Polling server for answer: ") ;
						if(buf.available() == false)
						{
							System.out.print("no answer\n") ;
						}
						else
						{
							workDone = true;
							System.out.print("ANSWER!\n") ;
						}
					}
					
					serverAnswer = buf.getWork();
					System.out.println("Server has calculated: "+serverAnswer) ;
					System.out.println("Will sleep for a while") ;
					th.sleep(RNJesus(50,100));
					
				}
				else
				{
					th.sleep(RNJesus(100,200));
				}
			} 
			catch (RemoteException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
	public static int getInt()
	{
		int answer = 0 ;
		
		while (! keyboard.hasNextInt ())
		{
			keyboard.nextLine();
			System.out.println("That is not a number!");
			System.out.println("Please choose");
		}
		answer = keyboard.nextInt();	
		keyboard.nextLine();
		
		return answer ;
	}
	
	public int RNJesus(int min, int max)
	{
		int randomIndex = generator.nextInt(( max + 1 ) - min ) + max ;
		return randomIndex;
	}
}
