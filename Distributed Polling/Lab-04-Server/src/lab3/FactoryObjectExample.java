package lab3;

import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class FactoryObjectExample extends UnicastRemoteObject implements ObjectInterface 
{
	
	private int ID;
	private String name;
	private IBuffer buffer = new Buffer();
	

	public FactoryObjectExample(int id, String n) throws RemoteException 
	{
		ID = id;
		name = n;
	}

	public String getName() throws RemoteException 
	{
		// TODO Auto-generated method stub
		return name;
	}

	public IBuffer getBuffer() throws RemoteException
	{
		// TODO Auto-generated method stub
		return buffer;
	}

}
