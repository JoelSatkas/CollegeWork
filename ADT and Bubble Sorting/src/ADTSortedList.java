import java.util.Arrays;


public class ADTSortedList 
{
	private int[] theArray ;
	private int arraySize ;
	private int amount = 0 ;
	
	public ADTSortedList(int size)
	{
		arraySize = size ;
		theArray = new int[arraySize];
		createEmptySortedList();
	}
	
	public ADTSortedList()
	{
		this(10);
	}
	
	private void createEmptySortedList()
	{
		for(int x = 0 ; x < arraySize ; x++)
		{
			theArray[x] = 0 ;
		}
	}
	
	public void printArray()
	{
		String answer = "" ;
		for(int x = 0 ; x < arraySize ; x++)
		{
			answer += theArray[x] + " ";
		}
		System.out.println(answer);
	}
	
	public boolean sortedIsEmpty()
	{
		boolean answer = true ;
		int num = 0 ;
		
		for(int x = 0 ; x < arraySize ; x++)
		{
			if(theArray[x] != 0)
			{
				num++ ;
			}
		}
		
		if(num > 0)
		{
			answer = false ;
		}
		
		return answer ;
	}
	
	public int getSortedSize()
	{
		return arraySize ;
	}
	
	public void sortAdd(int newValue)
	{
		try
		{
			if(amount > arraySize)
			{
				throw new ListIsFullException() ;
			}
		}
		catch(ListIsFullException e)
		{
			String message3 = e.getMessage() ;
			System.out.println(message3) ;
			System.out.println("Shutting down") ;
			System.exit(0) ;
		}
		
		amount++ ;
	
		int temp = 0 ;
		boolean copy = false ;
		
		for(int x = 0 ; x < arraySize ; x++)
		{
			
			if(newValue == theArray[x])
			{
				copy = true ;
			}
			
		}
		
		if(copy == true)
		{
			System.out.println("Array already contains this value") ;
		}
		else
		{
			for(int x = 0 ; x < arraySize ; x++)
			{
				for(int z = 0 ; z < arraySize ; z++)
				{
					
					if(newValue  > theArray[z])
					{
						temp = theArray[z] ;
						theArray[z] = newValue ;
						newValue = temp ;
					}
				}
			}
			
			
			
		}
		
		
		
	}
	
	public void sortedRemove(int index)
	{
		
		try
		{
			if((index > (arraySize-1))||(index < 0 ))
			{
				throw new MyIndexOutOfBoundsException() ;
			}
			else
			{
				
			}
			
			amount-- ;
			
			theArray[index] = 0 ;
			
			
			for(int x = index ; x < (arraySize-1) ; x++ )
			{
				theArray[x] = theArray[x+1] ;
			}
			
		}
		catch(MyIndexOutOfBoundsException ex1)
		{
			String message = ex1.getMessage() ;
			System.out.println(message) ;
			System.out.println("Shutting down") ;
			System.exit(0) ;
		}
		
		
		
		
	}
	
	public int sortedGet(int index)
	{
		try
		{
			if((index > (arraySize-1))||(index < 0 ))
			{
				throw new MyIndexOutOfBoundsException() ;
			}
			else
			{
				
			}
			
			
		}
		catch(MyIndexOutOfBoundsException ex1)
		{
			String message = ex1.getMessage() ;
			System.out.println(message) ;
			System.out.println("Shutting down") ;
			System.exit(0) ;
		}
		
		return theArray[index] ;
		
		
	
	}
	
	public int locateIndex(int value)
	{
		int answer = 0 ;
		
		for(int x = 0 ; x < arraySize ; x++)
		{
			if(theArray[x] == value)
			{
				answer = x ;
			}
			else
			{
				
			}
		}
		
		return answer ;
	}
	
	
	
}
