package lab3;

import java.rmi.RemoteException;
import java.util.Random;
import java.util.Scanner;


public class ProducerThread implements Runnable
{
	private IBuffer buf ;
	private Thread th ;
	private String threadName ;
	private Random generator = new Random();
	private int answer = 0;
	
	public ProducerThread(String name, IBuffer b)
	{
		System.out.println("Creating Thread: Producer");
		threadName = name;
		buf = b;
	}
	
	public void run() 
	{
		System.out.println("Running Producer Thread");
		
		while(true)
		{
			try 
			{
				if(buf.available() == false)
				{
					System.out.println("Client has indicated they want work done");
					System.out.println("Getting client input... "+buf.getClientRequest());
					System.out.println("Calculating.....");
					
					answer = RNJesus(1,10000);
					th.sleep(RNJesus(200,400));
					buf.setAnswer(answer);
					buf.setWorkDone();
					
					System.out.println("Done! Answer sent");
					th.sleep(RNJesus(100,200));
				}
				else
				{
					th.sleep(RNJesus(100,200));
				}
			} 
			catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	
	public int RNJesus(int min, int max)
	{
		int randomIndex = generator.nextInt(( max + 1 ) - min ) + max ;
		return randomIndex;
	}
}
