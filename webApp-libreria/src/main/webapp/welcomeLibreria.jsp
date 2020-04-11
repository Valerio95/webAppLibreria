<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="css/botton.css">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="center">
<% String messaggio = (String) request.getAttribute("messaggio"); 
	if (messaggio != null ){
		%>
		
		<%=messaggio%>
	
				
	<% }%>



<h1>Benvenuto nella nostra libreria</h1>

<form action="scegliAzione" method="post">

<input type="submit" class="button" name="azione" value="Accedi" /> <br><br>


<input type="submit" class="button" name="azione" value="Registrati" /><br><br>

</form>
</div>

</body>
</html>