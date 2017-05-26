
public class Book 
{
	private String title ;
	private int ISBN ;
	private String author ;
	private double price ;
	
	public Book(String T, int num, String A, double P)
	{
		title = T ;
		ISBN = num ;
		author = A ;
		price = P ;
	}
	
	public double getPrice()
	{
		return price ;
	}
	
	public int getISBN()
	{
		return ISBN ;
	}
	
	public String getTitle()
	{
		return title ;
	}
	
	public String getAuthor()
	{
		return author ;
	}
}
