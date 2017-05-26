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
	
	public void setNext(ListNode n)
	{
		next = n ;
	}
	
	public void setData(Object d)
	{
		data = d ;
	}
	
	public Object getData()
	{
		return data ;
	}
	
	public ListNode getNext()
	{
		return next ;
	}
	
}
