package it.dstech.modelli;

public class Libro {
private String titolo;
private String genere;
private String autore;
private int id;
private boolean prenotabilità;
private String giorniConsegna;
private int quantità;
private int prezzo;




public boolean isPrenotabilità() {
	return prenotabilità;
}
public void setPrenotabilità(boolean prenotabilità) {
	this.prenotabilità = prenotabilità;
}
public int getPrezzo() {
	return prezzo;
}
public void setPrezzo(int prezzo) {
	this.prezzo = prezzo;
}
public int getQuantità() {
	return quantità;
}
public void setQuantità(int quantità) {
	this.quantità = quantità;
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
	return "Libro [titolo=" + titolo + ", genere=" + genere + ", autore=" + autore + ", id=" + id + ", prenotabilità="
			+ prenotabilità + ", giorniConsegna=" + giorniConsegna + ", quantità=" + quantità + ", prezzo=" + prezzo
			+ "]";
}


}
