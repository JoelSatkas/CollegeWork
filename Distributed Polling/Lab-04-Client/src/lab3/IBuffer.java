package lab3;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
// client side 
public interface IBuffer extends Remote, Serializable
{
	static final String RMI_NAME = "Buffer" ;
	
	// client checks to see if server has done work
	public boolean available() throws RemoteException ;
	
	// client retrieves work from server
	public int getWork()throws RemoteException;
	
	// client sets boolean to indicate it wants work done
	public void requestWork() throws RemoteException;
	
	// client sends its input to the server
	public void sendServerInput(int calc) throws RemoteException;
}
