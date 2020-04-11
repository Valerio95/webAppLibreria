<%@page import="it.dstech.modelli.Scontrino"%>
<%@page import="it.dstech.modelli.Libro"%>
<%@page import="java.util.List"%>
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
<%Scontrino scontrino = (Scontrino)request.getAttribute("scontrino"); %>
<%List<Libro> libriAffittati = (List<Libro>)request.getAttribute("libriAffittati"); %>


<table class="table">
<tr>
  <th>Scontrino</th>
</tr>
<tr>
  <th>Libri Acquistati</th>
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
 Quantità
</td>    
<td>
 Prezzo
</td>   
  </tr>	
<% for(Libro p : scontrino.getLibriAquistati()){%>
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
 <%=p.getQuantità()%> 
</td>     
<td>
 <%=p.getPrezzo()%> 
</td>   
  </tr>
<% } %>
<tr>
    <td>
Costo Totale <%=scontrino.getPrezzoTotale()%> 

</td>   
  </tr>
<tr>
  <th>Libri Affittati</th>
</tr>
<tr>
    <td>
Id
</td> 
<td>
Titolo</td> 
<td>
Autore</td> 
  </tr>	
  
<% for(Libro l : libriAffittati){%>

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
 </tr>
<% } %>
</table>
<div class="center">

<form action="welcomeUtente.jsp">
<input type="text" hidden="true" name ="username" value=<%=request.getParameter("username")%>><br><br> 
<input type="submit" class = "button newClass" value="Torna indietro"> </form>
</div>
</body>
</html>