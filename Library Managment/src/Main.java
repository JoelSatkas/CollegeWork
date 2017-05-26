import java.util.Scanner;


public class Main 
{
	static Library lib = new Library() ;
	static Scanner keyboard = new Scanner(System.in);
	
	public static void main(String[] args) 
	{
		
		int choice = 0 ;
		final int MAX = 6 ;
		final int MIN = 1 ;
		
		while(true)
		{
			System.out.println("\nPlease select an option");
			System.out.println("-----------------------");
			System.out.println("1) Add Book to Library");
			System.out.println("2) Remove a book from Library");
			System.out.println("3) See if library contains book");
			System.out.println("4) find index of book");
			System.out.println("5) edit a book");
			// Test Code
			System.out.println("TESTING OPTION (6) See all books");
			//------------------
			
			
			choice = getIntBetween(MIN, MAX);
			
			if(choice == 1)
			{
				add() ;
			}
			else if(choice == 2)
			{
				remove() ;
			}
			else if(choice == 3)
			{
				searchYesNo() ;
			}
			else if(choice == 4)
			{
				searchLocation() ;
			}
			else if(choice == 5)
			{
				edit() ;
			}
			else
			{
				print();
			}
		}
		
		
	}
	
	public static double getPosDouble()
	{
		double num = getDouble() ;
		
		while(num < 0)
		{
			System.out.println("number must be positive");
			System.out.println("Please try again");
			num = getDouble() ;
		}
		
		return num ;
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
	
	public static int getIntBetween(int min, int max)
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
	
	public static double getDouble()
	{
		double choice = 0.0 ;
		
		while (! keyboard.hasNextDouble())
		{
			keyboard.nextLine();
			System.out.println("That is not a valid number!");
			System.out.println("Please choose");
		}
		choice = keyboard.nextInt();
		keyboard.nextLine();
		
		
		
		return choice ;
	}
	
	public static void print()
	{
		System.out.println("Title\tPrice\tISBN\tAuthor\tEdition\tPublisher"+lib.print()) ;
	}
	
	public static void add()
	{
		
		String title = "" ;
		String author = "" ;
		double price = 0.0 ;
		int ISBN = 0 ;
		int edition = 0 ;
		String publisher = "" ;
		
		System.out.println("Enter the title");
		title = keyboard.nextLine();
		
		System.out.println("Enter the Author");
		author = keyboard.nextLine() ;
		
		System.out.println("Enter the price");
		price = getPosDouble() ;
		
		System.out.println("Enter the ISBN");
		ISBN = getInt() ;
		
		System.out.println("Enter the edition");
		edition = getInt() ;
		
		System.out.println("Enter the publisher");
		publisher = keyboard.nextLine() ;
		
		lib.add(title, ISBN, author, price, edition, publisher);
		
		
	}
	
	public static void remove()
	{
		
		int choice = 0 ;
		System.out.println("Enter the the book to remove");
		choice = getInt() ;
		
		lib.remove(choice) ;
		
		
	}
	
	public static void edit()
	{
		System.out.println("Which book to edit?");
		int index = getInt() ;
		
		lib.edit(index);
		
		
		
	}
	
	public static Book search()
	{
		String title = "" ;
		String author = "" ;
		double price = 0.0 ;
		int ISBN = 0 ;
		int edition = 0 ;
		String publisher = "" ;
		
		
		System.out.println("Enter the title");
		title = keyboard.nextLine();
		
		System.out.println("Enter the Author");
		author = keyboard.nextLine() ;
		
		System.out.println("Enter the price");
		price = getPosDouble() ;
		
		System.out.println("Enter the ISBN");
		ISBN = getInt() ;
		
		System.out.println("Enter the edition");
		edition = getInt() ;
		
		System.out.println("Enter the publisher");
		publisher = keyboard.nextLine() ;
		
		Book newBook = new Book(title, ISBN, author, price, edition, publisher);
		
		return newBook ;
	}

	public static void searchLocation()
	{
		Book theBook = search() ;
		int index = lib.searchIndex(theBook);
		
		System.out.println("This book is in position "+index);
	}
	
	public static void searchYesNo()
	{
		Book theBook = search() ;
		boolean isThere = false ;
		
		isThere = lib.search(theBook);
		
		if(isThere == true)
		{
			System.out.println("This book is in the library");
		}
		else
		{
			System.out.println("This book is not in the library");
		}
	}
	
}
