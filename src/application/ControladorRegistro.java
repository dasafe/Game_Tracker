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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ControladorRegistro implements Initializable {

	private Stage primaryStage;

	private static boolean existe;

	@FXML
	private TextField user, password1, password2;

	@FXML
	private Button registrar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void registrarUsuario() throws IOException {
		existe = false;
		/*if (password1.getText().equals(password2.getText())) {
			for (String usuario : cargarUsuarios()) {
				JSONObject obj = new JSONObject(usuario);
				String nombreUsuario = obj.getString("Username");
				if (nombreUsuario.equals(user.getText())) {
					existe = true;
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setHeaderText(null);
					alert.setContentText("El usuario ya existe");
					alert.showAndWait();
				}
			}*/
			if (!existe) {
				String query_url = "http://0.0.0.0:5000/users/signup";
				String json = "{\"name\" : \"" + user.getText() + "\", \"password\": \"" + password1.getText() + "\" }";

				URL url = new URL(query_url);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
				conn.setDoOutput(true);
				conn.setDoInput(false);
				conn.setRequestMethod("POST");

				OutputStream os = conn.getOutputStream();
				os.write(json.getBytes("UTF-8"));
				os.close();
			}
		//}
	}

	public ArrayList<String> cargarUsuarios() throws IOException {
		String query_url = "https://game-tracker-api.herokuapp.com/users/list";

		URL url = new URL(query_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("GET");

		InputStream in = new BufferedInputStream(conn.getInputStream());
		String resultUnformatted = IOUtils.toString(in, "UTF-8");
		String result = resultUnformatted.substring(1, resultUnformatted.length() - 1);
		ArrayList<String> userList = new ArrayList<>(Arrays.asList(result.split(",")));
		return userList;
	}
}
