<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link href="css/botton.css" rel="stylesheet" type="text/css">
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="center">
<% String messaggio = (String) request.getAttribute("messaggio"); 
	if (messaggio != null ){
		%>
		<h1>ERRORE</h1>
		<%=messaggio%>
					
	<% }
	else{
%>

<h1>Inserisci la tua e-mail e la tua password</h1>
<form action="accedi" method="post">
<input type="text"  name="username" placeholder="Inserisci e-mail" />
<input type="password"  name=password placeholder="Inserisci password" />
<input type="submit" class = "button"  name ="azione" value="Accedi">

</form>
<% } %>
</div>
<div class="bottomleft">

<form action="welcomeLibreria.jsp">
<input type="submit" class = "button newClass" value="Torna indietro"> </form>
</div>
</body>
</html>