package application;

import java.io.IOException;
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
	public void start(Stage primaryStage) throws IOException {
		// cargarDatosPrueba();
		try {
			Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					toast.WatchDir.main(args);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		thread.setDaemon(true);

		thread.start();

		launch(args);
	}

	public void cargarDatosPrueba() throws IOException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

		/*
		 * ArrayList<Juego> listaJuegos = new ArrayList<Juego>(); listaJuegos.add(new
		 * Juego(LocalDate.parse("07/02/21", formatter), LocalDate.parse("07/02/21",
		 * formatter), "Kingdom Hearts", 10)); listaJuegos.add(new
		 * Juego(LocalDate.parse("08/02/19", formatter), LocalDate.parse("07/02/22",
		 * formatter), "Loco Roco", 8)); listaJuegos.add(new
		 * Juego(LocalDate.parse("21/02/20", formatter), LocalDate.parse("07/02/21",
		 * formatter), "B6C926577DE2BBAFC6BA06B8EEE44CC3", 8)); listaJuegos.add(new
		 * Juego(LocalDate.parse("02/02/20", formatter), LocalDate.parse("07/02/20",
		 * formatter), "Crash Bandicoot", 5));
		 * 
		 * Usuario user = new Usuario("David", listaJuegos);
		 * 
		 * usuarios.add(user);
		 */

	}
}
