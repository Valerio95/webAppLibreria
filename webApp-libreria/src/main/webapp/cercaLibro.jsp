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
<%List<Libro> carrello = (List<Libro>)request.getAttribute("carrello"); %>
<%List<Libro> libriAffittati = (List<Libro>)request.getAttribute("libriAffittati"); %>
<%int costoTotale = (Integer)request.getAttribute("costoTotale"); %>




<table class="">
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
<form action="compraAffitta" method="post">
<input type="text" hidden="true" name ="username" value=<%=request.getParameter("username")%>><br><br>
<input type="number" hidden="true" name ="idLibro" value=<%=l.getId()%>><br><br>

<div class="center">
<%if(l.isPrenotabilità()==true){%>
<input type="submit" class = "button newClass"  name ="azione"  value="Affitta"><p>(Solo uno alla volta) </p><br><br>
<% } %>
</div>
<div class="center">
<p>selezione la quantità da comprare : <select name="quantità">
<%for(int i=0;i<=l.getQuantità();i++){%>
<option value=<%=i%> > <%=i%>
<% } %>
</select></p>
<input type="submit" class = "button newClass"  name ="azione" value="AggiungiAlCarrello"><br><br>
</div>
</form>

</td> 

  </tr>
<% } %>
<tr>
  <th>Carrello</th>
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
Prezzo</td>
 <td> 
  Costo Totale
  </td> 
  </tr>	
  
<% for(Libro l : carrello){%>

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
<%=l.getPrezzo()%>
</td> 


 
<% } %>
<td> 
  <%=costoTotale%>
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
<% String messaggio = (String) request.getAttribute("messaggio"); 
	if (messaggio != null ){
		%>
		<h1>ERRORE</h1>
		<%=messaggio%>
					
	<% }%>
<form action="cercaLibro" method="post">
<div class="center">
<input type="text" hidden="true" name ="username" value=<%=request.getParameter("username")%>><br><br>
	Inserisci il Titolo del libro da cercare:<input type="text" name="titolo" /><br><br>
    Inserisci l'autore da cercare:<input type="text" name="autore" /><br><br>
<input type="submit" class = "button newClass"  name ="azione" value="Cerca"><br><br>
</div>
<div class="center">
<input type="submit" class = "button newClass"  name ="azione" value="Paga">
</div>
</form>

<div class="bottomleft">

<form action="welcomeUtente.jsp">
<input type="text" hidden="true" name ="username" value=<%=request.getParameter("username")%>><br><br> 
<input type="submit" class = "button newClass" value="Torna indietro">
 </form>
</div>

</body>
</html>