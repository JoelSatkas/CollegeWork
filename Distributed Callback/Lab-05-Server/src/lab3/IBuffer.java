package lab3;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
// server side
public interface IBuffer extends Remote, Serializable 
{
	static final String RMI_NAME = "Buffer" ;
	static final int RMI_PORT = 222;
	
	// server tells client that the work is done
	public void setWorkDone()throws RemoteException; 
	
	// server gives the 'calculation' to the buffer so client can read it
	public void setAnswer(int ans) throws RemoteException; 
	
	// server checks if it need to calculate something from the client
	public boolean available() throws RemoteException;
	
	// server gets the input from the client that it need to perform the 'calculation'
	public int getClientRequest() throws RemoteException;
	
	// server buffer will take in a client callback object
	public void callBackToClient(IClientCallBack callBackObject) throws RemoteException;
		
	// client retrieves work from server
	public int getWork()throws RemoteException;
		
	// client sets boolean to indicate it wants work done
	public void requestWork() throws RemoteException;
		
	// client sends its input to the server
	public void sendServerInput(int calc) throws RemoteException;
	
}
