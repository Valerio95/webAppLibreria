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

@WebServlet( urlPatterns = {"/cercaLibro"})
public class CercaLibro extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String azione = req.getParameter("azione");
		String titolo="";
		if ("Cerca".equalsIgnoreCase(azione)) {
		if(req.getParameter("titolo") != null && !req.getParameter("titolo").equals("")) {
		 titolo = req.getParameter("titolo");
		 }
		String autore="";
		if(req.getParameter("autore") != null && !req.getParameter("autore").equals("")) {
		 autore = req.getParameter("autore"); 
		 }
		if (titolo.equals("")|| titolo.equals(null)) {
			try {
				GestioneLibreria db = new GestioneLibreria();
				List<Libro> lista = db.cercaLibroPerAutore(autore);
				req.setAttribute("lista", lista);
				req.setAttribute("username", username);
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			
			req.getRequestDispatcher("cercaLibro.jsp").forward(req, resp);
		} else if (autore.equals("")|| autore.equals(null)) {
			try {
				GestioneLibreria db = new GestioneLibreria();
				List<Libro> lista = db.cercaLibroPerTitolo(titolo);
				req.setAttribute("lista", lista);
				req.setAttribute("username", username);
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			req.getRequestDispatcher("cercaLibro.jsp").forward(req, resp);
		}	
		}else if ("Paga".equalsIgnoreCase(azione)){
			
			try {
				GestioneLibreria db = new GestioneLibreria();
				List<Libro> carrello = db.stampaCarrello();
				List<Libro> libriAffittati =db.stampaLibriAffittati();
				Scontrino scontrino =new Scontrino();
				scontrino.setLibriAquistati(carrello);
				scontrino.setPrezzoTotale(scontrino.calcolaPrezzoTotale());
				if(scontrino.getPrezzoTotale()==0) {
					req.setAttribute("scontrino", scontrino);
					req.setAttribute("libriAffittati", libriAffittati);
					req.getRequestDispatcher("paga.jsp").forward(req, resp);
				}else {
				req.setAttribute("scontrino", scontrino);
				req.setAttribute("libriAffittati", libriAffittati);
				int idCliente=db.trovaIdUtente(username);
				db.creaScontrino(idCliente);
				db.assegnaIdScontrino(idCliente);
				db.close();
			req.getRequestDispatcher("paga.jsp").forward(req, resp);
				}
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
				
			}
			
		}
}


