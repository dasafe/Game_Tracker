package application;

import java.util.ArrayList;

public class Usuario {

	private String Username, Password;
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	private ArrayList<Juego> Games;

	public Usuario() {
		super();
	}

	public Usuario(String user, String pass, ArrayList<Juego> listaJuegos) {
		super();
		this.Username = user;
		this.Password = pass;
		this.Games = listaJuegos;
	}

	public String getUser() {
		return Username;
	}

	public void setUser(String user) {
		this.Username = user;
	}

	public ArrayList<Juego> getJuegos() {
		return Games;
	}

	public void setJuegos(ArrayList<Juego> listaJuegos) {
		this.Games = listaJuegos;
	}

	@Override
	public String toString() {
		return "Usuario [user=" + Username + ", listaJuegos=" + Games + "]";
	}

}
