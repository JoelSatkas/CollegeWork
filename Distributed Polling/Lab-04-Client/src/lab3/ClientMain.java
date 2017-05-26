package lab3;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain 
{
	public static void main(String[] args) 
	{
		final String SECURITY_POLICY = "java.security.policy";
		final String FILE_PATH = "src/client.policy";
		IBuffer buf;
		
		System.setProperty(SECURITY_POLICY, FILE_PATH);
		//System.setProperty("java.security.policy","ClientMain:./ClientMain.policy");
		
		if (System.getSecurityManager() == null) 
		{
			System.setSecurityManager(new SecurityManager());
			System.out.println("Security manager set");
		}
		
		try 
		{
            Registry registry = LocateRegistry.getRegistry(ObjectInterface.RMI_PORT);
            ObjectInterface factoryA = (ObjectInterface) registry.lookup(ObjectInterface.RMI_NAME);
			
            System.out.println("Name of Object retreved: "+factoryA.getName());
            buf = factoryA.getBuffer();
            
            ConsumerThread consumer = new ConsumerThread("Client-1",buf);
            consumer.start();
		} 
		catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
