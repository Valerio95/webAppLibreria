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

 @WebServlet( urlPatterns = {"/rimuoviUtente"})
 public class RimuoviUtente extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("utenteScelto"));
		
		try {
			GestioneLibreria db = new GestioneLibreria();
			 db.rimuoviUtente(id);
			 List<Libro> lista = db.stampaLibri();
				req.setAttribute("lista", lista);
				req.getRequestDispatcher("rimuoviUtente.jsp").forward(req, resp);
			}
		catch (SQLException | ClassNotFoundException e) {
			        e.printStackTrace();
		}

}}
