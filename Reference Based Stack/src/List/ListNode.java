package List;


public class ListNode 
{
	char data ;
	ListNode next ;
	
	public ListNode(char p)
	{
		data = p ;
		next = null ;
	}
	
	public ListNode(char p, ListNode nextNode)
	{
		data = p ;
		next = nextNode ;
	}
	
	public char getData()
	{
		return data ;
	}
	
	public ListNode getNext()
	{
		return next ;
	}
	
}
