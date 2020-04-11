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
<title>Modifica Libro</title>
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
Titolo
</td> 
<td>
Autore 
</td>   
<td>
Genere 
</td>
<td>
Quantità 
</td> 
<td>
Prezzo 
</td>     
  </tr>	
<%for(Libro p : listaLibri){%>
<tr>
    <td>
<%=p.getId()%>
</td>  
<td>
 <%=p.getTitolo()%>
</td>  
<td>
<%=p.getAutore()%> 
</td>  
<td>
<%=p.getGenere()%> 
</td> 
<td>
<%=p.getQuantità()%> 
</td>   
<td>
<%=p.getPrezzo()%> 
</td>  
  </tr>
<% } %>
</table>
<div class="center">

<form action="modificaLibro" method="post">
<p>IdProdotto: <select  name="idLibro" >
<%for(Libro p : listaLibri){%>
<option  value=<%=p.getId()%> > id:<%=p.getId()%> 
<% } %>
</select></p>
    Titolo:<input type="text" name="titolo" /><br><br>
	Autore:<input type="text" name="autore" /><br><br>
	Genere:<input type="text" name="genere" /><br><br>
	Quantità:<input type="number" min = "1" name="qta" /><br><br>
	Prezzo:<input type="number" min = "1" name="prezzo" /><br><br>
	
	<input type="submit" class = "button newClass" value="Modifica"> <br><br>
</form>
</div>

<div class="bottomleft">
<form action="welcomeAmministratore.jsp">
<input type="submit" class = "button newClass" value="Torna indietro"> </form>
</div>


</body>
</html>