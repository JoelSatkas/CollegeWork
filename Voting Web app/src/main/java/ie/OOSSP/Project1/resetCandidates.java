package ie.OOSSP.Project1;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class resetCandidates
 */
public class resetCandidates extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String stockCandidates;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public resetCandidates() {
        super();
        // TODO Auto-generated constructor stub
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
		
		Cookie newCookie;
		
		newCookie = new Cookie("candidatesCookie", stockCandidates);
		newCookie.setMaxAge(60*60*24*7*4*12);
		
		response.addCookie(newCookie);
		request.getRequestDispatcher("/resetCandidates.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
