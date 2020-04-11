<%@page import="it.dstech.modelli.Utente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link href="css/botton.css" rel="stylesheet" type="text/css">
<head>
<meta charset="ISO-8859-1">
<title>Scelta azioni dell'utente</title>
</head>
<body>
<% Utente utente = (Utente)request.getAttribute("utente"); %>
<div class="center">
<img alt="immagine" src="data:image/jpg;base64,<%= utente.getImage() %>"> <br>
<h1>Benvenuto cliente, <%=request.getParameter("username")%> </h1>
<h1>Cosa vuoi fare?</h1>


<form action="azioniUtente" method="post">

<input type="text" hidden="true" name ="username" value=<%=request.getParameter("username")%>><br><br> 
	<input type="submit"  class = "button " name ="azione" value="CercaLibri" /> <br><br>
	<input type="submit"  class = "button " name ="azione" value="ControllaLibriAffittati" /> <br><br>
	<input type="submit" class = "button" name ="azione" value="StampaScontrini" /> <br><br>
</form>
</div>

<div class="bottomleft">
<form action="welcomeLibreria.jsp">
<input type="submit" class = "button newClass" value="Torna indietro">
 </form>
 </div>
 
</body>
</html>