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
<form action="azioniAmministratore" method="post">

	<input type="submit"  class = "button " name ="azione" value="AggiungiLibro" /> <br><br>
	<input type="submit"  class = "button " name ="azione" value="ModificaLibro" /> <br><br>
	<input type="submit" class = "button" name ="azione" value="RimuoviLibro" /> <br><br>
	<input type="submit" class = "button" name ="azione" value="SollecitaRestituzione" /> <br><br>
	<input type="submit" class = "button" name ="azione" value="StampaVendite" /> <br><br>
	<input type="submit" class = "button" name ="azione" value="RimuoviUtente" /> <br><br>
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