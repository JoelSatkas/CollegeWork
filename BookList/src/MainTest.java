import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class MainTest 
{
	static Scanner keyboard = new Scanner(System.in);
	static ArrayList<Lecturer> lecturerList = new ArrayList<Lecturer>() ;
	
	public static void main(String[] args) 
	{
		int choice = 0 ;
		while (true)
		{						
			print("\nPlease select an option");
			print("-----------------------");
			print("1) Add Lecturer");
			print("2) Find Lecturer");
			print("3) Add Book to lecturers book list");
			print("4) Remove a book from lecturers book list");
			print("5) Search for a book");
			print("6) Calculate yearly book payment");
			print("7) Output all book details to file");
			print("8) Exit");
			// Test Code
			print("TESTING OPTION (9) See all lectures and their books");
			//------------------
			
			choice = getInt();
			while((choice < 1) || (choice > 9))
			{
				print("That is not a valid number range!");
				print("Please choose from 1 - 8");
				choice = getInt();
			}
			
			print("\n\n");
			
			switch(choice)
			{
				case 1: addLecturer();
				break;
				case 2: findLecturer();
				break;
				case 3: addBook();
				break;
				case 4: removeBook();
				break;
				case 5: search();
				break;
				case 6: calculateYearlyPayment();
				break;
				case 7: output();
				break;
				case 8: keyboard.close(); 
						exit();
				break;
				case 9: for(int x = 0 ; x < lecturerList.size() ; x++)
						{
							lecturerList.get(x).print() ;
						}
				break;
				default: print("ERROR");
				break;
			}
		}
	}
	
	public static void addLecturer()
	{
		String name = "" ;
		int id = 0 ;
		
		print("Please enter the name of this new Lecturer");
		name = keyboard.nextLine();
		
		print("Please enter their ID");
		id = getInt();
		
		Lecturer newLectur = new Lecturer(name, id) ;
		lecturerList.add(newLectur);
	}
	
	public static void findLecturer()
	{
		if(lecturerList.size() > 0)
		{
			int id = 0 ;
			int newID = 0 ;
			String name = "" ;
			
			print("Please enter the ID the Lecturer");
			id = getInt();
			
			for(int x = 0 ; x < lecturerList.size(); x++)
			{
				newID = lecturerList.get(x).getID() ;
				if(newID == id)
				{
					name = lecturerList.get(x).getName();
				}
			}
			
			if(name.isEmpty())
			{
				print("No such lecturer exists");
			}
			else
			{
				print(name+" has the id of "+id);
			}
		}
		else
		{
			print("No lecture in List");
		}
	}
	
	public static void addBook()
	{
		if(lecturerList.size() > 0)
		{
			String name = "" ;
			int id = 0 ;
			String bookName = "" ;
			String BookAuthor = "" ;
			int bookISBN = 0 ;
			double bookPrice = 0.0 ;
			int lecturesPosition = 0 ;
			int count = 0 ;
			
			print("Please enter the name or the ID of the lecturer");
			
			if (!keyboard.hasNextInt())
			{
				name = keyboard.nextLine();
				print("variable is String");
			}
			else
			{
				id = keyboard.nextInt();
				keyboard.nextLine();
				print("variable is int");
			}
			
			if(!name.isEmpty())
			{
				count = 0 ;
				String newName = "" ;
				for(int x = 0 ; x < lecturerList.size(); x++)
				{
					newName = lecturerList.get(x).getName() ;
					if(newName.equals(name))
					{
						id = lecturerList.get(x).getID();
						lecturesPosition = x ;
					}
					else
					{
						count++ ;
					}
				}
			}
			else if(id != 0)
			{
				count = 0 ;
				int newID = 0 ;
				for(int x = 0 ; x < lecturerList.size(); x++)
				{
					newID = lecturerList.get(x).getID() ;
					if(newID == id)
					{
						name = lecturerList.get(x).getName();
						lecturesPosition = x ;
					}
					else
					{
						count++ ;
					}
				}
			}
			else
			{
				print("ERROR");
			}
			
			if(count == lecturerList.size())
			{
				print("Lecturer not found");
			}
			else
			{
				print("Lecturer found");
				print("Name: "+name+"\nID :"+id+"\n");
				
				print("Please enter the name of the book");
				bookName = keyboard.nextLine();
			
				print("Please enter the Author of the book");
				BookAuthor = keyboard.nextLine();
			
				print("Please enter the price of the book");
				while (! keyboard.hasNextDouble())
				{
					keyboard.nextLine();
					print("That is not a valid number!");
					print("Please choose");
				}
				bookPrice = keyboard.nextDouble();
				keyboard.nextLine();
				
				print("Please enter the ISBN code of the book");
				
				bookISBN = getInt();
				
				Book newBook = new Book(bookName, bookISBN, BookAuthor, bookPrice);
				
				lecturerList.get(lecturesPosition).addBook(newBook) ;
			}
		}
		else
		{
			print("No lecture in List");
		}
	}
	
	public static void removeBook()
	{
		if(lecturerList.size() > 0)
		{
			String name = "" ;
			int id = 0 ;
			int lecturesPosition = 0 ;
			boolean end = false ;
			boolean removed = false ;
			int count = 0 ;
			
			int bookISBN = 0 ;
			
			print("Please enter the name or the ID of the lecturer");
			
			if (! keyboard.hasNextInt())
			{
				name = keyboard.nextLine();
				print("variable is String");
			}
			else
			{
				id = keyboard.nextInt();
				keyboard.nextLine();
				print("variable is int");
			}
			
			if(! name.isEmpty())
			{
				count = 0 ;
				String newName = "" ;
				for(int x = 0 ; x < lecturerList.size(); x++)
				{
					newName = lecturerList.get(x).getName() ;
					if(newName.equals(name))
					{
						id = lecturerList.get(x).getID();
						lecturesPosition = x ;
					}
					else
					{
						count++ ;
					}
				}
			}
			else if(id != 0)
			{
				count = 0 ;
				int newID = 0 ;
				for(int x = 0 ; x < lecturerList.size(); x++)
				{
					newID = lecturerList.get(x).getID() ;
					if(newID == id)
					{
						name = lecturerList.get(x).getName();
						lecturesPosition = x ;
					}
					else
					{
						count++ ;
					}
				}
			}
			else
			{
				print("ERROR");
			}
			
			if(count == lecturerList.size())
			{
				print("Lecturer not found");
			}
			else
			{
				BookList bookList = lecturerList.get(lecturesPosition).getBookList();
				Book resultBook ;
				
				while(end == false)
				{
					print("Please enter the ISBN number of the book you wish to remove");
					bookISBN = getInt();
					
					resultBook = bookList.search(bookISBN);
					
					if(resultBook == null)
					{
						String answer = "" ;
						
						print("Book not found");
						print("would you like to search again? Y/N");
						answer = keyboard.nextLine().toLowerCase();
						answer = answer.charAt(0)+"" ;
						
						if(!answer.equals('y'+""))
						{
							end = true ;
						}
					}
					else
					{
						print("Book found");
						print("Removing book ....");
						
						removed = bookList.removeBook(bookISBN);
						
						if(removed == false)
						{
							print("ERROR: FAILED");
							end = true ;
						}
						else
						{
							end = true ;
						}
					}
				}
			}
		}
		else
		{
			print("No lecture in List");	
		}
	}

	public static void search()
	{
		int ISBN = 0 ;
		Book resultBook = null ;
		Book testBook = null ;
		BookList bookList ;
		
		print("Please enter the ISBN number of the book your looking for");
		ISBN = getInt();
		
		for(int x = 0 ; x < lecturerList.size() ; x++)
		{
			bookList = lecturerList.get(x).getBookList();
			testBook = bookList.search(ISBN);
			
			if(testBook != null)
			{
				resultBook = testBook ;
			}
		}
		
		if(resultBook != null)
		{
			print("Tilte: "+resultBook.getTitle()+"\nAuthor: "+resultBook.getAuthor()+"\nISBN: "+resultBook.getISBN()
					+"\nPrice: "+resultBook.getPrice());
		}
		else
		{
			print("Book not found");
		}
	}
	
	public static void calculateYearlyPayment()
	{
		BookList bookList ;
		double total = 0.0 ;
		
		for(int x = 0 ; x < lecturerList.size() ; x++)
		{
			bookList = lecturerList.get(x).getBookList();
			print("Name: "+lecturerList.get(x).getName()+"	ID: "+lecturerList.get(x).getID()+" Total: $"+bookList.calculateYearlyBookPayment());
			total += bookList.calculateYearlyBookPayment() ;
		}
		
		print("____________________________");
		print("Yearly Sum: $"+total);
		print("\n") ;
	}
	
	public static void output()
	{
		try
		{
			PrintWriter outputFile = new PrintWriter("output.txt") ;
			String answer = "" ;
			for(int x = 0 ; x < lecturerList.size() ; x++)
			{
				answer = lecturerList.get(x).toString() ;
				outputFile.println(answer) ;
			}
			outputFile.close();
			print("output file created");
		}
		catch(IOException obj)
		{
			print("Error, could not create a output file");
		}
		
	}
	
	public static void exit()
	{
		print("SHUTTING DOWN...");
		System.exit(0) ;
	}
	
	private static int getInt() //Gets an integer from the user.
	{
		int answer = 0;
		
		while (! keyboard.hasNextInt())
		{
			keyboard.nextLine();
			print("That is not a number!");
			print("Please choose a valid number");
		}
		answer = keyboard.nextInt();	
		keyboard.nextLine();
		
		return answer;
	} 
	
	private static void print(String message)
	{
		System.out.println(message);
	} 
}
