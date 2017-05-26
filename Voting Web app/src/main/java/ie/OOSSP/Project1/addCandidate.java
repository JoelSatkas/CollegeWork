package ie.OOSSP.Project1;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class addCandidate
 */
public class addCandidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String msm;
	private String okmsm;
	private String errormsm;
	private String stockCandidates;

	/**
     * Default constructor. 
     */
    public addCandidate() {
        // TODO Auto-generated constructor stub
    	// adding the stock candidates for this project 
    	msm = "new";
    	okmsm = "The candidate was added sucsefully. Please delete old voting cookie for it to work properly";
    	errormsm = "Please fill in all feilds.";
    	stockCandidates = "Kathy Fishman – People Before Dogfish."
    			+ "Craig Heaney – Underwater Republican Party."
    			+ "Andrew Ryan – The I Still Live Party."
    			+ "George O’Carroll – United Utopians."
    			+ "Alice Orwell – Beware Big Brother Party.";
    }
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		if(msm.equals("new"))
		{
			request.setAttribute("todo", "");
			request.getRequestDispatcher("/addCandidate.jsp").forward(request, response);
		}
		else if(msm.equals("error"))
		{
			request.setAttribute("todo", errormsm);
			request.getRequestDispatcher("/addCandidate.jsp").forward(request, response);
		}
		else //msm.equals("ok")
		{
			request.setAttribute("todo", okmsm);
			request.getRequestDispatcher("/addCandidate.jsp").forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = (String) request.getParameter("firstName");
		String surName = (String) request.getParameter("surName");
		String partyName = (String) request.getParameter("partyName");
		
		System.out.println(name);
		System.out.println(surName);
		System.out.println(partyName);
		
		
		if((name.equals("")) || (surName.equals("")) || (partyName.equals("")))
		{
			msm = "error";
		}
		else
		{
			Cookie cookies[];
			Cookie candidatesCookie;
			cookies = request.getCookies(); // get client's cookies
			boolean found = false;
			
	        for ( int i = 0; i < cookies.length; i++ ) 
	        {
	        	if(cookies[i].getName().equals("candidatesCookie"))
	        	{
	        		String value = "";
	        		Cookie newCookie;
	        		
	        		candidatesCookie = cookies[i];
	        		found = true;
	        		
	        		value = candidatesCookie.getValue();
	        		value += name + " " + surName + " - " + partyName + ".";
	        		
	        		newCookie = new Cookie("candidatesCookie", value);
	        		newCookie.setMaxAge(60*60*24*7*4*12);
	        		
	        		response.addCookie(newCookie);
	        	}	        	 
	        }
	        
	        if(found == false)
	        {
	        	candidatesCookie = new Cookie("candidatesCookie", stockCandidates);
	        	candidatesCookie.setMaxAge(60*60*24*7*4*12);
	        	
	        	response.addCookie(candidatesCookie);
	        }
	        
			msm = "ok";
		}
			
		doGet(request, response);
	}

}
