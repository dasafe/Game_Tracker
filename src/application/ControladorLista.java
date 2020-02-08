package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ControladorLista implements Initializable {

	private ObservableList<Usuario> list = FXCollections.observableArrayList();

	@FXML
	private ListView<Usuario> listaUsuarios;

	@FXML
	private Button aceptar;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		listaUsuarios.setCellFactory(param -> new ListCell<Usuario>() {
			@Override
			protected void updateItem(Usuario item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null || item.getUser() == null) {
					setText(null);
				} else {
					setText(item.getUser());
				}
			}
		});
		cargarDatosPrueba();
		listaUsuarios.setItems(list);
	}

	public void cargarDatosPrueba() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
		ArrayList<Juego> listaJuegos = new ArrayList<Juego>();
		listaJuegos.add(new Juego(LocalDate.parse("07/02/21", formatter), LocalDate.parse("07/02/21", formatter),
				"Kingdom Hearts", 10));
		listaJuegos.add(new Juego(LocalDate.parse("08/02/19", formatter), LocalDate.parse("07/02/22", formatter),
				"Loco Roco", 8));
		listaJuegos.add(new Juego(LocalDate.parse("21/02/20", formatter), LocalDate.parse("07/02/21", formatter),
				"Patapon", 8));
		listaJuegos.add(new Juego(LocalDate.parse("02/02/20", formatter), LocalDate.parse("07/02/20", formatter),
				"Crash Bandicoot", 5));

		Usuario user = new Usuario("David", listaJuegos);
		Usuario user2 = new Usuario("Victor", listaJuegos);
		añadirUsuario(user);
		añadirUsuario(user2);
	}

	public void añadirUsuario(Usuario user) {
		// TODO Auto-generated method stub
		list.add(user);
	}

	public void start() {
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("tabla.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(listaUsuarios.getSelectionModel().getSelectedItem().getUser());

			ControladorTabla ct = loader.getController();
			ct.cargarUsuario(listaUsuarios.getSelectionModel().getSelectedItem().getJuegos());

			Stage stage = (Stage) aceptar.getScene().getWindow();
			stage.close();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
