package application;

import javafx.scene.image.ImageView;

public class Logro {

	private ImageView img;
	private String titulo, descripcion;

	public Logro(ImageView img, String titulo, String descripcion) {
		super();
		this.img = img;
		this.titulo = titulo;
		this.descripcion = descripcion;
	}

	public Logro() {
		super();
	}

	public ImageView getImg() {
		return img;
	}

	public void setImg(ImageView img) {
		this.img = img;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
