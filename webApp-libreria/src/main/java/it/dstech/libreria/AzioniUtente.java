package it.dstech.libreria;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.dstech.modelli.Libro;
import it.dstech.repos.GestioneLibreria;
import it.dstech.modelli.Scontrino;

@WebServlet( urlPatterns = {"/azioniUtente"})
public class AzioniUtente extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String azione = req.getParameter("azione");
		String username = req.getParameter("username");
		System.out.println(username+"1");
		if ("CercaLibri".equalsIgnoreCase(azione)) {
			try {
				GestioneLibreria db = new GestioneLibreria();
				List<Libro> lista = db.stampaLibri();
				req.setAttribute("lista", lista);
				int costoTotale=0;
				req.setAttribute("costoTotale",costoTotale );
				List<Libro> carrello = db.stampaCarrello();
				req.setAttribute("carrello", carrello);
				List<Libro> libriAffittati = db.stampaLibriAffittati();
				req.setAttribute("libriAffittati", libriAffittati);
				req.setAttribute("username", username);
				db.close();
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			
			req.getRequestDispatcher("cercaLibro.jsp").forward(req, resp);
		}else if ("ControllaLibriAffittati".equalsIgnoreCase(azione)) {
			try {
				GestioneLibreria db = new GestioneLibreria();
				int idUtente=db.trovaIdUtente(username);
				List<Libro> libriNonRestituiti = db.libriNonRestituiti(idUtente);
				List<Libro> libriRestituiti = db.libriRestituiti(idUtente);
				req.setAttribute("libriNonRestituiti", libriNonRestituiti);
				req.setAttribute("libriRestituiti", libriRestituiti);
				req.setAttribute("username", username);
				db.close();
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			
			req.getRequestDispatcher("LibriAffittati.jsp").forward(req, resp);
}else if ("stampaScontrini".equalsIgnoreCase(azione)) {
	try {
		GestioneLibreria db = new GestioneLibreria();
		int idUtente=db.trovaIdUtente(username);
		List<Scontrino> lista = db.scontriniCliente(idUtente);
		req.setAttribute("lista", lista);
	} catch (ClassNotFoundException | SQLException e) {
		
		e.printStackTrace();
	}
	req.getRequestDispatcher("stampaScontriniCliente.jsp").forward(req, resp);
}else if ("stampaScontrini".equalsIgnoreCase(azione)) {
	
}
	

	

}
	}
