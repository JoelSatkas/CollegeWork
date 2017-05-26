package lab3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain 
{
	
	/**
	 * 
	 * Assignment 5. Finish by Week 11
	 * 
	 * Develop a distributed application to implement a call-back based application.
	 * I’ll accept the integer being served again.
	 * The client registers and the server sends the integers one at a time
	 * when it is ready and not sleeping(one integer at a time when the client is ready).
	 * Keep the ordering only call-back when client/consumer is ready.
	 */
	

	public static void main(String[] args) 
	{
		final String SECURITY_POLICY = "java.security.policy";
		final String FILE_PATH = "src/client.policy";
		IBuffer buf;
		ProducerThread pro;
		
		System.setProperty(SECURITY_POLICY, FILE_PATH);
		
		if (System.getSecurityManager() == null) 
		{
			System.setSecurityManager(new SecurityManager());
			System.out.println("Security manager set");
		}
		
		try 
		{
			buf = new Buffer();
			Registry registry = LocateRegistry.createRegistry(IBuffer.RMI_PORT);
            
            registry.rebind(IBuffer.RMI_NAME, buf);
            System.out.println("Buffer bound");
            
            pro = new ProducerThread("Server-1",buf);
            pro.start();
		} 
		catch (RemoteException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
