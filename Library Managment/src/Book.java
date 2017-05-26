
public class Book 
{
	private String title ;
	private int ISBN ;
	private String author ;
	private double price ;
	private int edition ;
	private String publisher ;
	
	public Book(String T, int num, String A, double P, int ed, String pub)
	{
		title = T ;
		ISBN = num ;
		author = A ;
		price = P ;
		edition = ed ;
		publisher = pub ;
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
	
	public int getEdition()
	{
		return edition ;
	}
	
	public String getPublisher()
	{
		return publisher ;
	}
	
	public void setPrice(double P)
	{
		price = P ;
	}
	
	public void setISBN(int num)
	{
		ISBN = num ;
	}
	
	public void setTitle(String T)
	{
		title = T ;
	}
	
	public void setAuthor(String A)
	{
		author = A ;
	}
	
	public void setEdition(int ed)
	{
		edition = ed ;
	}
	
	public void setPublisher(String pub)
	{
		publisher = pub ;
	}
	
}
