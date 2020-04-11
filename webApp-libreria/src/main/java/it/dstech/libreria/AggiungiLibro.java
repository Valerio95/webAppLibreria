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



@WebServlet( urlPatterns = {"/aggiungiLibro"})
public class AggiungiLibro extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String titolo = req.getParameter("titolo");
		String autore = req.getParameter("autore");
		String genere = req.getParameter("genere");
		int quantità =Integer.parseInt(req.getParameter("quantità")) ;
		int prezzo =Integer.parseInt(req.getParameter("prezzo")) ;
		Libro l =new Libro();
		l.setTitolo(titolo);
		l.setAutore(autore);	
		l.setGenere(genere);	
		l.setQuantità(quantità);
		l.setPrezzo(prezzo);	
try {
	GestioneLibreria gestione = new GestioneLibreria();
	gestione.aggiungiLibro(l);
	List<Libro> lista = gestione.stampaLibri();
	req.setAttribute("lista", lista);
}catch (ClassNotFoundException | SQLException e) {
	
	e.printStackTrace();
}

req.getRequestDispatcher("aggiungiLibro.jsp").forward(req, resp);
	}

}
