<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Adding Candidates</title>
</head>

<body>
	<h2>Adding a new Candidate</h2>
	<h3><i>Using the character '.' will cause problems in the cookies</i></h3>
	
<%=(String)request.getAttribute("todo")%>

	<FORM method = "post" ACTION="addCandidate">
		<br>Name<br>
		<INPUT SIZE="text" NAME="firstName"><br><br>
		Surname<br>
		<INPUT SIZE="text" NAME="surName"><br><br>
		Party<br>
		<INPUT SIZE="text" NAME="partyName"><br><br><br>
		<INPUT TYPE="SUBMIT" value="Submit" name="Submit">
		
	</FORM>
	<br>
	<FORM method = "get" ACTION="resetCandidates">
	<INPUT TYPE="SUBMIT" value="Reset" name="Submit">
	</FORM>
	
</body>

</html>