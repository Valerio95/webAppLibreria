<%@page import="it.dstech.modelli.Scontrino"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<link href="css/botton.css" rel="stylesheet" type="text/css">
<link href="css/table.css" rel="stylesheet" type="text/css">

<head>
<meta charset="ISO-8859-1">
<title>Lista Vendite</title>
</head>
<body>
<%List<Scontrino> listaScontrini = (List<Scontrino>)request.getAttribute("lista"); %>


<table class="table">
<tr>
  <th>Lista Scontrini</th>
</tr>
<tr>
    <td>
Id
</td> 
<td>
Prezzo totale
</td> 
<td>
 Data di emissione
</td>    
  </tr>	
<% for(Scontrino p : listaScontrini){%>

<tr>

    <td>
<%=p.getId()%> 
</td> 
<td>
<%=p.getPrezzoTotale()%>
</td>
<td>
<%=p.getDataDiEmissione()%>
</td> 
<td>
<div class="center">

<form action="dettagli" method="post">
<input type="number" hidden="true" name ="idScontrino" value=<%=p.getId()%>><br><br>
<input type="submit" class = "button newClass" value="Dettagli">
</form>
</div>
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