package it.dstech.modelli;

public class Libro {
private String titolo;
private String genere;
private String autore;
private int id;
private boolean prenotabilit�;
private String giorniConsegna;
private int quantit�;
private int prezzo;




public boolean isPrenotabilit�() {
	return prenotabilit�;
}
public void setPrenotabilit�(boolean prenotabilit�) {
	this.prenotabilit� = prenotabilit�;
}
public int getPrezzo() {
	return prezzo;
}
public void setPrezzo(int prezzo) {
	this.prezzo = prezzo;
}
public int getQuantit�() {
	return quantit�;
}
public void setQuantit�(int quantit�) {
	this.quantit� = quantit�;
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

public String getGiorniConsegna() {
	return giorniConsegna;
}
public void setGiorniConsegna(String giorniConsegna) {
	this.giorniConsegna = giorniConsegna;
}
@Override
public String toString() {
	return "Libro [titolo=" + titolo + ", genere=" + genere + ", autore=" + autore + ", id=" + id + ", prenotabilit�="
			+ prenotabilit� + ", giorniConsegna=" + giorniConsegna + ", quantit�=" + quantit� + ", prezzo=" + prezzo
			+ "]";
}


}
