import java.util.Scanner;


public class MainTest 
{
	static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		
		int choice ;
		
		while(true)
		{
			
			System.out.println("\nPlease select an option");
			System.out.println("-----------------------");
			System.out.println("1) Test ArrayStructure");
			System.out.println("2) Test ADTSortedList");
			System.out.println("3) Exit");
			
			choice = getIntBetween(1,3) ;
			
			
			if(choice == 1)
			{
				testingArrayStructure() ;
			}
			else if(choice == 2)
			{
				testingSorted() ;
			}
			else
			{
				System.out.println("Shutting down");
				System.exit(0); 
			}
		}
	}
	
	public static int getInt() // gets an int from the user
	{
		int answer = 0 ;
		
		while (! keyboard.hasNextInt ())
		{
			keyboard.nextLine();
			System.out.println("That is not a number!");
			System.out.println("Please choose");
		}
		answer = keyboard.nextInt();	
		keyboard.nextLine();
		
		return answer ;
	}
	
	public static int getIntBetween(int min, int max) // gets an int between two numbers from the user
	{
		int choice = getInt() ;
		while((choice < min) || (choice > max))
		{
			System.out.println("That is not a valid number range!");
			System.out.println("Please choose from "+min+" - "+max);
			choice = getInt() ;
		}
		
		return choice ;
	}
	
	public static void testingArrayStructure() //testing Array Structure (the one with bubble sort) 
	{
		int arraySize = 10 ; // sets the size of the array
		
		ArrayStructure array = new ArrayStructure(arraySize) ;
		
		int choice = 0 ;
		
		while (choice <= 7) 
		{
			System.out.println("\nPlease select an option");
			System.out.println("-----------------------");
			System.out.println("1) Randomize the array");
			System.out.println("2) Print array");
			System.out.println("3) Get Value at index X...");
			System.out.println("4) Does array contain X...?");
			System.out.println("5) Delete at index X...");
			System.out.println("6) Insert value X...");
			System.out.println("7) Bubble sort the array");
			System.out.println("8) back to main menu");
			
			choice = getIntBetween(1,8) ;
			
			switch (choice) 
			{
			
			case 1 : System.out.println("\nRandom array made");
					array.generateRandomArray() ;
			break;
			case 2 : System.out.println("\nprinting array");
					array.printArray() ;
			break;
			case 3 : getValueAtIndexBubble(array) ;
			break;
			case 4 : containValueBubble(array);
			break;
			case 5 : delAtIndexBubble(array) ;
			break;
			case 6 : insertValueBubble(array) ;
			break;
			case 7 : array.bubbleSort();
			break;
			case 8 :
			break;
			default : System.out.println("invalid option") ;
			break;
			}
		}
	}
	
	public static void testingSorted() // tests the sorted list array. automatically generates empty list
	{
		int arraySize = 10 ; // sets the size of the array
		
		ADTSortedList array = new ADTSortedList(arraySize) ;
		
		int choice = 0 ;
		
		while (choice <= 5) 
		{
			System.out.println("\nPlease select an option");
			System.out.println("-----------------------");
			System.out.println("1) Print array");
			System.out.println("2) add value X...");
			System.out.println("3) Remove value at index X...");
			System.out.println("4) Get value at index X...");
			System.out.println("5) Locate index of X... // if it does not exists, will return 0");
			System.out.println("6) Back to main menu");
			
			choice = getIntBetween(1,6) ;
			
			switch (choice) 
			{
			
			case 1 : array.printArray();
			break;
			case 2 : addValueSorted(array);
			break;
			case 3 : removeValueAtSorted(array) ;
			break;
			case 4 : getValueatIndexSorted(array) ;
			break;
			case 5 : getValueAtIndexSorted(array) ;
			break;
			case 6 : 
			break;
			default : System.out.println("invalid option") ;
			break;
			}
		}
	}
	
	public static void getValueAtIndexBubble(ArrayStructure array) // returns the value at index of array structure
	{
		int index = 0 ;
		int value = 0 ;
		
		System.out.println("Type in the index");
		
		index = getInt() ;
		value = array.getValueAtIndex(index) ;
		
		System.out.println("The value at "+index+" is "+value);
	}
	
	public static void containValueBubble(ArrayStructure array) //checks if array structure contains a given value
	{
		System.out.println("Type in the value");
		
		int value = getInt() ;
		
		System.out.println(array.doesArrayContainThisValue(value));
	}
	
	public static void delAtIndexBubble(ArrayStructure array) //deletes value at given index from array structure
	{
		System.out.println("Type in the index");
		
		int index = getInt() ;
		
		array.deleteIndex(index);
		
		System.out.println("value deleted");
	}
	
	public static void insertValueBubble(ArrayStructure array)// inserts value into array structure
	{
		System.out.println("Type in the value to add");
		
		int value = getInt() ;
		
		array.insertValue(value);
		
		System.out.println("value added");
	}
	
	public static void addValueSorted(ADTSortedList array) // adds a value to the sorted array list
	{
		System.out.println("Type in the value to add");
		
		int value = getInt() ;
		
		array.sortAdd(value);
		
		//System.out.println("value was added");
	}
	
	public static void removeValueAtSorted(ADTSortedList array) // removes the value of the given index of the sorted list
	{
		System.out.println("Type in the index of the value to remove");
		
		int index = getInt() ;
		
		array.sortedRemove(index);
		
		System.out.println("Value was removed");
	}
	
	public static void getValueatIndexSorted(ADTSortedList array) // gets the value at the given index
	{
		System.out.println("Type in the index of the value to get");
		
		int index = getInt() ;
		
		int value = array.sortedGet(index) ;
		
		System.out.println("Value is"+value);
	}
	
	public static void getValueAtIndexSorted(ADTSortedList array) // locates the index of a value. if the array does not contain the value will return 0 
	{
		System.out.println("Type in the value");
		
		int value = getInt() ;
		
		System.out.println("value is at index"+array.locateIndex(value));
	}
}
