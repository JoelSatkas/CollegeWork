
public abstract class ObjectList 
{

	private Object[] oList ;
	private int total ;
	private int maxSize ;
	
	public ObjectList(int size)
	{
		oList = new Object[size] ;
		maxSize = size ;
	}
	
	public boolean add(Object newObj)
	{
		if(total >= maxSize)
		{
			return false ;
		}
		else
		{
			oList[total] = newObj ;
			total++ ;
			return true ;
		}
	}
	
	public boolean remove(int position)
	{
		oList[position] = null ;
		
		for(int x = position ; x < (maxSize-1) ; x++)
		{
			oList[x] = oList[x+1] ;
		}
		
		total--;
		
		return true ;
	}
	
	
	public boolean isFull()
	{
		if(total >= maxSize)
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	
	public boolean isEmpty()
	{
		if(oList.length == 0)
		{
			return true ;
		}
		else
		{
			return false ;
		}
	}
	
	public Object getObject(int position)
	{
		return oList[position] ;
	}
	
	public int getTotal()
	{
		return total ;
	}
	
}
