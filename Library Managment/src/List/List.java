package List;

public class List 
{
	private ListNode firstNode ;
	private ListNode lastNode ;
	private String name ;
	private static int counter = 0 ;
	
	
	public List(String s)
	{
		name = s ;
		firstNode = lastNode = null ;
	}
	
	public List()
	{
		this("List") ;
	}
	
	public void insertAtfront(Object insertItem)
	{
		if(isEmpty())
		{
			firstNode = lastNode = new ListNode(insertItem);
		}
		else
		{
			firstNode = new ListNode(insertItem, firstNode) ;
		}
		counter++ ;
	}
	
	public void insertAtBack(Object insertItem)
	{
		if(isEmpty())
		{
			firstNode = lastNode = new ListNode(insertItem);
		}
		else
		{
			lastNode = lastNode.next = new ListNode(insertItem) ;
		}
		counter++ ;
	}
	
	public Object removeFromFront() throws EmptyListException
	{
		Object removeItem = null ;
		
		if(isEmpty())
		{
			throw new EmptyListException(name) ;
		}
		
		removeItem = firstNode.data ;
		
		if(firstNode.equals(lastNode))
		{
			firstNode = lastNode = null ;
		}
		else
		{
			firstNode = firstNode.next ;
		}
		
		counter-- ;
		return removeItem ;
	}
	
	public Object removeFromBack() throws EmptyListException
	{
		Object removeItem = null ;
		
		if(isEmpty())
		{
			throw new EmptyListException(name) ;
		}
		
		removeItem = lastNode.data ;
		
		if(firstNode.equals(lastNode))
		{
			firstNode = lastNode = null ;
		}
		else
		{
			ListNode current = firstNode ;
			
			while(current.next != lastNode)
			{
				current = current.next ;
			}
			
			lastNode = current ;
			current.next = null ;
		}
		
		counter-- ;
		return removeItem ;
		
	}
	
	public void remove(int index) throws EmptyListException
	{
		ListNode removeItem = null ;
		ListNode previous  = firstNode ;
		
		if(isEmpty())
		{
			throw new EmptyListException(name) ;
		}
		
		if(firstNode.equals(lastNode))
		{
			firstNode = lastNode = null ;
		}
		else
		{
			for(int x = 0 ; x < (index-1) ; x++)
			{
				previous = previous.next ;
			}
			
			removeItem = previous.next ;
			
			previous.next = removeItem.next ;
		}
		
		counter-- ;
	}

	public Object get(int index) throws EmptyListException
	{
		
		if(isEmpty())
		{
			throw new EmptyListException(name) ;
		}
		
		
		ListNode current  = firstNode ;
		
		for(int x = 0 ; x < (index) ; x++)
		{
			current = current.next ;
		}
		
		return current.data ;
	}
	
	public boolean isEmpty()
	{
		return firstNode == null ;
	}
	
	public void print()
	{
		
		if(isEmpty())
		{
			System.out.println("Empty "+name) ;
		}
		
		System.out.println("the "+name+" is: ") ;
		
		ListNode current = firstNode;
		
		while(current != null)
		{
			System.out.println(current.data.toString() + " ") ;
			current = current.next ;
		}
		
		System.out.println() ;
		System.out.println() ;
		
	}
	
	public int getAmount()
	{
		return counter ;
	}
}
