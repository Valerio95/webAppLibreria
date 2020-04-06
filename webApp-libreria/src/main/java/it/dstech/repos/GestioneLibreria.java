package it.dstech.repos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import it.dstech.modelli.Libro;
import it.dstech.modelli.Utente;

/*La biblioteca deve permettere la gestione dei prestiti e delle vendite di libri online.
solo un utente registrato può accedere alla biblioteca.
l'utente può cercare i libri tramite una ricerca per nome o per autore. l'utente deve avere
 la possibilità di vedere le sue prenotazioni attuali, quelle passate e anche gli eventuali acquisti
  effettuati.

l'amministratore deve vedere, in ordine, i libri che stanno per finire ed eventualmente riordinarli.
inoltre l'amministratore deve vedere i libri che sono stati affittati da più di 30 giorni per 
sollecitarne la riconsegna.

Mandare una mail agli utenti che hanno affittato un libro per chiederne la restituzione.
usate Servlet 3.1 e file properties per la gestione dei dati del DB*/

public class GestioneLibreria  {

	private Connection connessione;

	public GestioneLibreria() throws SQLException, ClassNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("file.properties"));
		Class.forName(properties.getProperty("db.driver")); 
		String password = properties.getProperty("db.password"); 
		String username = properties.getProperty("db.username"); 
		String url = properties.getProperty("db.url");
		this.connessione = DriverManager.getConnection(url, username, password);
	}
	public boolean aggiungiUtente(Utente u) throws SQLException {
		PreparedStatement controlloUtente = this.connessione.prepareStatement("select * from utenti ");
		ResultSet executeControlloUtente = controlloUtente.executeQuery();
		while (executeControlloUtente.next()) {
			String username=executeControlloUtente.getString(2);
			if(u.getUsername().equals(username)) {
				return false;
			}
		}
		PreparedStatement prepareStatement = this.connessione.prepareStatement("INSERT INTO utenti(username, password, registrazione) VALUES ( ?, ?, ?);");
		prepareStatement.setString(1, u.getUsername());
		prepareStatement.setString(2, u.getPassword());
		prepareStatement.setBoolean(3, false);
		prepareStatement.execute();
		return true;
	}
	public boolean controlloAccessoUtente(Utente u) throws SQLException {
		PreparedStatement controlloUtente = this.connessione.prepareStatement("select * from utenti ");
		ResultSet executeControlloUtente = controlloUtente.executeQuery();
		while (executeControlloUtente.next()) {
			String username=executeControlloUtente.getString(2);
			if(u.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
		
	}
	public boolean controlloValidaUtente(Utente u) throws SQLException {
		PreparedStatement controlloUtente = this.connessione.prepareStatement("select * from utenti where username =? and password=? ");
		ResultSet executeControlloUtente = controlloUtente.executeQuery();
		while (executeControlloUtente.next()) {
		boolean registrazione=	executeControlloUtente.getBoolean(4);
		if(registrazione==false) {
			return false;
		}
		}
		return true;
	}
	public void validaUtente(String username) throws SQLException {
		PreparedStatement prepareStatement = this.connessione.prepareStatement("UPDATE utenti SET registrazione = ? WHERE username = ?;");
		prepareStatement.setBoolean(1, true);
		prepareStatement.setString(2, username);
		prepareStatement.execute();
	}
	
	public boolean aggiungiLibro(Libro l) throws SQLException {
		PreparedStatement controlloLibri = this.connessione.prepareStatement("select * from libri ");
		ResultSet executeControlloLibri = controlloLibri.executeQuery();
		
		while (executeControlloLibri.next()) {
			String titolo=executeControlloLibri.getString(2);
			int quantità=executeControlloLibri.getInt(6);
			String autore=executeControlloLibri.getString(4);
			
		if(l.getTitolo().equalsIgnoreCase(titolo)&&l.getAutore().equalsIgnoreCase(autore)) {
			PreparedStatement updateQuery = this.connessione.prepareStatement("Update libri set quantità = ? where titolo = ? and autore=?");
			updateQuery.setInt(1, l.getQuantità()+quantità);
			updateQuery.setString(2, l.getTitolo());
			updateQuery.setString(3, l.getAutore());
			updateQuery.execute();
			return true;
		}
		} 
			PreparedStatement prepareStatement = this.connessione.prepareStatement("INSERT INTO libri(titolo, genere, autore, prenotazione, quantità, prezzo) VALUES ( ?, ?, ?, ?, ?, ?);");
			prepareStatement.setString(1, l.getTitolo());
			prepareStatement.setString(2, l.getGenere());
			prepareStatement.setString(3, l.getAutore());
			prepareStatement.setBoolean(4, true);
			prepareStatement.setInt(5, l.getQuantità());
			prepareStatement.setInt(6, l.getPrezzo());

			prepareStatement.execute();
		
		return true;
		
		}
	
	
	public void modificaLibro(int idLibro,Libro l) throws SQLException {
		PreparedStatement updateQuery = this.connessione.prepareStatement("Update libri set titolo= ?, genere= ?, autore= ?,  quantità = ? where id = ?;");
        updateQuery.setString(1, l.getTitolo());
        updateQuery.setString(2, l.getGenere());
        updateQuery.setString(3, l.getAutore());
        updateQuery.setInt(4, l.getQuantità());
        updateQuery.setInt(5, idLibro);

        updateQuery.execute();
		
	}
	public void modificaUtente(String password,Utente u) throws SQLException {
		PreparedStatement updateQuery = this.connessione.prepareStatement("Update utenti set username= ?, password= ?   where password = ?;");
        updateQuery.setString(1, u.getUsername());
        updateQuery.setString(2, u.getPassword());
        updateQuery.setString(3, password);
        

        updateQuery.execute();
		
	}
	public void rimuoviUtente(int id) throws SQLException {
		PreparedStatement prepareStatement = this.connessione.prepareStatement("delete from utenti where id = ? limit 1");
		prepareStatement.setInt(1,id);
		prepareStatement.execute();
	}
	public void rimuoviLibro(int id) throws SQLException {
		PreparedStatement prepareStatement = this.connessione.prepareStatement("delete from libri where id = ? limit 1");
		prepareStatement.setInt(1,id);
		prepareStatement.execute();
	}
	public void vendiLibro(int idUtente, Libro l ) throws SQLException {
		Libro libro =new Libro();
		 PreparedStatement prepareStatement = this.connessione.prepareStatement("select * from libri where id = ? limit 1");
			prepareStatement.setInt(1, l.getId());
			ResultSet executeQuery = prepareStatement.executeQuery();
			
			while (executeQuery.next()) {
				libro.setId(executeQuery.getInt(1));
				libro.setTitolo(executeQuery.getString(2));
				libro.setGenere(executeQuery.getString(3));
				libro.setAutore(executeQuery.getString(4));
				libro.setPrenotabilità(executeQuery.getBoolean(5));
				libro.setQuantità(executeQuery.getInt(6));
				libro.setPrezzo(executeQuery.getInt(8));
			}
			
			if(!l.isPrenotabilità()) {
				
			}
		
	}
	public void close() throws SQLException {
		this.connessione.close();
	}
}
