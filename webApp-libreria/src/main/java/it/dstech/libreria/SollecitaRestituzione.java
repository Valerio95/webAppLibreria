package it.dstech.libreria;

import java.io.IOException;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.dstech.repos.GestioneLibreria;
import it.dstech.utility.EmailUtility;

@WebServlet(urlPatterns = { "/sollecita" })
@MultipartConfig
public class SollecitaRestituzione extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("messaggio", "Pagina non accessibile");
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String titolo = req.getParameter("titolo");
		String autore = req.getParameter("autore");
		try {
			GestioneLibreria	gestioneDB = new GestioneLibreria();
			
			EmailUtility.sendEmail(username, "Ritardo restituzione", "Gentile cliente"+""+username+"le ricordo di restituire il libro"+""+titolo+"di"+""+autore);
			gestioneDB.close();
			req.setAttribute("messaggio", "messaggio inviato");
			req.getRequestDispatcher("/sollecitaRestituzione.jsp").forward(req, resp);

			} catch (ClassNotFoundException | SQLException | MessagingException e) {
				
				
				e.printStackTrace();

			}

	}

}
