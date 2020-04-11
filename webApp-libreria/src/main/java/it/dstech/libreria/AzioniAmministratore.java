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
import it.dstech.modelli.Utente;
import it.dstech.repos.GestioneLibreria;

@WebServlet( urlPatterns = {"/azioniAmministratore"})
public class AzioniAmministratore extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String azione = req.getParameter("azione");
		if ("AggiungiLibro".equalsIgnoreCase(azione)) {
			try {
				GestioneLibreria db = new GestioneLibreria();
				List<Libro> lista = db.stampaLibri();
				req.setAttribute("lista", lista);
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			
			req.getRequestDispatcher("aggiungiLibro.jsp").forward(req, resp);
		} else if ("ModificaLibro".equalsIgnoreCase(azione)) {
			try {
			GestioneLibreria db = new GestioneLibreria();
			List<Libro> lista = db.stampaLibri();
			req.setAttribute("lista", lista);
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
		req.getRequestDispatcher("modificaLibro.jsp").forward(req, resp);
		}else if ("RimuoviLibro".equalsIgnoreCase(azione)) {
		try {
		GestioneLibreria db = new GestioneLibreria();
		List<Libro> lista = db.stampaLibri();
		req.setAttribute("lista", lista);
	} catch (ClassNotFoundException | SQLException e) {
		
		e.printStackTrace();
	}
	
	req.getRequestDispatcher("rimuoviLibro.jsp").forward(req, resp);
	}else if ("SollecitaRestituzione".equalsIgnoreCase(azione)) {
	try {
	GestioneLibreria db = new GestioneLibreria();
	db.controllaRitardoRestituzione();
	List<Libro> lista = db.AllLibriNonRestituiti();
	req.setAttribute("lista", lista);
} catch (ClassNotFoundException | SQLException e) {
	
	e.printStackTrace();
}

req.getRequestDispatcher("sollecitaRestituzione.jsp").forward(req, resp);
}else if ("StampaVendite".equalsIgnoreCase(azione)) {
	try {
	GestioneLibreria db = new GestioneLibreria();
	List<Libro> lista = db.creaListaVendite();
	req.setAttribute("lista", lista);
} catch (ClassNotFoundException | SQLException e) {
	
	e.printStackTrace();
}

req.getRequestDispatcher("stampaVendite.jsp").forward(req, resp);
}else if ("RimuoviUtente".equalsIgnoreCase(azione)) {
	try {
	GestioneLibreria db = new GestioneLibreria();
	List<Utente> lista = db.listaUtenti();
	req.setAttribute("lista", lista);
} catch (ClassNotFoundException | SQLException e) {
	
	e.printStackTrace();
}

req.getRequestDispatcher("rimuoviUtente.jsp").forward(req, resp);
}
		
		
		
}

	


}


