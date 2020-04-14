package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ControladorTablaLogros implements Initializable {

	private Stage primaryStage;

	private ObservableList<Logro> list = FXCollections.observableArrayList();

	private static ArrayList<Logro> listaLogros = new ArrayList<Logro>();

	@FXML
	private Button volver;

	@FXML
	private TableView<Logro> tabla;

	@FXML
	private TableColumn<Logro, String> img;

	@FXML
	private TableColumn<Logro, String> titulo, descripcion;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		img.setCellValueFactory(new PropertyValueFactory<Logro, String>("img"));

		titulo.setCellValueFactory(new PropertyValueFactory<Logro, String>("Titulo"));

		descripcion.setCellValueFactory(new PropertyValueFactory<Logro, String>("Descripcion"));

		try {
			cargarLogros();
		} catch (IOException | SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		tabla.setItems(list);
	}

	public void cargarLogros() throws IOException, SAXException, ParserConfigurationException {
		listaLogros.clear();
		leerLogros();
		addLogros();
	}

	public void addLogros() {
		// TODO Auto-generated method stub
		list.clear();
		for (Logro logro : listaLogros) {
			list.add(logro);
		}
	}

	public void volverTabla() throws IOException {
		primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("logros.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			primaryStage.setOnCloseRequest(confirmCloseEventHandler);

			Stage stage = (Stage) volver.getScene().getWindow();
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

	private void leerLogros() throws SAXException, IOException, ParserConfigurationException {
		// TODO Auto-generated method stub
		String ruta = toast.WatchDir.ruta + "\\" + ControladorLogros.appId + "\\achievements.ini";
		File file = new File(ruta);
		Scanner sc = new Scanner(file);

		String linea;
		String logroAux = "";

		while (sc.hasNextLine()) {
			linea = sc.nextLine();
			if (linea.startsWith("[") && linea.endsWith("]") && !linea.equalsIgnoreCase("[SteamAchievements]")) {
				logroAux = linea.substring(linea.indexOf("[") + 1, linea.indexOf("]"));
				listado(ControladorLogros.appId, logroAux);
			}
		}
	}

	public static void listado(String appId, String logroId)
			throws SAXException, IOException, ParserConfigurationException {
		String key = "B6C926577DE2BBAFC6BA06B8EEE44CC3";
		URL url = new URL("https://api.steampowered.com/ISteamUserStats/GetSchemaForGame/v0002/?key=" + key + "&appid="
				+ appId + "&l=spanish&format=xml");

		String apiLogro;
		String titulo;
		String descripcion;
		String icono;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(url.openStream());

		NodeList nList = doc.getElementsByTagName("achievement");

		NodeList gameList = doc.getElementsByTagName("game");
		Node gameNode = gameList.item(0);
		Element gameElement = (Element) gameNode;

		for (int temp = 0; temp < nList.getLength(); temp++) {

			Node nNode = nList.item(temp);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				NodeList nDesc = eElement.getElementsByTagName("description");
				apiLogro = eElement.getElementsByTagName("name").item(0).getTextContent();

				if (apiLogro.equalsIgnoreCase(logroId)) {
					titulo = eElement.getElementsByTagName("displayName").item(0).getTextContent();
					if (nDesc.getLength() > 0) {
						descripcion = eElement.getElementsByTagName("description").item(0).getTextContent();
					} else {
						descripcion = " ";
					}
					icono = eElement.getElementsByTagName("icon").item(0).getTextContent();
					ImageView imagen = new ImageView();
					imagen.setImage(new Image(icono));
					listaLogros.add(new Logro(imagen, titulo, descripcion));
					break;
				}

			}
		}

	}
}
