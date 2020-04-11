<%@page import="java.util.List"%>
<%@page import="it.dstech.modelli.Libro"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link href="css/botton.css" rel="stylesheet" type="text/css">
<link href="css/table.css" rel="stylesheet" type="text/css">

<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>


<body>
<%List<Libro> libriNonRestituiti = (List<Libro>)request.getAttribute("lista"); %>


<% String messaggio = (String) request.getAttribute("messaggio"); 
	if (messaggio != null ){
		%>
		
		<%=messaggio%>
				
	<% }%>

<table class="table">
<tr>
  <th>Libri Non Restituiti</th>
</tr>
<tr>
 <td>
Id
</td> 
<td>
Titolo
</td> 
<td>
Autore
</td> 
<td>
Genere
</td>    
<td>
Data di prenotazione
</td>
<td>
Proprietario
</td>
  </tr>	
  
<% for(Libro l : libriNonRestituiti){%>

<tr>

    <td>
<%=l.getId()%> 
</td> 
<td>
<%=l.getTitolo()%>
</td>
<td>
<%=l.getAutore()%>
</td> 
<td>
<%=l.getGenere()%>
</td> 
<td>
<%=l.getDataDiPrenotazione()%>
</td> 
<td>
<%=l.getProprietario()%>
</td> 
<td>
<div class="center">
<form action="sollecita" method="post">
<%if(l.isRitardo()==true){%>
<input type="submit" class = "button newClass"  name ="azione"  value="SollecitaRestituzione"><br><br>
<% } %>
<input type="text" hidden="true" name ="username" value=<%=l.getProprietario()%>><br><br>
<input type="number" hidden="true" name ="titolo" value=<%=l.getTitolo()%>><br><br>
<input type="number" hidden="true" name ="autore" value=<%=l.getAutore()%>><br><br>
</form>
</div>
</td> 

  </tr>
<% } %>
</table>
<div class="bottomleft">
<form action="welcomeAmministratore.jsp">
<input type="submit" class = "button newClass" value="Torna indietro"> </form>
</div>
</body>
</html>