package application;

import java.awt.Desktop;
import java.awt.MenuItem;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.google.gson.Gson;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ControladorTabla implements Initializable {
	
	private Stage primaryStage;

	private ObservableList<Juego> list = FXCollections.observableArrayList();

	private ArrayList<Juego> listaJuegos = new ArrayList<Juego>();

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

	public String usuario;
	
	public static String mensaje, oldName;

	@FXML
	private TableView<Juego> tabla;

	@FXML
	private TableColumn<Juego, String> fechaInicio, fechaFin;

	@FXML
	private TableColumn<Juego, String> nombre, estado, comentario;

	@FXML
	private TableColumn<Juego, Integer> nota;

	@FXML
	private DatePicker fi;

	@FXML
	private DatePicker ff;

	@FXML
	private TextField name, newComentario;

	@FXML
	private ChoiceBox<Integer> note;
	
	@FXML
	private ChoiceBox<String> newEstado;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		fechaInicio.setCellValueFactory(new PropertyValueFactory<Juego, String>("StartDate"));

		fechaFin.setCellValueFactory(new PropertyValueFactory<Juego, String>("FinalDate"));

		nombre.setCellValueFactory(new PropertyValueFactory<Juego, String>("Name"));
		nombre.setCellFactory(TextFieldTableCell.forTableColumn());

		nota.setCellValueFactory(new PropertyValueFactory<Juego, Integer>("Score"));
		nota.setCellFactory(ChoiceBoxTableCell.forTableColumn(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0));
		
		estado.setCellValueFactory(new PropertyValueFactory<Juego, String>("GameStatus"));
		estado.setCellFactory(ChoiceBoxTableCell.forTableColumn("Terminado", "En curso", "Pendiente"));
		
		comentario.setCellValueFactory(new PropertyValueFactory<Juego, String>("Comentario"));
		comentario.setCellFactory(TextFieldTableCell.forTableColumn());

		tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		note.getItems().addAll(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
		newEstado.getItems().addAll("Terminado", "En curso", "Pendiente");

		tabla.setItems(list);
		try {
			cargarJuegos();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addJuegos(ArrayList<Juego> lista) {
		// TODO Auto-generated method stub
		list.clear();
		for (Juego juego : lista) {
			list.add(juego);
		}
	}

	public void borrarJuego() throws IOException {
		for (int i = 0; i < tabla.getSelectionModel().getSelectedItems().size(); i++) {
			borrarJuegoenBBDD(tabla.getSelectionModel().getSelectedItems().get(i).getName());
		} 
		//showAlert(mensaje);
		tabla.getItems().removeAll(tabla.getSelectionModel().getSelectedItems());
		renovarArray();
	}

	public void guardarJuego() throws IOException {
		renovarArray();
		listaJuegos.add(
				new Juego(formatter.format(fi.getValue()).toString(), formatter.format(ff.getValue()).toString(), name.getText(), note.getSelectionModel().getSelectedItem(), newEstado.getSelectionModel().getSelectedItem(), newComentario.getText()));
		addJuegos(listaJuegos);
		subirJuegoABBDD();
	}

	private void renovarArray() {
		// TODO Auto-generated method stub
		listaJuegos.clear();
		for (int i = 0; i < list.size(); i++) {
			listaJuegos.add(list.get(i));
		}
	}

	public void start() {
		guardarUsuario();
		Stage primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			Stage stage = (Stage) tabla.getScene().getWindow();
			stage.close();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void guardarUsuario() {
		// TODO Auto-generated method stub
		for (int i = 0; i < Main.usuarios.size(); i++) {
			if (Main.usuarios.get(i).getUser().equalsIgnoreCase(usuario)) {
				Main.usuarios.get(i).setJuegos(listaJuegos);
			}
		}
	}

	public void cargarUsuario(ArrayList<Juego> lista) {
		listaJuegos.clear();
		listaJuegos = lista;
		addJuegos(lista);
	}

	@FXML
	public void editarNombre(TableColumn.CellEditEvent<Juego, String> evento) throws IOException {
		Juego aux = tabla.getSelectionModel().getSelectedItem();
		oldName = aux.getName();
		for (Juego juego : list) {
			if (juego.getName().equalsIgnoreCase(aux.getName())) {
				juego.setName(evento.getNewValue());
			}
		}
		actualizarJuegoenBBDD();
		//showAlert(mensaje);
	}

	@FXML
	public void editarNota(TableColumn.CellEditEvent<Juego, Integer> evento) throws IOException {
		Juego aux = tabla.getSelectionModel().getSelectedItem();
		oldName = aux.getName();
		for (Juego juego : list) {
			if (juego.getName().equalsIgnoreCase(aux.getName())) {
				juego.setScore(evento.getNewValue());
			}
		}
		actualizarJuegoenBBDD();
		//showAlert(mensaje);
	}
	
	@FXML
	public void editarEstado(TableColumn.CellEditEvent<Juego, String> evento) throws IOException {
		Juego aux = tabla.getSelectionModel().getSelectedItem();
		oldName = aux.getName();
		for (Juego juego : list) {
			if (juego.getName().equalsIgnoreCase(aux.getName())) {
				juego.setGameStatus(evento.getNewValue());
			}
		}
		actualizarJuegoenBBDD();
		//showAlert(mensaje);
	}
	
	@FXML
	public void editarComentario(TableColumn.CellEditEvent<Juego, String> evento) throws IOException {
		Juego aux = tabla.getSelectionModel().getSelectedItem();
		oldName = aux.getName();
		for (Juego juego : list) {
			if (juego.getName().equalsIgnoreCase(aux.getName())) {
				juego.setComentario(evento.getNewValue());
			}
		}
		actualizarJuegoenBBDD();
		//showAlert(mensaje);
	}

	public void mostrarDocumento() throws IOException {
		primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("logros.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			Stage stage = (Stage) tabla.getScene().getWindow();
			primaryStage.setOnCloseRequest(confirmCloseEventHandler);
			stage.close();
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cargarJuegos() throws IOException {
		String query_url = "https://game-tracker-api.herokuapp.com/users/myProfile";
		String json = "{\"name\" : \"" + ControladorLogin.usuarioActivo + "\" }";
		
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
		String resultUnformatted = IOUtils.toString(in, "UTF-8");
		String result = resultUnformatted.substring(1, resultUnformatted.length() - 1);
		Gson g = new Gson();
		Usuario usuario = g.fromJson(result, Usuario.class);
		System.out.println(usuario);
		addJuegos(usuario.getJuegos());
	}
	
	public void subirJuegoABBDD() throws IOException {
		String query_url = "https://game-tracker-api.herokuapp.com/users/addGames";
		String json = "{\"name\" : \"" + ControladorLogin.usuarioActivo + "\", \"game\" : { \"Name\" : \"" + name.getText() + "\", \"GameStatus\" : \"" + newEstado.getSelectionModel().getSelectedItem() + "\", \"StartDate\" : \"" + formatter.format(fi.getValue()).toString() + "\", \"FinalDate\" : \"" + formatter.format(ff.getValue()).toString() + "\", \"Score\" : \"" + note.getSelectionModel().getSelectedItem() + "\", \"Comentario\" : \"" + newComentario.getText() + "\" } }";  
		
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
		System.out.println(result);
	}
	
	public void borrarJuegoenBBDD(String gamename) throws IOException {
		String query_url = "https://game-tracker-api.herokuapp.com/users/deleteGames";
		String json = "{\"name\" : \"" + ControladorLogin.usuarioActivo + "\", \"gamename\" : \"" + gamename + "\"}";  
		
		URL url = new URL(query_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("DELETE");
		
		OutputStream os = conn.getOutputStream();
		os.write(json.getBytes("UTF-8"));
		os.close();

		InputStream in = new BufferedInputStream(conn.getInputStream());
		String result = IOUtils.toString(in, "UTF-8");
		JSONObject obj = new JSONObject(result);
		mensaje = obj.getString("mensaje");
	}
	
	public void actualizarJuegoenBBDD() throws IOException {
		String query_url = "https://game-tracker-api.herokuapp.com/users/updateGames";
		String json = "{\"name\" : \"" + ControladorLogin.usuarioActivo + "\", \"oldName\" : \"" + oldName + "\", \"game\" : { \"Name\" : \"" + tabla.getSelectionModel().getSelectedItems().get(0).getName() + "\", \"GameStatus\" : \"" + tabla.getSelectionModel().getSelectedItems().get(0).getGameStatus() + "\", \"StartDate\" : \"" + tabla.getSelectionModel().getSelectedItems().get(0).getStartDate() + "\", \"FinalDate\" : \"" + tabla.getSelectionModel().getSelectedItems().get(0).getFinalDate() + "\", \"Score\" : \"" + tabla.getSelectionModel().getSelectedItems().get(0).getScore() + "\", \"Comentario\" : \"" + tabla.getSelectionModel().getSelectedItems().get(0).getComentario() + "\" } }";  
		
		URL url = new URL(query_url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("PUT");
		
		OutputStream os = conn.getOutputStream();
		os.write(json.getBytes("UTF-8"));
		os.close();

		InputStream in = new BufferedInputStream(conn.getInputStream());
		String result = IOUtils.toString(in, "UTF-8");
		JSONObject obj = new JSONObject(result);
		mensaje = obj.getString("mensaje");
	}
	
	public void showAlert(String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
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
