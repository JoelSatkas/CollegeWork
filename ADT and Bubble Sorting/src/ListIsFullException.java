
public class ListIsFullException extends Exception
{
	public ListIsFullException()
	{
		super("the list is full") ;
	}
	
	public ListIsFullException(String message)
	{
		super(message) ;
	}
}
