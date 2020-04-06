package it.dstech.libreria;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.dstech.repos.GestioneLibreria;

public class ControlloValidazione extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String mailUtente = req.getParameter("utente");
		GestioneLibreria gestione;
		try {
			gestione = new GestioneLibreria();
			gestione.validaUtente(mailUtente);
			gestione.close();
			req.setAttribute("message", "L'utente " + mailUtente + " è stato validato");
			req.getRequestDispatcher("/home_page.jsp").forward(req, resp);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	}
}
