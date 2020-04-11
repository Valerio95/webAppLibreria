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
<%List<Libro> listaLibri = (List<Libro>)request.getAttribute("lista"); %>




<table class="table">
<tr>
  <th>Lista Libri</th>
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
Quantità</td>
<td>
Quantità Prenotabile</td>
<td>
Prezzo</td>
  </tr>	
  
<% for(Libro l : listaLibri){%>

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
<%=l.getQuantità()%>
</td> 
<td>
<%=l.getQuantitàPrenotabile()%>
</td> 
<td>
<%=l.getPrezzo()%>
</td> 
<td>
<% } %>
</table>
 <div class="center">
<form action="aggiungiLibro" method="post">
   Titolo:<input type="text" name="titolo" /><br><br>
   Autore:<input type="text" name="autore" /><br><br>
   Genere:<input type="text" name="genere" /><br><br>
   Quantità:<input type="number" name="quantità" /><br><br>
   Prezzo:<input type="number" name="prezzo" /><br><br>
   
<input type="submit" class = "button newClass"  name ="azione" value="Aggiungi">
</form>
</div>

<div class="bottomleft">

<form action="welcomeAmministratore.jsp">
<input type="submit" class = "button newClass" value="Torna indietro"> </form>
</div>
</body>
</html>