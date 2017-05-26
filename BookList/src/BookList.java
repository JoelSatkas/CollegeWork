
public class BookList extends ObjectList
{
	public BookList(int size)
	{
		super(size);
	}
	
	public Book getBook(int number)
	{
		Book book = (Book) getObject(number);
		return book ;
	}
	
	public Book search(int ISBN) // ISBN
	{
		Book searchBook = null ;
		Book resultBook = null ;
		int searchISBN ;
		int count = getTotal() ;
		
		// If 2 books have same name, returns latter book in array
		
		for(int x = 0 ; x < count ; x++)
		{
			searchBook = (Book) getObject(x);
			searchISBN = searchBook.getISBN();
			
			if(searchISBN == ISBN)
			{
				resultBook = searchBook ;
			}
		}
		return resultBook ;
	}
	
	public boolean removeBook(int ISBN)
	{
		Book searchBook ;
		int searchISBN ;
		int loop = getTotal() ;
		int position = 0 ;
		boolean success  = false ;
		
		// if same title, will give back last book in array
		
		for(int x = 0 ; x < loop ; x++)
		{
			searchBook = (Book) getObject(x);
			searchISBN = searchBook.getISBN();
			
			if(searchISBN == ISBN)
			{
				position = x ;
			}
		}
		success = remove(position);
		return success ;
	}
	
	public double calculateYearlyBookPayment()
	{
		int loop = getTotal() ;
		double total = 0.0 ;
		Book book;
		
		for(int x = 0 ; x < loop ; x++)
		{
			book = (Book) getObject(x) ;
			total += book.getPrice() ;
		}
		return total ;
	}
}
