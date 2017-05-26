package lab3;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientCallBack extends Remote, Serializable
{
	
	public void notifyClient(String input) throws RemoteException;
	
}
