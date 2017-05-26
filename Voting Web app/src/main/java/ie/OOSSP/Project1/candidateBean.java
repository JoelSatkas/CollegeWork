package ie.OOSSP.Project1;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class candidateBean 
{
	private final char COOKIE_VALUE_SEPERATOR = '.' ; // char that separates candidates in the cookie
	
	private int size = 0;
	private String[] candidateStringArray;
	private ArrayList<String> strings = new ArrayList<String>();
	private ArrayList<candidate> candidateObjects = new ArrayList<candidate>();
	private candidate[] candidates;
	
	public candidateBean()
	{
		System.out.println("bean created");
	}
	
	
	public int getSize()
	{
		return size;
	}
	
	public String[] getCandidateStringarray()
	{
		return candidateStringArray ;
	}
	
	public candidate[] getCandidateObjectArry()
	{
		return candidates;
	}
	
	public boolean sort(String msm)
	{
		System.out.println("Sort called");
		if(msm != null)
		{
			String oneCandidate = "";
			for(int x = 0 ; x < msm.length() ; x++)
			{
				oneCandidate += msm.charAt(x);
				if ((x+1) < msm.length())
				{
					if((msm.charAt(x+1) == COOKIE_VALUE_SEPERATOR)) 
					{
						strings.add(oneCandidate);
						candidateObjects.add(new candidate(oneCandidate));
						oneCandidate = "";
						
						x++;
					}
					
				}
			}
			candidateStringArray = new String[strings.size()];
			candidateStringArray = strings.toArray(candidateStringArray);
			size = strings.size();
			
			candidates = new candidate[size];
			candidates = candidateObjects.toArray(candidates);
			
			// this avoids duplicating the candidates in the jsp and users browser 
			strings.clear();
			candidateObjects.clear();
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	// prototype method. not used yet
	public boolean openCookie(String key, HttpServletRequest request)
	{
		Cookie cookies[];
		Cookie candidatesCookie = null;
		cookies = request.getCookies(); // get client's cookies
		boolean found = false;
		
		if(cookies == null)
		{
			return false;
		}
		else
		{
			for ( int i = 0; i < cookies.length; i++ ) 
	        {
	        	if(cookies[i].getName().equals(key))
	        	{
	        		candidatesCookie = cookies[i];
	        		found = true;
	        	}	        	 
	        }
			
			if(found == false)
			{
				return false;
			}
			else
			{
				String value = candidatesCookie.getValue();
				if(sort(value))
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
	}
	
}
