<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="calcBeanId" scope="session" class="ie.OOSSP.Project1.calculationBean" />
<jsp:useBean id="sortBeanId" scope="session" class="ie.OOSSP.Project1.candidateBean" />
<%@ page import="ie.OOSSP.Project1.candidate" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Voting Results</title>
</head>
<body>

<h2>Results</h2>

<p>Total Votes <%=(String)request.getAttribute("totalVotes")%></p>
<p>Valid Votes <%=(String)request.getAttribute("validVotes")%></p>
<p>Invalid Votes <%=(String)request.getAttribute("invalidVotes")%></p>

<%
	
	String candidatesString = (String) request.getAttribute("candidates");
	String[] votes = (String[])request.getAttribute("Votes");
	
	if(sortBeanId.sort(candidatesString))
	{
		
		calcBeanId.calculateAll(votes, sortBeanId.getSize());
		
		candidate[] candidateObjects = sortBeanId.getCandidateObjectArry();
		
		int candidate = 0;
			
		for (candidate can : sortBeanId.getCandidateObjectArry()) 
		{
			request.setAttribute("candidate", candidate);
			%>
			
			<li><%=can.getLine() %><br>
			Scored: <%=calcBeanId.getScore(candidate, sortBeanId.getSize())%>
			
			
			
			</li>
			
		<%
		candidate++;
		
		}%>
		
		<br><br>
		
		
	<%}
	else
	{%>
		<p>candidates string was not found. logic error</p> 
	<%}%>

</body>
</html>