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
<div class="center"><h1>Inserisci la tua e-mail e una password</h1>
<form action="registrati" method="post">
<input type="text"  name="username" placeholder="Inserisci l'e-mail"  />
<input type="password"  name=password placeholder="Inserisci la password"  />
<input type="file" name="image" placeholder="Inserisci l'immagine del profilo">
<input type="submit" class="botton" name="azione" value="Registrati" />
</form>
</div>
<div class="bottomleft">

<form action="welcomeLibreria.jsp">
<input type="submit" class = "button newClass" value="Torna indietro"> </form>
</div>
</body>
</html>