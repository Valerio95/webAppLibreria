<%@page import="java.util.List"%>
<%@page import="it.dstech.modelli.Utente"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link href="css/botton.css" rel="stylesheet" type="text/css">
<link href="css/table.css" rel="stylesheet" type="text/css">
<head>
<meta charset="ISO-8859-1">
<title>Rimuovi Utente</title>
</head>
<body>

<%List<Utente> listaUtenti = (List<Utente>)request.getAttribute("lista"); %>


<table class="table">
<tr>
  <th>Lista Utenti</th>
</tr>
<tr>
    <td>
Id
</td>  
<td>
Username
</td> 

  </tr>	
<%for(Utente p : listaUtenti){%>
<tr>
    <td>
<%=p.getId()%>
</td>  
<td>
 <%=p.getUsername()%>
</td>  

  </tr>
<% } %>
</table>

<div class="center">
<form action="rimuoviUtente" method="post">
<p>IdProdotto: <select  name="prodottoScelto" >
<%for(Utente p : listaUtenti){%>
<option  value=<%=p.getId()%> > id:<%=p.getId()%> 
<% } %>
</select></p>
	<input type="submit" class = "button newClass" value="RIMUOVI"> <br><br>
</form>
</div>

<div class="bottomleft">
<form action="welcomeAmministratore.jsp">
<input type="submit" class = "button newClass" value="Torna indietro"> </form>
</div>
</body>

</body>
</html>