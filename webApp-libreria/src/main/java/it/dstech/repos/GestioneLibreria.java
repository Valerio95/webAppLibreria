package it.dstech.repos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import it.dstech.modelli.Libro;
import it.dstech.modelli.Utente;
import it.dstech.modelli.Scontrino;

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

	static Scontrino scontrino =new Scontrino();
	 static List<Integer> idLibriVenduti = new ArrayList<>();
	static List<Libro> libriAffittati = new ArrayList<Libro>();
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
	
	
	public boolean aggiungiUtente(Utente u, InputStream immagine) throws SQLException {
		PreparedStatement controlloUtente = this.connessione.prepareStatement("select * from utenti ");
		ResultSet executeControlloUtente = controlloUtente.executeQuery();
		while (executeControlloUtente.next()) {
			String username=executeControlloUtente.getString(2);
			if(u.getUsername().equals(username)) {
				return false;
			}
		}
		PreparedStatement prepareStatement = this.connessione.prepareStatement("INSERT INTO utenti(username, password, registrazione, immagine) VALUES ( ?, ?, ?, ?);");
		prepareStatement.setString(1, u.getUsername());
		prepareStatement.setString(2, u.getPassword());
		prepareStatement.setBoolean(3, false);
		prepareStatement.setBlob(4, immagine);
		prepareStatement.execute();
		return true;
	}
	
	
	
	public Utente prendiUtente(String username, String password) throws SQLException, IOException {
		PreparedStatement statement = connessione
				.prepareStatement("select * from utente where username = ? and password = ?");
		statement.setString(1, username);
		statement.setString(2, password);
		ResultSet executeQuery = statement.executeQuery();
		while (executeQuery.next()) {
			String u = executeQuery.getString("username");
			String p = executeQuery.getString("password");
			boolean ac = executeQuery.getBoolean("4");
			Blob blob = executeQuery.getBlob("5");
			InputStream inputStream = blob.getBinaryStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			byte[] imageBytes = outputStream.toByteArray();
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            Utente utente =new Utente();
            utente.setUsername(u);
            utente.setPassword(p);
            utente.setAttivo(ac);
            utente.setImage(base64Image);
            return utente;
		}
		return null;
	}
	
	
	
	
	
	public boolean controlloEsistenzaUtente(Utente u) throws SQLException {
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
		controlloUtente.setString(1,u.getUsername());
		controlloUtente.setString(2,u.getPassword());

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
	
	
	public int trovaIdUtente (String username) throws SQLException {
		PreparedStatement controlloUtente = this.connessione.prepareStatement("select * from utenti where username =? ");
		controlloUtente.setString(1,username);
		ResultSet executeControlloUtente = controlloUtente.executeQuery();
		int	idUtente=0;
		while (executeControlloUtente.next()) {
			idUtente=executeControlloUtente.getInt(1);
		}
		return idUtente;
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
		PreparedStatement updateQuery = this.connessione.prepareStatement("Update libri set titolo= ?, genere= ?, autore= ?,  quantità = ?, prezzo=? where id = ?;");
        updateQuery.setString(1, l.getTitolo());
        updateQuery.setString(2, l.getGenere());
        updateQuery.setString(3, l.getAutore());
        updateQuery.setInt(4, l.getQuantità());
        updateQuery.setInt(5, idLibro);
        updateQuery.setInt(6, l.getPrezzo());

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

	
	
	
	
	public boolean vendiLibro(int idUtente, Libro l ) throws SQLException {
		
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
			if (l.getQuantità() > libro.getQuantità()) {
				return false;
			}
			
			Libro prodottoCarrello = new Libro();
			prodottoCarrello.setId(l.getId());
			prodottoCarrello.setTitolo(libro.getTitolo());
			prodottoCarrello.setAutore(libro.getAutore());
			prodottoCarrello.setGenere(libro.getGenere());
			prodottoCarrello.setQuantità(l.getQuantità());
			for(int i=0;i<l.getQuantità();i++) {
			prodottoCarrello.setPrezzo(libro.getPrezzo()+prodottoCarrello.getPrezzo());
			}
			
              if (l.getQuantità()==libro.getQuantità()) {
				
				scontrino.getLibriAquistati().add(prodottoCarrello);
				PreparedStatement prepareStatement3 = this.connessione.prepareStatement("INSERT INTO libri_acquistati(numero_vendite, idLibro, id_scontrino, idUtente) VALUES ( ?, ?, ?,?);");
				prepareStatement3.setInt(1,l.getQuantità());
				prepareStatement3.setInt(2,l.getId());
				prepareStatement3.setInt(3,0);
				prepareStatement3.setInt(4,idUtente);
				prepareStatement3.execute();
				PreparedStatement deleteQuery = this.connessione.prepareStatement("Delete from libri where id = ?");
				deleteQuery.setInt(1, l.getId());
				deleteQuery.execute();
				PreparedStatement prepareStatement2 = this.connessione.prepareStatement("select Max(id) from libri_acquistati where idLibri =?;");
				prepareStatement2.setInt(1, l.getId());
	            ResultSet executeQuery2 = prepareStatement2.executeQuery();
	           int idScontrino=0;
	            while(executeQuery2.next()) {
	            	idScontrino =executeQuery2.getInt(1);
	            }
	            idLibriVenduti.add(idScontrino);
				return true;
              }else {
      			
      			scontrino.getLibriAquistati().add(prodottoCarrello);
      			PreparedStatement updateQuery = this.connessione.prepareStatement("Update libri set quantità = ? where id = ?");
      			updateQuery.setInt(1, libro.getQuantità() - l.getQuantità());
      			updateQuery.setInt(2, libro.getId());
      			updateQuery.execute();
      			PreparedStatement prepareStatement3 = this.connessione.prepareStatement("INSERT INTO libri_acquistati(numero_vendite, idLibro, id_scontrino) VALUES ( ?, ?, ?);");
      			prepareStatement3.setInt(1,l.getQuantità());
      			prepareStatement3.setInt(2,l.getId());
      			prepareStatement3.setInt(3,0);
      			prepareStatement3.execute();
      			PreparedStatement prepareStatement2 = this.connessione.prepareStatement("select Max(id) from libri_acquistati where idLibro=?;");
      			prepareStatement2.setInt(1, l.getId());
                  ResultSet executeQuery2 = prepareStatement2.executeQuery();
      			int idScontrino=0;
                  while(executeQuery2.next()) {
                  	idScontrino =executeQuery2.getInt(1);
                  }
                  idLibriVenduti.add(idScontrino);
      			return true;

      			}
			
	}
	
	
	
	public void creaScontrino(int idUtente) throws SQLException {
		PreparedStatement prepareStatement = this.connessione.prepareStatement("INSERT INTO scontrini(idUtente,spesaTotale) VALUES ( ?, ?);");
		prepareStatement.setInt(1, idUtente);
		prepareStatement.setInt(2, scontrino.calcolaPrezzoTotale());
		prepareStatement.execute();
        
	}
	
	
	
	public void assegnaIdScontrino(int idUtente) throws SQLException {
		PreparedStatement prepareStatement2 = this.connessione.prepareStatement("select Max(id) from scontrini where idUtente=?;");
		prepareStatement2.setInt(1, idUtente);
        ResultSet executeQuery = prepareStatement2.executeQuery();
       int idScontrino=0;
        while(executeQuery.next()) {
        	idScontrino =executeQuery.getInt(1);
        }      
        for(Integer idLibro: idLibriVenduti ) {
			PreparedStatement updateQuery = this.connessione.prepareStatement("Update libri_acquistati set id_scontrino = ? where id = ?;");
            updateQuery.setInt(1, idScontrino);
            updateQuery.setInt(2, idLibro);
            updateQuery.execute();		
        }
	}
	
	
	
	public void affittaLibro(int idCliente, Libro l) throws SQLException {
		
		Libro libro =new Libro();
		 PreparedStatement prepareStatement = this.connessione.prepareStatement("select * from libri where id = ? limit 1");
			prepareStatement.setInt(1, l.getId());
			ResultSet executeQuery = prepareStatement.executeQuery();
			int quantitàPrenotata=0;
			while (executeQuery.next()) {
				libro.setId(executeQuery.getInt(1));
				libro.setTitolo(executeQuery.getString(2));
				libro.setGenere(executeQuery.getString(3));
				libro.setAutore(executeQuery.getString(4));
				libro.setPrenotabilità(executeQuery.getBoolean(5));
				libro.setPrezzo(executeQuery.getInt(8));
				libro.setQuantità(executeQuery.getInt(6));
				quantitàPrenotata=executeQuery.getInt(7);
			}
			
			Libro prodottoCarrello = new Libro();
			prodottoCarrello.setId(l.getId());
			prodottoCarrello.setTitolo(libro.getTitolo());
			prodottoCarrello.setAutore(libro.getAutore());
			
			PreparedStatement prepareStatement2 = this.connessione.prepareStatement("INSERT INTO libri_prenotati( idLibroPrenotato, idUtente, consegnato, dataConsegna, ritardo) VALUES ( ?, ?, ?, ?, ?);");
  			prepareStatement2.setInt(2,idCliente);
  			prepareStatement2.setInt(1,l.getId());
  			prepareStatement2.setTimestamp(4,  dataConsegna());
  			prepareStatement2.setBoolean(3,false);
  			prepareStatement2.setBoolean(5,false);
  			prepareStatement2.execute();
			if (libro.getQuantità()-quantitàPrenotata==1) {
				PreparedStatement prepareStatement3 = this.connessione.prepareStatement("Update libri set prenotazione = ? where id = ?;");
	  			prepareStatement3.setBoolean(1,false);
	  			prepareStatement3.setInt(2,l.getId());
	  			prepareStatement3.execute();

			}
			
			PreparedStatement prepareStatement3 = this.connessione.prepareStatement("Update libri set quantitàPrenotata = ? where id = ?;");
  			prepareStatement3.setInt(1,quantitàPrenotata+1);
  			prepareStatement3.setInt(2,l.getId());
  			prepareStatement3.execute();
            libriAffittati.add(prodottoCarrello);
	}
	
	
	
	public List<Libro> stampaLibriAffittati(){
		
		return libriAffittati;
	}
	
	
	
	public void restituisciLibro(int idLibro,int idUtente) throws SQLException {
		PreparedStatement updateQuery = this.connessione.prepareStatement("Update libri_prenotati set consegnato = ? where idUtente = ? and idLibroPrenotato=?;");
        updateQuery.setBoolean(1, true);
        updateQuery.setInt(2, idUtente);
        updateQuery.setInt(3, idLibro);
        updateQuery.execute();
        PreparedStatement prepareStatement = this.connessione.prepareStatement("select * from libri where id = ? limit 1");
		prepareStatement.setInt(1,idLibro);
		ResultSet executeQuery = prepareStatement.executeQuery();
		while (executeQuery.next()) {
        PreparedStatement updateQuery2 = this.connessione.prepareStatement("Update libri set quantitàPrenotata = ? where id=?;");
        updateQuery2.setInt(1, executeQuery.getInt(7)-1);
        updateQuery2.setInt(2, idLibro);
        updateQuery2.execute();
		}
		
	}
	 
	
	
	
	public Timestamp dataConsegna() {
		LocalDateTime day = LocalDateTime.now().plusMonths(1);
	     Timestamp giornoConsegna = Timestamp.valueOf(day);
return giornoConsegna;
	}
	
	public Timestamp dataAttuale() {
		LocalDateTime day = LocalDateTime.now();
	     Timestamp giornoConsegna = Timestamp.valueOf(day);
return giornoConsegna;
	}
	
	
	public List<Libro> stampaCarrello(){
		List<Libro> carrello=new ArrayList<>();
		for (Libro prodotto: scontrino.getLibriAquistati()) {
			carrello.add(prodotto);
		}
		return carrello;
	}
	
	
	
	public String usernameUtente(int idUtente) throws SQLException{
	    PreparedStatement updateQuery = this.connessione.prepareStatement("select * from utenti where id=?;");
	    updateQuery.setInt(1, idUtente);
	    ResultSet executeQuery = updateQuery.executeQuery();
		String username= "";
		while(executeQuery.next()) {
			username=executeQuery.getString(2);
		}
		return username;
	}
	
	
	
	public void controllaRitardoRestituzione() throws SQLException {
		List<Libro> listalibri= AllLibriNonRestituiti();
		for(Libro libro: listalibri) {
			if((dataAttuale().after(libro.getDataDiConsegna()))){
			PreparedStatement updateQuery = this.connessione.prepareStatement("Update libri_prenotati set ritardo = ? where id= ?;");
			updateQuery.setBoolean(1,true);
			updateQuery.setInt(2, libro.getIdPrenotazione());
			}
		}
	}
	
	
	
	
	public List<Libro> AllLibriNonRestituiti () throws SQLException{
		
		List<Libro> listaLibri= new ArrayList<>();
		
		
		PreparedStatement updateQuery = this.connessione.prepareStatement("select * from libri_prenotati where  consegnato=?;");
		updateQuery.setBoolean(1, false);
		ResultSet executeQuery = updateQuery.executeQuery();
		while(executeQuery.next()) {
			Libro libro =new Libro();
			int id =executeQuery.getInt(3);
			String username=usernameUtente(id);
			libro.setProprietario(username);
			libro.setIdPrenotazione(executeQuery.getInt(1));
			libro.setId(executeQuery.getInt(2));
			libro.setDataDiConsegna(executeQuery.getTimestamp(5));
            libro.setDataDiPrenotazione(executeQuery.getTimestamp(4));
			PreparedStatement updateQuery2 = this.connessione.prepareStatement("select * from libri where id=?;");
			updateQuery2.setInt(1, libro.getId());
			ResultSet executeQuery2 = updateQuery2.executeQuery();
			while(executeQuery2.next()) {
				
				libro.setTitolo(executeQuery2.getString(2));
				libro.setAutore(executeQuery2.getString(4));
				libro.setGenere(executeQuery2.getString(3));
				listaLibri.add(libro);
			}
			}
		return listaLibri;

		}
	
	
	
	
	
	
	public List<Libro> libriNonRestituiti (int idUtente) throws SQLException{
		PreparedStatement updateQuery = this.connessione.prepareStatement("select * from libri_prenotati where idUtente=? and consegnato=?;");
		updateQuery.setInt(1, idUtente);
		updateQuery.setBoolean(2, false);
		ResultSet executeQuery = updateQuery.executeQuery();
		List<Libro> listaLibri= new ArrayList<>();
		while(executeQuery.next()) {
			Libro libro =new Libro();
			libro.setId(executeQuery.getInt(2));
            libro.setDataDiPrenotazione(executeQuery.getTimestamp(4));
			PreparedStatement updateQuery2 = this.connessione.prepareStatement("select * from libri where id=?;");
			updateQuery2.setInt(1, libro.getId());
			ResultSet executeQuery2 = updateQuery2.executeQuery();
			while(executeQuery2.next()) {
				
				libro.setTitolo(executeQuery2.getString(2));
				libro.setAutore(executeQuery2.getString(4));
				libro.setGenere(executeQuery2.getString(3));
				listaLibri.add(libro);
			}
			}
		return listaLibri;

		}
	
	
	
	public List<Libro> libriRestituiti (int idUtente) throws SQLException{
		PreparedStatement updateQuery = this.connessione.prepareStatement("select * from libri_prenotati where idUtente=? and consegnato=?;");
		updateQuery.setInt(1, idUtente);
		updateQuery.setBoolean(2, true);
		ResultSet executeQuery = updateQuery.executeQuery();
		List<Libro> listaLibri= new ArrayList<>();
		while(executeQuery.next()) {
			Libro libro =new Libro();
			libro.setId(executeQuery.getInt(2));
            libro.setDataDiPrenotazione(executeQuery.getTimestamp(4));
			PreparedStatement updateQuery2 = this.connessione.prepareStatement("select * from libri where id=?;");
			updateQuery2.setInt(1, libro.getId());
			ResultSet executeQuery2 = updateQuery2.executeQuery();
			while(executeQuery2.next()) {
				libro.setTitolo(executeQuery2.getString(2));
				libro.setGenere(executeQuery2.getString(3));
			    libro.setAutore(executeQuery2.getString(4));

				listaLibri.add(libro);
			}
			}
		return listaLibri;

		}
	
	
	
	
	public List<Libro> cercaLibroPerTitolo(String titolo) throws SQLException{
		PreparedStatement updateQuery = this.connessione.prepareStatement("select * from libri where titolo=?;");
		updateQuery.setString(1, titolo);
		ResultSet executeQuery = updateQuery.executeQuery();
		List<Libro> listaLibri= new ArrayList<>();
		while(executeQuery.next()) {
		Libro libro =new Libro();
		libro.setTitolo(titolo);
		libro.setId(executeQuery.getInt(1));
		libro.setAutore(executeQuery.getString(4));
		libro.setGenere(executeQuery.getString(3));
		libro.setPrenotabilità(executeQuery.getBoolean(5));
		libro.setQuantità(executeQuery.getInt(6));
		libro.setPrezzo(executeQuery.getInt(8));
		libro.setQuantitàPrenotabile(executeQuery.getInt(6)-executeQuery.getInt(7));

		listaLibri.add(libro);
		}
		return listaLibri;
	}
	
	
	
	public List<Libro> cercaLibroPerAutore(String autore) throws SQLException{
		PreparedStatement updateQuery = this.connessione.prepareStatement("select * from libri where autore=?;");
		updateQuery.setString(1, autore);
		ResultSet executeQuery = updateQuery.executeQuery();
		List<Libro> listaLibri= new ArrayList<>();
		while(executeQuery.next()) {
		Libro libro =new Libro();
		libro.setTitolo(autore);
		libro.setId(executeQuery.getInt(1));
		libro.setAutore(executeQuery.getString(4));
		libro.setGenere(executeQuery.getString(3));
		libro.setPrenotabilità(executeQuery.getBoolean(5));
		libro.setQuantità(executeQuery.getInt(6));
		libro.setPrezzo(executeQuery.getInt(8));
		libro.setQuantitàPrenotabile(executeQuery.getInt(6)-executeQuery.getInt(7));

		listaLibri.add(libro);
		}
		return listaLibri;
	}
	
	
	
	
	public List<Libro> stampaLibri() throws SQLException{
		PreparedStatement updateQuery = this.connessione.prepareStatement("select * from libri ;");
		
		ResultSet executeQuery = updateQuery.executeQuery();
		List<Libro> listaLibri= new ArrayList<>();
		while(executeQuery.next()) {
		Libro libro =new Libro();
		libro.setTitolo(executeQuery.getString(2));
		libro.setId(executeQuery.getInt(1));
		libro.setAutore(executeQuery.getString(4));
		libro.setGenere(executeQuery.getString(3));
		libro.setPrenotabilità(executeQuery.getBoolean(5));
		libro.setQuantità(executeQuery.getInt(6));
		libro.setPrezzo(executeQuery.getInt(8));
		libro.setQuantitàPrenotabile(executeQuery.getInt(6)-executeQuery.getInt(7));
		listaLibri.add(libro);
		}
		return listaLibri;
	}
	
	
	public List<Scontrino> scontriniCliente(int idCliente) throws SQLException {
		PreparedStatement updateQuery = this.connessione.prepareStatement("select * from scontrini where idUtente=?;");
		updateQuery.setInt(1, idCliente);
		ResultSet executeQuery = updateQuery.executeQuery();
		List<Scontrino> listaScontrini= new ArrayList<>();
		while(executeQuery.next()) {
		Scontrino scontrino =new Scontrino();
		scontrino.setDataDiEmissione(executeQuery.getTimestamp(4));
		scontrino.setId(executeQuery.getInt(1));
		scontrino.setPrezzoTotale(executeQuery.getInt(3));
		listaScontrini.add(scontrino);
		}
		return listaScontrini;
	}
	
	public List<Libro> dettagliScontrino(int idScontrino) throws SQLException{
		PreparedStatement updateQuery = this.connessione.prepareStatement("select * from libri_acquistati where id_scontrino=?;");
		updateQuery.setInt(1, idScontrino);
		ResultSet executeQuery = updateQuery.executeQuery();
		
		List<Libro> dettagli= new ArrayList<>();
		while(executeQuery.next()) {
		Libro libro =new Libro();
		int id = executeQuery.getInt(3);
		PreparedStatement updateQuery2 = this.connessione.prepareStatement("select * from libri where id=?;");
		updateQuery2.setInt(1, id);
		ResultSet executeQuery2 = updateQuery2.executeQuery();
		while(executeQuery2.next()) {
			libro.setTitolo(executeQuery2.getString(2));
			libro.setAutore(executeQuery2.getString(4));
			libro.setPrezzo(executeQuery2.getInt(8));
		}
		
		libro.setQuantità(executeQuery.getInt(2));
		dettagli.add(libro);
		}
		return dettagli;
	}
	
	
	
	
	public List<Libro> creaListaVendite() throws SQLException {
		List<Libro> elenco = new ArrayList<>();
		PreparedStatement trovaIdProdotto = connessione
				.prepareStatement("select distinct idLibro from libri_acquistati;");
		ResultSet executetrovaIdProdotto = trovaIdProdotto.executeQuery();
		List<Integer> elencoIdProdotti = new ArrayList<>();
		
		while (executetrovaIdProdotto.next()) {
			int idLibro = executetrovaIdProdotto.getInt(1);
			elencoIdProdotti.add(idLibro);
		} 
		for(Integer idLibro:elencoIdProdotti ) {
			
		
		PreparedStatement updateQuery = this.connessione.prepareStatement("select * from libri_acquistati where idLibro=?;");
		updateQuery.setInt(1, idLibro);
		ResultSet executeQuery = updateQuery.executeQuery();
		Libro temp = new Libro();
		
		while(executeQuery.next()) {	
			temp.setQuantità(executeQuery.getInt(5)+temp.getQuantità());
		}
		PreparedStatement updateQuery2 = this.connessione.prepareStatement("select * from libri where id=?;");
		updateQuery2.setInt(1, idLibro);
		ResultSet executeQuery2 = updateQuery2.executeQuery();
		while(executeQuery2.next()) {
			temp.setPrezzo(executeQuery2.getInt(8));
			temp.setTitolo(executeQuery2.getString(2));
			temp.setAutore(executeQuery2.getString(4));
			temp.setGenere(executeQuery2.getString(3));

			elenco.add(temp);
		}
		}
		return elenco;
	}
	
	
	
	public List<Utente> listaUtenti() throws SQLException{
		PreparedStatement updateQuery = this.connessione.prepareStatement("select * from utenti;");
		ResultSet executeQuery = updateQuery.executeQuery();
		List<Utente> elenco = new ArrayList<>();
		while(executeQuery.next()) {
			Utente temp = new Utente();
			temp.setId(executeQuery.getInt(1));
			temp.setUsername(executeQuery.getString(2));
			

			elenco.add(temp);
		}
		return elenco;
	}
	
	
	
	public void close() throws SQLException {
		this.connessione.close();
	}
}
