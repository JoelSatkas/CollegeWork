import java.util.Scanner;


public class ArraySearch 
{

	static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		
		int[] arr1 = {2,45,234,567,876,900,976,999} ;
		
		System.out.println("Enter the number to find") ;
		int position = getInt() ;
		
		System.out.println(recursiveSearch(arr1, 0, position)) ;
		
		System.out.println(binarySearch(arr1,position,arr1.length)) ;
		
		//binarySearch(arr1,position,arr1.length) ;
	}

	private static int recursiveSearch(int[] arr1, int start, int searchNum) 
	{
		// arr1 is the array of ints
		// start in the current array index you will examine
		// searchNum is the number you are searching for within the array
		
		
		if(start >= arr1.length)
		{
			return -1 ;
		}
		
		if(arr1[start] == searchNum)
		{
			
			return start ;
		}
		else
		{
			return recursiveSearch(arr1, (++start),searchNum) ;
		}
	}

	public static int getInt()
	{
		int choice = 0 ;
		
		while (! keyboard.hasNextInt())
		{
			keyboard.nextLine();
			System.out.println("That is not a valid number!");
			System.out.println("Please choose");
		}
		choice = keyboard.nextInt();
		
		keyboard.nextLine();
		
		return choice ;
	}
	
	public static int binarySearch(int[] arr, int searchNum, int length)
	{
		int half = (int)(length/2) ;
		
		System.out.println(half);
		
		if(half == length)
		{
			return -1 ;
		}
		
		if(arr[half] == searchNum)
		{
			return half ;
		}
		else
		{
			if(arr[half] > searchNum)
			{
				return binarySearch(arr, searchNum, half) ;
			}
			else
			{
				return binarySearch(arr, searchNum, (half + arr.length)) ;
			}
		}
	}
}
