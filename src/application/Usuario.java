package application;

import java.util.ArrayList;

public class Usuario {

	private String user;
	private ArrayList<Juego> listaJuegos;

	public Usuario() {
		super();
	}

	public Usuario(String user, ArrayList<Juego> listaJuegos) {
		super();
		this.user = user;
		this.listaJuegos = listaJuegos;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ArrayList<Juego> getJuegos() {
		return listaJuegos;
	}

	public void setJuegos(ArrayList<Juego> listaJuegos) {
		this.listaJuegos = listaJuegos;
	}

}
