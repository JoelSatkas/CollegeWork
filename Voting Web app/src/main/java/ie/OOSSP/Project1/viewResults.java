package ie.OOSSP.Project1;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class viewResults
 */
public class viewResults extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public viewResults() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Cookie cookies[];
		Cookie voteCookie = null;
		Cookie candidatesCookie = null;
		cookies = request.getCookies(); // get client's cookies
		
		if(cookies == null)
		{
			System.out.println("cookie == null");
			request.getRequestDispatcher("/CookieNotFound.jsp").forward(request, response);
		}
		else
		{
			
			for ( int i = 0; i < cookies.length; i++ ) 
	        {
	        	if(cookies[i].getName().equals("candidatesCookie"))
	        	{
	        		candidatesCookie = cookies[i];
	        	}
	        	
	        	if(cookies[i].getName().equals("voteCookie"))
	        	{
	        		voteCookie = cookies[i];
	        	}
	        }
			
			if((candidatesCookie == null) || (voteCookie == null))
			{
				System.out.println("candidatesCookie == null || voteCookie == null");
				request.getRequestDispatcher("/CookieNotFound.jsp").forward(request, response);
			}
			else // if all cookies that are required exists
			{
				// check if the amount of candidates in voting cookie matches the amount of candidates in candidate cookie just to make sure it doesnt crash.
				// candidate amount could be the same but different candidates. This is just to make sure there are no indexOutOfBound exceptions.
				String candidateCookieValue = candidatesCookie.getValue();
				request.setAttribute("candidates", candidatesCookie.getValue());
				
				candidateBean canBean = new candidateBean();
				canBean.sort(candidateCookieValue); // return doesnt matter as it cant be false if its this far in the code.
				
				int beanCount = canBean.getSize();
				
				String voteCookieValue = voteCookie.getValue();
				canBean.sort(voteCookieValue);
				
				int voteCount = Integer.parseInt(canBean.getCandidateStringarray()[0]);
				
				if(beanCount != voteCount)
				{
					request.getRequestDispatcher("/oldCookie.jsp").forward(request, response);
				}
				else
				{
					int totalVotes = canBean.getSize()-1;
					int validVotes = 0;
					int invalidVotes = 0;
					ArrayList<String> tempVotes = new ArrayList<String>();
					String[] correctVotes = null;
					String[] votesStrings = canBean.getCandidateStringarray();
					
					System.out.println("total Votes: "+totalVotes);
					
					for(int x = 1 ; x < (totalVotes+1) ; x++)
					{
						if(isValid(votesStrings[x],beanCount))
						{
							System.out.println("valid vote");
							validVotes++;
							tempVotes.add(votesStrings[x]);
						}
						else
						{
							System.out.println("invalid vote");
							invalidVotes++;
						}
					}
					
					System.out.println((invalidVotes+ validVotes) == totalVotes);
					
					correctVotes = new String[tempVotes.size()];
					correctVotes = tempVotes.toArray(correctVotes);
					
					request.setAttribute("totalVotes", (""+totalVotes));
					request.setAttribute("validVotes", (""+validVotes));
					request.setAttribute("invalidVotes", (""+invalidVotes));
					request.setAttribute("Votes", correctVotes);
					
					request.getRequestDispatcher("/results.jsp").forward(request, response);
					
				}
				
				
			}
		}
		
	}
	
	public boolean isValid(String vote, int amount)
	{
		String[] votes = new String[amount];
		String singleVote = "";
		int position = 0;
		
		// brake down the string into separate votes.
		for(int x = 0 ; x < vote.length() ; x++)
		{
			singleVote += vote.charAt(x);

			if(vote.charAt(x+1) == ':')
			{
				votes[position] = singleVote;
				singleVote = "";
				position++;
				x++;
			}
			else
			{
				
			}
		}
		
		int[] intVotes = new int[amount];
		int nonNullVotes = 0;
		
		// convert String array to int array for comparison.
		for(int x = 0 ; x < amount ; x++)
		{
			if(votes[x] == null || votes[x].equals("null"))
			{
				intVotes[x] = -1;
			}
			else
			{
				intVotes[x] = Integer.parseInt(votes[x]);
				nonNullVotes++;
			}
			
		}
		
		System.out.println("");
		
		if(nonNullVotes != 0)
		{
			int start = -1;
			boolean sequential = true;
			boolean foundStart = false;
			boolean copy = false;
			
			for(int x = 0 ; x < nonNullVotes ; x++)
			{
				start++;
				copy = false;
				for(int y = 0 ; y < amount ; y++)
				{
					if(intVotes[y] == start)
					{
						foundStart = true;
						
						if(copy == true)
						{
							sequential = false;
						}
						
						copy = true;
					}
				}
				
				if(foundStart == false)
				{
					sequential = false;
				}
				
				if(foundStart == true)
				{
					foundStart = false;
				}
			}
			
			return sequential;
		}
		else
		{
			return false;
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
