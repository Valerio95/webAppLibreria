package it.dstech.libreria.filtri;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.dstech.modelli.Utente;
import it.dstech.repos.GestioneLibreria;


@WebFilter(filterName = "filtroUtente", urlPatterns = "/utente/*")
public class FiltroUtente implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		HttpServletRequest req =(HttpServletRequest) request;
		HttpServletResponse resp= (HttpServletResponse) response;
		HttpSession session = req.getSession();
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		try {
		GestioneLibreria database = new GestioneLibreria();
		Utente utente = database.prendiUtente(username, password);
		boolean controllo =database.controlloEsistenzaUtente(utente);
		if (controllo==false) {
			
			req.setAttribute("messaggio", "Utente non ancora registrato");
			database.close();
			req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
		} else {
			boolean controlloAttivo=database.controlloValidaUtente(utente);
			if (controlloAttivo==false) {
				
				req.setAttribute("messaggio", scriviRispostaUtenteNonAttivo(utente));
				database.close();
				req.getRequestDispatcher("/welcomeLibreria.jsp").forward(req, resp);
			 }else {
		chain.doFilter(request, response);
			}
		}} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
			
	}

	@Override
	public void destroy() {
	}

	
	private String scriviRispostaUtenteNonAttivo(Utente utente) {
		String mailUtente = utente.getUsername();
		int indexOf = mailUtente.indexOf('@');
		String parteFinaleMail = mailUtente.substring(indexOf);
		String primiDueCaratteri = mailUtente.substring(0, 3);
		String mailFinale = primiDueCaratteri + contaX(indexOf - 2) + parteFinaleMail;
		return "L'utente " + mailFinale + " non ha ancora validato l'email";
	}

	
	
	private String contaX(int numeri) {
		String x = "";
		for (int i = 0; i < numeri; i++) {
			x += "x";
		}
		return x;
	}
	
}
