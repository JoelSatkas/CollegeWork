package lab3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class Buffer extends UnicastRemoteObject implements IBuffer
{
	
	private boolean workDone;	// 0 = client wants work server has not given, 
	 							// 1 = server has given, client does not want work
	private int work;
	private int clientInput;


	protected Buffer() throws RemoteException 
	{
		super();
		workDone = true;
		work = 0;
		clientInput = 0;
	}
	
	// server buffer will take in a client callback object
	public void callBackToClient(IClientCallBack callBackObject) throws RemoteException
	{
		while(available() == false)
		{
			
			try 
			{
				Thread.sleep(200);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		callBackObject.notifyClient(String.valueOf(getWork()));
	}
		
	
	// server-side method: tells client that the work is done
	public void setWorkDone() throws RemoteException 
	{
		workDone = true;
	}
	
	
	// server-side method: gives the 'calculation' to the buffer so client can read it
	public void setAnswer(int ans) throws RemoteException
	{
		work = ans;	
	}
	
	
	// server-side method: gets the input from the client that it needs to perform the 'calculation'
	public int getClientRequest() throws RemoteException
	{
		return clientInput;
	}
	
	
	// Both client and server side: the communication between the two. 
	// false = client wants server to do work, server has not done work
	// true = server has done work, client does not want work
	public boolean available() throws RemoteException 
	{
		return workDone;
	}
	
	
	// client-side method: retrieves work from server
	public int getWork() throws RemoteException 
	{
		return work;
	}
	
	
	// client-side method: sets boolean to indicate it wants work done
	public void requestWork() throws RemoteException
	{
		workDone = false ;
	}
	
	
	// client-side method: sends its input to the server
	public void sendServerInput(int calc) throws RemoteException
	{
		clientInput = calc;
	}
	
}
