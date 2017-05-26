package ie.OOSSP.Project1;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class vote
 */
public class vote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public vote() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Cookie cookies[];
		Cookie candidatesCookie = null;
		cookies = request.getCookies(); // get client's cookies
		boolean found = false;
		
		if(cookies != null)
		{
			for ( int i = 0; i < cookies.length; i++ ) 
	        {
	        	if(cookies[i].getName().equals("candidatesCookie"))
	        	{
	        		candidatesCookie = cookies[i];
	        		found = true;
	        	}	        	 
	        }
	        
	        if(found == false)
	        {
	        	request.getRequestDispatcher("/CookieNotFound.jsp").forward(request, response);
	        }
	        else // found candidate cookie
	        {
	        	request.setAttribute("candidates", candidatesCookie.getValue());
	        	request.getRequestDispatcher("/vote.jsp").forward(request, response);
	        }
		}
		else
		{
			request.getRequestDispatcher("/CookieNotFound.jsp").forward(request, response);
		}
		
        
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Cookie cookies[];
		Cookie voteCookie = null;
		cookies = request.getCookies(); // get client's cookies
		boolean found = false;
		
		// validation of cookie should be done here, since invalid votes are desired, they can be validated in the display votes page/servlet
		if(cookies == null)// if no cookies present. make new cookie
		{
			String value = "" + request.getParameter("candidateNum") + ".";
			value += getStringVoteForCookie(request);
			
			voteCookie = new Cookie("voteCookie", value);
			voteCookie.setMaxAge(60*60*24*7*4*12);
			
			response.addCookie(voteCookie);
		}
		else //try to find vote cookie
		{
			for ( int i = 0; i < cookies.length; i++ ) 
	        {
	        	if(cookies[i].getName().equals("voteCookie"))// if found, add onto vote cookie
	        	{
	        		voteCookie = cookies[i];
	        		found = true;
	        	}
	        }
			
			if(found == false) // if cant find => first vote, therefore make new vote cookie
			{
				String value = "" + request.getParameter("candidateNum") + ".";
				value += getStringVoteForCookie(request);
				
				voteCookie = new Cookie("voteCookie", value);
				voteCookie.setMaxAge(60*60*24*7*4*12);
				
				response.addCookie(voteCookie);
			}
			else
			{
				String value = voteCookie.getValue();
				value += getStringVoteForCookie(request);
				
				voteCookie = new Cookie("voteCookie", value);
				voteCookie.setMaxAge(60*60*24*7*4*12);
				
				response.addCookie(voteCookie);
			}

		}
		
                
		request.getRequestDispatcher("/afterVote.jsp").forward(request, response);
	}
	
	private String getStringVoteForCookie(HttpServletRequest request)
	{
		String voteString = "";
		
		System.out.println("Making string for cookie");
		System.out.println("amount of candidates: " + (String)request.getParameter("candidateNum"));
		
		// x will be row number. vote+x will return which candidate got that position.
		// example: vote3 will return the Nth candidate who got the 4th position. null == was not selected
		
		for(int x = 0 ; x < Integer.parseInt(request.getParameter("candidateNum")); x++)
		{
			voteString += (String)request.getParameter("vote"+x)+":";
			System.out.println((String)request.getParameter("vote"+x));
		}
		
		voteString += ".";
		
		return voteString;
	}

}
