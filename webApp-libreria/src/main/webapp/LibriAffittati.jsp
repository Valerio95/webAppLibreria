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
<%List<Libro> libriRestituiti = (List<Libro>)request.getAttribute("libriRestituiti"); %>
<%List<Libro> libriNonRestituiti = (List<Libro>)request.getAttribute("libriNonRestituiti"); %>
<table class="table">
<tr>
  <th>Libri Non Restituiti</th>
</tr>
<tr>
    <td>
Id
</td> 
<td>
Titolo</td> 
<td>
Autore</td> 
<td>
Genere</td>    
<td>
Data di prenotazione</td>

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
<div class="center">
<form action="restituzione" method="post">
<input type="text" hidden="true" name ="username" value=<%=request.getParameter("username")%>><br><br>
<input type="number" hidden="true" name ="idLibro" value=<%=l.getId()%>><br><br>
<input type="submit" class = "button newClass"  name ="azione"  value="Restituisci"><br><br>
</form>
</div>
</td> 

  </tr>
<% } %>

<tr>
  <th>Libri Restituiti</th>
</tr>

<tr>
    <td>
Id
</td> 
<td>
Titolo</td> 
<td>
Autore</td> 
<td>
Genere</td>    
<td>
Data di prenotazione</td>

  </tr>	
<% for(Libro l : libriRestituiti){%>

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
  </tr>
<% } %>

</table>

<div class="bottomleft">
<form action="welcomeUtente.jsp">
<input type="text" hidden="true" name ="username" value=<%=request.getParameter("username")%>><br><br> 
<input type="submit" class = "button newClass" value="Torna indietro"> </form>
</div>

</body>
</html>