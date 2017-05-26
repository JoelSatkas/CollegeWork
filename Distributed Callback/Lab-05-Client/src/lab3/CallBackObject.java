package lab3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallBackObject extends UnicastRemoteObject implements IClientCallBack
{

	
	protected CallBackObject() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public void notifyClient(String input) throws RemoteException 
	{
		System.out.println("Message recieved from server: "+input);
	}
	
	
	
	
}
