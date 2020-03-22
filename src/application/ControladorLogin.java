package application;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class ControladorLogin implements Initializable {

	private Stage primaryStage;
	
	@FXML
	private TextField user, password;

	@FXML
	private Button login, register;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void intentoLogin() throws IOException {
		String query_url = "https://game-tracker-api.herokuapp.com/users/login";
		String json = "{\"name\" : \"" + user.getText() + "\", \"password\": \"" + password.getText() + "\" }";

		URL url = new URL(query_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");

		OutputStream os = conn.getOutputStream();
		os.write(json.getBytes("UTF-8"));
		os.close();

		InputStream in = new BufferedInputStream(conn.getInputStream());
		String result = IOUtils.toString(in, "UTF-8");
		JSONObject obj = new JSONObject(result);
		String mensaje = obj.getString("mensaje");
		if (mensaje.equals("Autenticacion correcta")) {
			primaryStage = new Stage();
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("tabla.fxml"));
				Parent root = (Parent) loader.load();
				Scene scene = new Scene(root, 1280, 720);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.setTitle(user.getText());

				primaryStage.setOnCloseRequest(confirmCloseEventHandler);

				ControladorTabla ct = loader.getController();
				//ct.cargarUsuario(listaUsuarios.getSelectionModel().getSelectedItem().getJuegos());
				//ct.usuario = listaUsuarios.getSelectionModel().getSelectedItem().getUser();

				Stage stage = (Stage) login.getScene().getWindow();
				stage.close();
				primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText(mensaje);
			alert.showAndWait();
		}
	}
	
	public void crearUsuario() {
		primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("registro.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Registrar");

			primaryStage.setOnCloseRequest(confirmCloseEventHandler);

			Stage stage = (Stage) login.getScene().getWindow();
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
