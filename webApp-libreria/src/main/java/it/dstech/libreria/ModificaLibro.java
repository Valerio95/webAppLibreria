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


@WebServlet( urlPatterns = {"/modificaLibro"})
public class ModificaLibro extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("idLibro"));
		String titolo = req.getParameter("titolo");
		int qta = Integer.parseInt(req.getParameter("qta"));
		String autore =req.getParameter("autore");
		String genere =req.getParameter("genere");
		int prezzo = Integer.parseInt(req.getParameter("prezzo"));
		Libro l = new Libro();
		l.setTitolo(titolo);
		l.setQuantità(qta);
		l.setGenere(genere);
		l.setPrezzo(prezzo);
		l.setAutore(autore);

		try {
			GestioneLibreria dbManagment = new GestioneLibreria();
			dbManagment.modificaLibro(id, l);
			List<Libro> lista = dbManagment.stampaLibri();
			req.setAttribute("lista", lista);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		req.getRequestDispatcher("modificaLibro.jsp").forward(req, resp);

	}
}
