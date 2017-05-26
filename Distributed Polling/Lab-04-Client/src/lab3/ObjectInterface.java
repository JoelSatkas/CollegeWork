package lab3;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Note: interface for the factory
 */
public interface ObjectInterface extends Remote
{
	static final int RMI_PORT = 222;
	static final String RMI_NAME = "objFactory";
	
	public String getName() throws RemoteException;
	
	public IBuffer getBuffer()throws RemoteException;
}
