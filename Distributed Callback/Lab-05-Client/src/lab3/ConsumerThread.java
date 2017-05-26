package lab3;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;


public class ConsumerThread implements Runnable
{
	static Scanner keyboard = new Scanner(System.in);
	private Random generator = new Random();
	private IBuffer buf ;
	private Thread th ;
	private String threadName ;
	private IClientCallBack callBack;
	
	public ConsumerThread(String name, IBuffer b, IClientCallBack cb)
	{
		println("Creating Thread: Consumer") ;
		threadName = name ;
		buf = b;
		callBack = cb;
	}

	public void run() 
	{
		println("Running Thread: Consumer ") ;
		int serverAnswer = 0;		
		
		while(true)
		{
			try 
			{
				if(buf.available() == true)
				{
					println("Input a number for the server to calculate") ;
					buf.sendServerInput(getInt());
					println("Requesting server to perform calculation") ;
					buf.requestWork();
					buf.callBackToClient(callBack);
					
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
	
	public void println(String msm)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(sdf.format(cal.getTime())+": "+msm);
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
