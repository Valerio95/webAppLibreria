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



@WebServlet( urlPatterns = {"/compraAffitta"})
public class Affitta_CompraLibro extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String azione = req.getParameter("azione");
		String username = req.getParameter("username");
		System.out.println(username);
		int idLibro = Integer.parseInt(req.getParameter("idLibro"));
		int quantità = Integer.parseInt(req.getParameter("quantità"));
		Libro l = new Libro();
		l.setId(idLibro);
		l.setQuantità(quantità);
		if ("AggiungiAlCarrello".equalsIgnoreCase(azione)) {
			
			try {
				GestioneLibreria db = new GestioneLibreria();
				 List<Libro> carrello=db.stampaCarrello();
				 List<Libro> libriAffittati =db.stampaLibriAffittati();
				 List<Libro> lista=db.stampaLibri();
				 int costoTotale=0;
		            for(Libro prodotto:carrello) {
		            	costoTotale+=prodotto.getPrezzo();
		            }
				if(quantità==0) {
					req.setAttribute("costoTotale", costoTotale);
					req.setAttribute("carrello", carrello);
					req.setAttribute("libriAffittati", libriAffittati);
					req.setAttribute("lista", lista);
					req.setAttribute("username", username);
					req.getRequestDispatcher("cercaLibro.jsp").forward(req, resp);

				}else {
			int idUtente =	db.trovaIdUtente(username);
				db.vendiLibro(idUtente, l);
				
				 
						
				req.setAttribute("costoTotale", costoTotale);
				req.setAttribute("carrello", carrello);
				req.setAttribute("libriAffittati", libriAffittati);
				req.setAttribute("lista", lista);
				req.setAttribute("username", username);
				req.getRequestDispatcher("cercaLibro.jsp").forward(req, resp);

				 }
					
			} catch (ClassNotFoundException | SQLException e) {
				
				e.printStackTrace();
			}
			
		}else if("Affitta".equalsIgnoreCase(azione)) {
			try {
			GestioneLibreria db = new GestioneLibreria();
			db.affittaLibro(db.trovaIdUtente(username),l);
			 List<Libro> carrello=db.stampaCarrello();
			 List<Libro> libriAffittati =db.stampaLibriAffittati();
			 List<Libro> lista=db.stampaLibri();
			 int costoTotale=0;
	            for(Libro prodotto:carrello) {
	            	costoTotale+=prodotto.getPrezzo();
	            }
				req.setAttribute("costoTotale", costoTotale);
				req.setAttribute("carrello", carrello);
				req.setAttribute("libriAffittati", libriAffittati);
				req.setAttribute("lista", lista);
				req.setAttribute("username", username);
				 }catch (ClassNotFoundException | SQLException e) {
						
						e.printStackTrace();
					}
					
					req.getRequestDispatcher("cercaLibro.jsp").forward(req, resp);
			
			}
	}

}
