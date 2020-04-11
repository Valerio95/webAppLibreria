package it.dstech.modelli;

import java.sql.Timestamp;

public class Libro {
private String titolo;
private String genere;
private String autore;
private int id;
private String proprietario;
private boolean prenotabilit‡;
private int quantit‡;
private int prezzo;
private int quantit‡Prenotabile;
private Timestamp dataDiPrenotazione;
private Timestamp dataDiConsegna;
private int idPrenotazione;
private boolean ritardo;








public boolean isRitardo() {
	return ritardo;
}
public void setRitardo(boolean ritardo) {
	this.ritardo = ritardo;
}
public int getIdPrenotazione() {
	return idPrenotazione;
}
public void setIdPrenotazione(int idPrenotazione) {
	this.idPrenotazione = idPrenotazione;
}
public Timestamp getDataDiPrenotazione() {
	return dataDiPrenotazione;
}
public void setDataDiPrenotazione(Timestamp dataDiPrenotazione) {
	this.dataDiPrenotazione = dataDiPrenotazione;
}
public Timestamp getDataDiConsegna() {
	return dataDiConsegna;
}
public void setDataDiConsegna(Timestamp dataDiConsegna) {
	this.dataDiConsegna = dataDiConsegna;
}
public String getProprietario() {
	return proprietario;
}
public void setProprietario(String proprietario) {
	this.proprietario = proprietario;
}
public int getQuantit‡Prenotabile() {
	return quantit‡Prenotabile;
}
public void setQuantit‡Prenotabile(int quantit‡Prenotabile) {
	this.quantit‡Prenotabile = quantit‡Prenotabile;
}
public boolean isPrenotabilit‡() {
	return prenotabilit‡;
}
public void setPrenotabilit‡(boolean prenotabilit‡) {
	this.prenotabilit‡ = prenotabilit‡;
}
public int getPrezzo() {
	return prezzo;
}
public void setPrezzo(int prezzo) {
	this.prezzo = prezzo;
}
public int getQuantit‡() {
	return quantit‡;
}
public void setQuantit‡(int quantit‡) {
	this.quantit‡ = quantit‡;
}
public String getTitolo() {
	return titolo;
}
public void setTitolo(String titolo) {
	this.titolo = titolo;
}
public String getGenere() {
	return genere;
}
public void setGenere(String genere) {
	this.genere = genere;
}
public String getAutore() {
	return autore;
}
public void setAutore(String autore) {
	this.autore = autore;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}


@Override
public String toString() {
	return "Libro [titolo=" + titolo + ", genere=" + genere + ", autore=" + autore + ", id=" + id + ", prenotabilit‡="
			+ prenotabilit‡ + ", quantit‡=" + quantit‡ + ", prezzo=" + prezzo
			+ ", quantit‡Prenotabile=" + quantit‡Prenotabile + "]";
}


}
