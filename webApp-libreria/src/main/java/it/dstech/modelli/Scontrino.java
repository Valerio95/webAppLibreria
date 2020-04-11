package it.dstech.modelli;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Scontrino {
	private int id;
	private int idCliente;
	List<Libro> libriAquistati = new ArrayList<>();
	private Timestamp dataDiEmissione;
	private int prezzoTotale;



	public List<Libro> getLibriAquistati() {
		return libriAquistati;
	}
	public void setLibriAquistati(List<Libro> libriAquistati) {
		this.libriAquistati = libriAquistati;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public int getPrezzoTotale() {
		return prezzoTotale;
	}
	public void setPrezzoTotale(int prezzoTotale) {
		this.prezzoTotale = prezzoTotale;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Timestamp getDataDiEmissione() {
		return dataDiEmissione;
	}
	public void setDataDiEmissione(Timestamp dataDiEmissione) {
		this.dataDiEmissione = dataDiEmissione;
	}
	public int calcolaPrezzoTotale() {
		for(Libro prodotto:libriAquistati) {
			this.prezzoTotale+=prodotto.getPrezzo();
		}
		return this.prezzoTotale;
	}
	@Override
	public String toString() {
		return "Scontrino [id=" + id + ", idCliente=" + idCliente + ", libriAquistati=" + libriAquistati
				+ ", dataDiEmissione=" + dataDiEmissione + ", prezzoTotale=" + prezzoTotale + "]";
	}
}
