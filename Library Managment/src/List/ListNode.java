package List;


public class ListNode 
{
	Object data ;
	ListNode next ;
	
	public ListNode(Object p)
	{
		data = p ;
		next = null ;
	}
	
	public ListNode(Object p, ListNode nextNode)
	{
		data = p ;
		next = nextNode ;
		
	}
	
	public Object getObject()
	{
		return data ;
	}
	
	public ListNode getNext()
	{
		return next ;
	}
	
}
