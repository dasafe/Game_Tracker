package application;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ControladorRegistro implements Initializable {

	private Stage primaryStage;

	@FXML
	private TextField user, password1, password2;

	@FXML
	private Button registrar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void registrarUsuario() {
		if (password1.getText().equals(password2.getText())) {
			URL url = null;
			HttpURLConnection conn = null;
			InputStream in = null;
			String result = "";
			JSONObject obj = null;
			String mensaje = "";
			try {
				String query_url = "https://game-tracker-api.herokuapp.com/users/signup";
				String json = "{\"name\" : \"" + user.getText() + "\", \"password\": \"" + password1.getText() + "\" }";

				url = new URL(query_url);
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				conn.setDoOutput(true);
				conn.setDoInput(true);
				conn.setRequestMethod("POST");

				OutputStream os = conn.getOutputStream();
				os.write(json.getBytes("UTF-8"));
				os.close();

				in = new BufferedInputStream(conn.getInputStream());
				result = IOUtils.toString(in, "UTF-8");
				obj = new JSONObject(result);
				mensaje = obj.getString("mensaje");
				showAlert(mensaje);
				primaryStage = new Stage();
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
					Parent root = (Parent) loader.load();
					Scene scene = new Scene(root);
					scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene);
					Stage stage = (Stage) registrar.getScene().getWindow();
					stage.close();
					primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				try {
					in = new BufferedInputStream(conn.getErrorStream());
					result = IOUtils.toString(in, "UTF-8");
					obj = new JSONObject(result);
					mensaje = obj.getString("mensaje");
					showAlert(mensaje);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void showAlert(String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}
}
