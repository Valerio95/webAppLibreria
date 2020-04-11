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



@WebServlet( urlPatterns = {"/restituzione"})
public class Restituzione extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		int idLibro = Integer.parseInt(req.getParameter("idLibro"));
        try {
        	GestioneLibreria gestione = new GestioneLibreria();
        	int idUtente=gestione.trovaIdUtente(username);
        	gestione.restituisciLibro(idLibro, idUtente);
        	List<Libro> libriNonRestituiti = gestione.libriNonRestituiti(idUtente);
			List<Libro> libriRestituiti = gestione.libriRestituiti(idUtente);
			req.setAttribute("libriNonRestituiti", libriNonRestituiti);
			req.setAttribute("libriRestituiti", libriRestituiti);
			gestione.close();
        }catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
		req.getRequestDispatcher("LibriAffittati.jsp").forward(req, resp);
	}
}
