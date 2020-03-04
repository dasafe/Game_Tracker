package application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	public static ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	
	@Override
	public void start(Stage primaryStage) {
		cargarDatosPrueba();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("lista.fxml"));
			Scene scene = new Scene(root, 245, 486);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void cargarDatosPrueba() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
		ArrayList<Juego> listaJuegos = new ArrayList<Juego>();
		listaJuegos.add(new Juego(LocalDate.parse("07/02/21", formatter), LocalDate.parse("07/02/21", formatter),
				"Kingdom Hearts", 10));
		listaJuegos.add(new Juego(LocalDate.parse("08/02/19", formatter), LocalDate.parse("07/02/22", formatter),
				"Loco Roco", 8));
		listaJuegos.add(new Juego(LocalDate.parse("21/02/20", formatter), LocalDate.parse("07/02/21", formatter),
				"B6C926577DE2BBAFC6BA06B8EEE44CC3", 8));
		listaJuegos.add(new Juego(LocalDate.parse("02/02/20", formatter), LocalDate.parse("07/02/20", formatter),
				"Crash Bandicoot", 5));

		Usuario user = new Usuario("David", listaJuegos);
		
		ArrayList<Juego> listaJuegos2 = new ArrayList<Juego>();
		listaJuegos2.add(new Juego(LocalDate.parse("07/02/21", formatter), LocalDate.parse("07/02/21", formatter),
				"Half Life 2", 10));
		listaJuegos2.add(new Juego(LocalDate.parse("08/02/19", formatter), LocalDate.parse("07/02/22", formatter),
				"Bloodborne", 9));
		listaJuegos2.add(new Juego(LocalDate.parse("21/02/20", formatter), LocalDate.parse("07/02/21", formatter),
				"Spider-Man", 8));
		listaJuegos2.add(new Juego(LocalDate.parse("02/02/20", formatter), LocalDate.parse("07/02/20", formatter),
				"Hollow Knight", 9));
		
		Usuario user2 = new Usuario("Victor", listaJuegos2);
		usuarios.add(user);
		usuarios.add(user2);
	}
}
