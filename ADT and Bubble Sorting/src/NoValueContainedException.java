
public class NoValueContainedException extends Exception 
{
	public NoValueContainedException()
	{
		super("There is no value") ;
	}
	
	public NoValueContainedException(String message)
	{
		super(message) ;
	}
	
}
