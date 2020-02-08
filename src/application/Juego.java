package application;

import java.time.LocalDate;

public class Juego {

	private LocalDate fecha_inicio;
	private LocalDate fecha_fin;
	private String nombre;
	private int nota;

	public Juego(LocalDate fecha_inicio, LocalDate fecha_fin, String nombre, int nota) {
		super();
		this.fecha_inicio = fecha_inicio;
		this.fecha_fin = fecha_fin;
		this.nombre = nombre;
		this.nota = nota;
	}

	public Juego() {
		super();
	}

	public LocalDate getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(LocalDate fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public LocalDate getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(LocalDate fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}

}
