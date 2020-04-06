package it.dstech.libreria;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.dstech.modelli.Utente;
import it.dstech.repos.GestioneLibreria;
import it.dstech.utility.EmailUtility;

public class Registrazione extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("message", "Pagina non accessibile");
		req.getRequestDispatcher("/home_page.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		Utente u = new Utente();
		u.setPassword(username);
		u.setPassword(password);
		GestioneLibreria gestioneLibreria = null;
		try {
			gestioneLibreria = new GestioneLibreria();
			boolean aggiungiUtente =gestioneLibreria.aggiungiUtente(u);
			if(aggiungiUtente==false) {
				req.setAttribute("message", "Mail già presente nel DB");
				req.getRequestDispatcher("/accedi.jsp").forward(req, resp);
			}
			else {
			
			EmailUtility.sendEmail(u.getUsername(), "Conferma Mail", generaLinkValidazioneUtente(u));
			gestioneLibreria.close();
			req.setAttribute("message", "Controlla la mail per attivare l'account");
			req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
			}
		} catch (ClassNotFoundException | SQLException | MessagingException e) {
			try {
				gestioneLibreria.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

		}

	}
	private String generaLinkValidazioneUtente(Utente utente) {
		String validationPath = "http://localhost:8080/webApp-libreria/validazione?utente=";
		return "Per attivare la mail clicca su questo link: " + validationPath + utente.getUsername();
	}
}
