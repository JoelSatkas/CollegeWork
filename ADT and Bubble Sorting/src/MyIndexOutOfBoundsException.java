
public class MyIndexOutOfBoundsException extends Exception 
{
	public MyIndexOutOfBoundsException()
	{
		super("The index is out of range") ;
	}
	
	public MyIndexOutOfBoundsException(String message)
	{
		super(message) ;
	}
}
