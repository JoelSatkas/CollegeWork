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
	 * Assignment 4. Finish by Week 8
	 * Develop a distributed application to demonstrate polling e.g. where the client must
	 * 1. Request a service
	 * 2. Wait until the server sets a Boolean indicating that the service is ready.
	 * 3. Consume the service You may use this like in lab2 to remotely deliver an integer,
	 * where server/producer sleeps for random times.
	 * 
	 */

	public static void main(String[] args) 
	{
		final String SECURITY_POLICY = "java.security.policy";
		final String FILE_PATH = "src/client.policy";
		IBuffer buf;
		FactoryObjectExample Factory;
		ProducerThread pro;
		
		System.setProperty(SECURITY_POLICY, FILE_PATH);
		
		try 
		{
			Factory = new FactoryObjectExample(4,"FactoryA");
			Registry registry = LocateRegistry.createRegistry(ObjectInterface.RMI_PORT);
            
            registry.rebind(ObjectInterface.RMI_NAME, Factory);
            System.out.println("Factory bound");
            
            buf = Factory.getBuffer();
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
