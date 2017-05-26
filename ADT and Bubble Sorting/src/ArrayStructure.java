import java.util.Random;


public class ArrayStructure 
{
	static Random generator = new Random();
	
	private int[] theArray ;
	private int arraySize ;
	
	private static final int MINIMUM_RANDOM_NUMBER = 1 ;
	private static final int MAXIMUM_RANDOM_NUMBER = 100 ;
	
	public ArrayStructure()
	{
		this(10) ;
	}
	
	public ArrayStructure(int size)
	{
		arraySize = size ;
		
		theArray = new int[arraySize] ;
	}
	
	public void generateRandomArray()
	{
		int randomIndex = 0 ;
		
		for(int x = 0 ; x < arraySize ; x++)
		{
			randomIndex = generator.nextInt(( MAXIMUM_RANDOM_NUMBER + 1 ) - MINIMUM_RANDOM_NUMBER ) + MINIMUM_RANDOM_NUMBER ;
			
			theArray[x] = randomIndex ;
			
		}
	}
	
	public int[] getTheArray()
	{
		return theArray.clone() ;
	}
	
	public int getArraySize()
	{
		return arraySize ;
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
	
	public int getValueAtIndex(int index)
	{
		int answer = 0 ;
		
		try
		{
			if((index > (arraySize-1))||(index < 0 ))
			{
				throw new MyIndexOutOfBoundsException() ;
			}
			else
			{
				
			}
			
			if(theArray[index] == 0)
			{
				throw new NoValueContainedException() ;
			}
			
			return theArray[index] ;
		}
		catch(MyIndexOutOfBoundsException ex1)
		{
			String message = ex1.getMessage() ;
			System.out.println(message) ;
			System.out.println("Shutting down") ;
			System.exit(0) ;
		}
		catch(NoValueContainedException ex2)
		{
			String message2 = ex2.getMessage() ;
			System.out.println(message2) ;
			System.out.println("Shutting down") ;
			System.exit(0) ;
		}
		return answer;
	}
	
	public String doesArrayContainThisValue(int value)
	{
		String answer = "" ;
		int search = 0 ;
		
		for(int x = 0 ; x < arraySize ; x++)
		{
			if(theArray[x] == value)
			{
				search++ ;
			}
			else
			{
				
			}
		}
		
		if(search > 0)
		{
			answer = "Yes" ;
		}
		else
		{
			answer = "No" ;
		}
		
		return answer ;
	}
	
	public void deleteIndex(int index)
	{
		try
		{
			if((index > arraySize)||(index < 0 ))
			{
				throw new MyIndexOutOfBoundsException() ;
			}
			
			theArray[index] = 0 ;
		}
		catch(MyIndexOutOfBoundsException ex1)
		{
			String message = ex1.getMessage() ;
			System.out.println(message) ;
			System.out.println("Shutting down") ;
			System.exit(0) ;
		}
	}
	
	public void insertValue(int value)
	{
		try
		{
			int freeSpace = 0 ;
			int position = 0 ;
			
			for(int x = 0 ; x < arraySize ; x++)
			{
				if(theArray[x] == 0)
				{
					freeSpace++ ;
					position = x ;
				}
				else
				{
					
				}
			}
			
			if(freeSpace == 0)
			{
				throw new ListIsFullException();
			}
			else
			{
				
			}
			
			theArray[position] = value ;
			
		}
		catch(ListIsFullException ex3)
		{
			String message3 = ex3.getMessage() ;
			System.out.println(message3) ;
			System.out.println("Shutting down") ;
			System.exit(0) ;
		}
	}
	
	public boolean linearSearchForValue(int value)
	{
		boolean answer = false ;
		int search = 0 ;
		
		for(int x = 0 ; x < arraySize ; x++)
		{
			if(theArray[x] == value)
			{
				search++ ;
			}
			else
			{
				
			}
		}
		
		if(search > 0)
		{
			answer = true ;
		}
		else
		{
			answer = false ;
		}
		
		return answer ;
	}
	
	public void bubbleSort()
	{
		int firstValue = 0 ;
		int secondValue = 0 ;
		boolean changes = false ;
		boolean sorted = false ;
		
		while(sorted == false)
		{
			for(int x = 0 ; x < arraySize-1 ; x++)
			{
				firstValue = theArray[x] ;
				secondValue = theArray[x+1] ;
				
				if(secondValue < firstValue)
				{
					swapValues(x,x+1);
					changes = true ;
				}
				else
				{
					
				}
				
			}
			
			if(changes == false )
			{
				sorted = true ;
			}
			changes = false ;
		}
		
		
	}
	
	private void swapValues(int first, int second)
	{
		
		int firstValue = theArray[first] ;
		int secondValue = theArray[second] ;
		
		theArray[first] = secondValue ;
		theArray[second] = firstValue ;
	}
	
	
	
}
