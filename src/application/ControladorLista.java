package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.event.EventHandler;

public class ControladorLista implements Initializable {

	private ObservableList<Usuario> list = FXCollections.observableArrayList();

	private Stage primaryStage;

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
		añadirUsuario();
		listaUsuarios.setItems(list);
	}

	public void añadirUsuario() {
		// TODO Auto-generated method stub
		for (Usuario usuario : Main.usuarios) {
			list.add(usuario);
		}
	}

	public void start() {
		primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("tabla.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root, 1280, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(listaUsuarios.getSelectionModel().getSelectedItem().getUser());

			primaryStage.setOnCloseRequest(confirmCloseEventHandler);

			ControladorTabla ct = loader.getController();
			ct.cargarUsuario(listaUsuarios.getSelectionModel().getSelectedItem().getJuegos());
			ct.usuario = listaUsuarios.getSelectionModel().getSelectedItem().getUser();

			Stage stage = (Stage) aceptar.getScene().getWindow();
			stage.close();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
		Alert closeConfirmation = new Alert(Alert.AlertType.CONFIRMATION, "Estas seguro de que quieres salir?");
		Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(ButtonType.OK);
		exitButton.setText("Salir");
		closeConfirmation.setHeaderText("Confirmar");
		closeConfirmation.initModality(Modality.APPLICATION_MODAL);
		closeConfirmation.initOwner(primaryStage);

		Optional<ButtonType> closeResponse = closeConfirmation.showAndWait();
		if (!ButtonType.OK.equals(closeResponse.get())) {
			event.consume();
		}
	};
}
