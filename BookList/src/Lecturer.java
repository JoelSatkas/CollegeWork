
public class Lecturer 
{
	private String name ;
	private int ID ;
	final int MAX_NUMBER_OF_BOOKS = 15 ;
	private BookList bookList ;
	
	public Lecturer(String N, int newID)
	{
		name = N ;
		ID = newID ;
		bookList = new BookList(MAX_NUMBER_OF_BOOKS);
	}
	
	public String getName()
	{
		return name ;
	}
	
	public int getID()
	{
		return ID ;
	}
	
	public void addBook(Book newBook)
	{
		if(bookList.isFull())
		{
			System.out.println("Cannot add any more books");
		}
		else
		{
			boolean success  = false ;
			
			// may not be necessary as this is creating objects with no reference every time a book is created
			// if device has small amount of memory, method should use reference to main object instead of creating another object
			String bookName = newBook.getTitle() ;
			String autoher = newBook.getAuthor() ;
			int ISBN = newBook.getISBN() ;
			double price = newBook.getPrice() ;
			
			Book book = new Book(bookName, ISBN, autoher, price);
			success = bookList.add(book);
			
			if(success == true)
			{
				System.out.println("Book was added");
			}
			else
			{
				System.out.println("Cannot add any more books");
			}
		}
	}
	
	public BookList getBookList()
	{
		return bookList ;
	}
	
	public void print()
	{
		int count = bookList.getTotal();
		String answer = "" ;
		Book testBook ;
		
		System.out.println("Lecture name: "+name+" ID: "+ID+"\n");
		
		for(int x = 0 ; x < count ; x++)
		{
			testBook = bookList.getBook(x);
			answer = "Title: " + testBook.getTitle() + "\nAuthor: " + testBook.getAuthor() +"\nISBN: " + testBook.getISBN() + "\nPrice: " + testBook.getPrice()  ;
			System.out.println(answer+"\n");
		}
	}
	
	public String toString()
	{
		String answer = "" ;
		int count = bookList.getTotal();
		
		Book testBook ;
		answer += ("Lecture: Name: "+name+" ID: "+ID+"\n");
		
		for(int x = 0 ; x < count ; x++)
		{
			testBook = bookList.getBook(x);
			answer += "Title: " + testBook.getTitle() + "\nAuthor: " + testBook.getAuthor() +"\nISBN: " + testBook.getISBN() + "\nPrice: " + testBook.getPrice()  ;
			answer += ("\n");
		}
		return answer ;
	}
}
