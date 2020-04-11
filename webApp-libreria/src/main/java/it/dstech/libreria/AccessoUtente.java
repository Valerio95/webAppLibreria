package it.dstech.libreria;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.dstech.modelli.Utente;
import it.dstech.repos.GestioneLibreria;


@WebServlet( urlPatterns = {"/accedi"})
public class AccessoUtente extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		System.out.println(username);
		if(username.equalsIgnoreCase("admin")&& password.equalsIgnoreCase("123")) {
			
				req.getRequestDispatcher("/welcomeAmministratore.jsp").forward(req, resp);
			
		}else {
		try {
			GestioneLibreria database = new GestioneLibreria();
			Utente utente = database.prendiUtente(username, password);
			boolean controllo =database.controlloEsistenzaUtente(utente);
			if (controllo==false) {
				
				req.setAttribute("messaggio", "Utente non ancora registrato");
				database.close();
				req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
			} else {
				boolean controlloAttivo=database.controlloValidaUtente(utente);
				if (controlloAttivo==false) {
					
					req.setAttribute("messaggio", scriviRispostaUtenteNonAttivo(utente));
					database.close();
					req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
				} else {
					
					req.setAttribute("Utente", utente);
					req.setAttribute("username", username);
					database.close();
					req.getRequestDispatcher("/welcomeUtente.jsp").forward(req, resp);
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}}
	}
	
	
	
	private String scriviRispostaUtenteNonAttivo(Utente utente) {
		String mailUtente = utente.getUsername();
		int indexOf = mailUtente.indexOf('@');
		String parteFinaleMail = mailUtente.substring(indexOf);
		String primiDueCaratteri = mailUtente.substring(0, 3);
		String mailFinale = primiDueCaratteri + contaX(indexOf - 2) + parteFinaleMail;
		return "L'utente " + mailFinale + " non ha ancora validato l'email";
	}

	
	
	private String contaX(int numeri) {
		String x = "";
		for (int i = 0; i < numeri; i++) {
			x += "x";
		}
		return x;
	}
}
