<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<jsp:useBean id="sortBeanId" scope="session" class="ie.OOSSP.Project1.candidateBean" />
<%@ page import="ie.OOSSP.Project1.candidate" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vote for a Candidate</title>
</head>
<body>

	<h2>Vote for a Candidate</h2><br>
	
	<!-- setting the number of candidates for the vote servlet dopost -->
	
	<%
	
	String candidatesString = (String) request.getAttribute("candidates");
	
	if(sortBeanId.sort(candidatesString))
	{
		candidate[] candidateObjects = sortBeanId.getCandidateObjectArry();
		int candidate = 0;%>
		
		<FORM method = "post" ACTION="vote">
		
	<% for (candidate can : sortBeanId.getCandidateObjectArry()) {
		request.setAttribute("candidate", candidate);
		candidate++;%>
		
		<li><%=can.getLine() %><br>
		
		<!-- The paragraph here prints numbers for each candidate -->
		<p> 
		<%for(int x = 0 ; x < sortBeanId.getSize() ;x++)
		{
			request.setAttribute("count", x+1);
		%>
			<%=(Integer)request.getAttribute("count")%> &nbsp &nbsp
			
		<%}%></p>
		
		<%for(int x = 0 ; x < sortBeanId.getSize() ;x++)
		{
			request.setAttribute("count", x); 
			
		%>
			<INPUT TYPE="radio" NAME="vote<%=(Integer)request.getAttribute("count")%>" VALUE="<%=(Integer)request.getAttribute("candidate")%>">
		<%}%>
		
		<br><br><br></li>
		
	<%}%>
	
		<input type="hidden" name="candidateNum" value="<%=sortBeanId.getSize()%>">
		<INPUT TYPE="SUBMIT" value="Submit" name="Submit">
	</FORM>
	<br><br>
	
	<%}
	
	
	else
	{%>
		<p>candidates string was not found. logic error</p> 
	<%}%>

</body>
</html>