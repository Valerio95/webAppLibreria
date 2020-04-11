package it.dstech.libreria;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.dstech.repos.GestioneLibreria;
import it.dstech.modelli.Libro;



@WebServlet( urlPatterns = {"/dettagli"})
public class DettagliScontrino extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String azione = req.getParameter("azione");
		int idScontrino = Integer.parseInt(req.getParameter("idScontrino"));
		String username = req.getParameter("username");
		if ("dettagli".equalsIgnoreCase(azione)) {

		try {
			GestioneLibreria dbManagment = new GestioneLibreria();
			
			List<Libro> lista = dbManagment.dettagliScontrino(idScontrino);
			req.setAttribute("lista", lista);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("dettagli.jsp").forward(req, resp);

	}else if("Torna indietro".equalsIgnoreCase(azione)) {
		try {
			GestioneLibreria dbManagment = new GestioneLibreria();
			
			List<Libro> lista = dbManagment.dettagliScontrino(idScontrino);
			req.setAttribute("lista", lista);
			req.setAttribute("username", username);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("stampaScontriniCliente.jsp").forward(req, resp);
	}

	}
}
