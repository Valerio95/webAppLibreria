package it.dstech.modelli;

import java.util.ArrayList;
import java.util.List;

public class Utente {
private String username;
private String password;
private int id;
private boolean attivo;
List<Libro> libriPrenotati = new ArrayList<Libro>();
List<Libro> libriAcquistati = new ArrayList<Libro>();
private String image;


public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public boolean isAttivo() {
	return attivo;
}
public void setAttivo(boolean attivo) {
	this.attivo = attivo;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public List<Libro> getLibriPrenotati() {
	return libriPrenotati;
}
public void setLibriPrenotati(List<Libro> libriPrenotati) {
	this.libriPrenotati = libriPrenotati;
}
public List<Libro> getLibriAcquistati() {
	return libriAcquistati;
}
public void setLibriAcquistati(List<Libro> libriAcquistati) {
	this.libriAcquistati = libriAcquistati;
}
@Override
public String toString() {
	return "Utente [username=" + username + ", password=" + password + ", id=" + id + ", attivo=" + attivo
			+ ", libriPrenotati=" + libriPrenotati + ", libriAcquistati=" + libriAcquistati + "]";
}

}
