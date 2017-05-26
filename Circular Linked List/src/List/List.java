package List;

public class List 
{
	private ListNode firstNode ;
	//private ListNode tempNode ;
	private ListNode lastNode ;
	private String name ;
	private static int counter = 0 ;
	
	
	public List(String s)
	{
		name = s ;
		lastNode = null ;
	}
	
	public List()
	{
		this("List") ;
	}
	
	public Object getHead()
	{
		return lastNode.next.data ;
	}
	
	public Object getTail()
	{
		return lastNode.data ;
	}
	
	public void deleteAll()
	{
		firstNode = lastNode = null ;
		counter = 0 ;
	}
	
	public void insertAtBack(Object insertItem)
	{
		if(isEmpty())
		{
			lastNode = new ListNode(insertItem);
			firstNode = lastNode ;
			lastNode.next = firstNode ;
		}
		else
		{
			firstNode = lastNode.next ;
			lastNode = lastNode.next = new ListNode(insertItem) ;
			lastNode.next = firstNode ;
			
		}
		counter++ ;
	}
	
	public void removeFromFront() throws EmptyListException
	{
		if(isEmpty())
		{
			throw new EmptyListException(name) ;
		}
		
		
		
		if(lastNode.equals(lastNode.next))
		{
			firstNode = lastNode = null ;
			counter = 0 ;
		}
		else
		{
			firstNode = firstNode.next ;
		}
		
		counter-- ;
	}
	
	public Object get(int index) throws EmptyListException
	{
		
		if(isEmpty())
		{
			throw new EmptyListException(name) ;
		}
		
		
		ListNode current = lastNode.next ;
		
		
		for(int x = 0 ; x < (index-1) ; x++)
		{
			current = current.next ;
		}
		
		return current.data ;
	}
	
	public boolean isEmpty()
	{
		if(lastNode == null)
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	
	public void print()
	{
		
		if(isEmpty())
		{
			System.out.println("Empty "+name) ;
		}
		else
		{
			System.out.println("\nthere are "+counter+" Nodes ") ;
			System.out.println("the "+name+" is: ") ;
			
			ListNode current = lastNode.next ;
			
			for(int x = 0 ; x < counter ; x++)
			{
				System.out.println(current.getData() + " ") ;
				System.out.println("---------") ;
				current = current.next ;
			}
			
			System.out.println() ;
			System.out.println() ;
		}
		
		
		
	}
	
	public int getAmount()
	{
		return counter ;
	}
}
