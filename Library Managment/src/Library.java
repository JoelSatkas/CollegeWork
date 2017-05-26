import java.util.ArrayList;
import java.util.Scanner;

import List.List;


public class Library implements ADTInterface 
{
	//private ArrayList<Book> bookList ;
	private List list ;
	
	static Scanner keyboard = new Scanner(System.in);
	
	public Library()
	{
		//bookList = new ArrayList<Book>() ;
		list = new List() ;
	}
	
	
	public void add() 
	{
		//bookList.add(new Book("",0,"",0.0,0,""));
		list.insertAtBack(new Book("",0,"",0.0,0,""));
	}

	@Override
	public void add(String T, int num, String A, double P, int ed, String pub) 
	{
		//bookList.add(new Book(T,num,A,P,ed,pub));
		list.insertAtBack(new Book(T,num,A,P,ed,pub));
	}

	@Override
	public void remove(int index) 
	{
		if(index >= list.getAmount())
		{
			System.out.println("index out of bound") ;
		}
		else
		{
			//bookList.remove(index) ;
			list.remove(index);
		}
	}

	@Override
	public void edit(int index) 
	{
		int choice = 0 ;
		Book book = null ;
		
		System.out.println("\nPlease select an option");
		System.out.println("-----------------------");
		System.out.println("1) Title");
		System.out.println("2) Author");
		System.out.println("3) Price");
		System.out.println("4) ISBN");
		System.out.println("5) Edition");
		System.out.println("6) Publisher");
		
		choice = Main.getIntBetween(1, 6) ;
		
		if(choice == 1)
		{
			String title = "" ;
			System.out.println("Enter new title");
			
			title = keyboard.nextLine() ;
			//bookList.get(index).setTitle(title);
			if(list.get(index) instanceof Book)
			{
				book = (Book) list.get(index) ;
			}
			
			book.setTitle(title) ;
		}
		else if(choice == 2)
		{
			String Author = "" ;
			System.out.println("Enter new Author");
			
			Author = keyboard.nextLine() ;
			//bookList.get(index).setAuthor(Author);
			if(list.get(index) instanceof Book)
			{
				book = (Book) list.get(index) ;
			}
			
			book.setAuthor(Author) ;
		}
		else if(choice == 3)
		{
			double price = 0.0 ;
			System.out.println("Enter new price");
			
			price = Main.getPosDouble() ;
			
			if(list.get(index) instanceof Book)
			{
				book = (Book) list.get(index) ;
			}
			
			book.setPrice(price); ;
		}
		else if(choice == 4)
		{
			int num = 0 ;
			System.out.println("Enter new ISBN");
			
			num = Main.getInt() ;
			
			if(list.get(index) instanceof Book)
			{
				book = (Book) list.get(index) ;
			}
			
			book.setISBN(num);
		}
		else if(choice == 5)
		{
			int ed = 0 ;
			System.out.println("Enter new edition");
			
			ed = Main.getInt() ;
			//bookList.get(index).setEdition(ed);
			
			if(list.get(index) instanceof Book)
			{
				book = (Book) list.get(index) ;
			}
			
			book.setEdition(ed);
		}
		else if(choice == 6)
		{
			String pub = "" ;
			System.out.println("Enter new publisher");
			
			pub = keyboard.nextLine() ;
			//bookList.get(index).setPublisher(pub);
			
			if(list.get(index) instanceof Book)
			{
				book = (Book) list.get(index) ;
			}
			
			book.setPublisher(pub) ;
		}
	}
	
	public boolean search(Book bk)
	{
		boolean answer = false ;
		Book book = null ;
		
		String t = bk.getTitle() ;
		String a = bk.getAuthor() ;
		double pr = bk.getPrice() ;
		String pu = bk.getPublisher() ;
		int e = bk.getEdition() ;
		int isbn = bk.getISBN() ;
		
		boolean sameA = false ;
		boolean sameT = false ;
		boolean samePR = false ;
		boolean samePU = false ;
		boolean sameE = false ;
		boolean sameISBN = false ;
		
		for(int x = 0 ; x < list.getAmount() ; x++)
		{
			if (t.equals(((Book)list.get(x)).getTitle()))
			{
				sameT = true ;
			}
			
			if (a.equals(((Book)list.get(x)).getAuthor()))
			{
				sameA = true ;
			}
			
			if (pr == ((Book)list.get(x)).getPrice())
			{
				samePR = true ;
			}
			
			if (pu.equals(((Book)list.get(x)).getPublisher()) )
			{
				samePU = true ;
			}
			
			if (e == ((Book)list.get(x)).getEdition())
			{
				sameE = true ;
			}
			
			if (isbn == ((Book)list.get(x)).getISBN())
			{
				sameISBN = true ;
			}
			
			if(sameA&&sameT&&samePR&&samePU&&sameE&&sameISBN)
			{
				answer = true ;
			}
		}
		
		return answer ;
	}
	
	public int searchIndex(Book bk)
	{
		int answer = 0 ;
		
		String t = bk.getTitle() ;
		String a = bk.getAuthor() ;
		double pr = bk.getPrice() ;
		String pu = bk.getPublisher() ;
		int e = bk.getEdition() ;
		int isbn = bk.getISBN() ;
		
		boolean sameA = false ;
		boolean sameT = false ;
		boolean samePR = false ;
		boolean samePU = false ;
		boolean sameE = false ;
		boolean sameISBN = false ;
		
		for(int x = 0 ; x < list.getAmount() ; x++)
		{
			if (t.equals(((Book)list.get(x)).getTitle()))
			{
				sameT = true ;
			}
			
			if (a.equals(((Book)list.get(x)).getAuthor()))
			{
				sameA = true ;
			}
			
			if (pr == ((Book)list.get(x)).getPrice())
			{
				samePR = true ;
			}
			
			if (pu.equals(((Book)list.get(x)).getPublisher()) )
			{
				samePU = true ;
			}
			
			if (e == ((Book)list.get(x)).getEdition())
			{
				sameE = true ;
			}
			
			if (isbn == ((Book)list.get(x)).getISBN())
			{
				sameISBN = true ;
			}
			
			if(sameA&&sameT&&samePR&&samePU&&sameE&&sameISBN)
			{
				answer = x ;
			}
		}
		
		return answer ;
	}
	
	public String print()
	{
		String answer = "" ;
		
		for(int x = 0 ; x < list.getAmount() ; x++)
		{
			answer += "\n" ;
			answer += ((Book)list.get(x)).getTitle() + " " ;
			answer += ((Book)list.get(x)).getPrice() + " " ;
			answer += ((Book)list.get(x)).getISBN() + " " ;
			answer += ((Book)list.get(x)).getAuthor() + " " ;
			answer += ((Book)list.get(x)).getEdition() + " " ;
			answer += ((Book)list.get(x)).getPublisher() + " " ;
			
		}
		return answer ;
	}
	
	
	
}
