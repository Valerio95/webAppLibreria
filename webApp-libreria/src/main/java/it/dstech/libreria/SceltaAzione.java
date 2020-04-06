package it.dstech.libreria;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet( urlPatterns = {"/scegliAzione","/"})
public class SceltaAzione extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
			
			req.getRequestDispatcher("welcomeLibreria.jsp").forward(req, resp);	

}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String azione = req.getParameter("azione");
		String path = "/welcomeLibreria.jsp";
		if("Registrati".equalsIgnoreCase(azione)) {
			
			path = "/registrazione.jsp";
			
		} else {
			
			path = "/accedi.jsp";
		}
		req.getRequestDispatcher(path).forward(req, resp);

		
		
	}





}
