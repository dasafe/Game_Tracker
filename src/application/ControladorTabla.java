package application;

import java.awt.MenuItem;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class ControladorTabla implements Initializable {

	private ObservableList<Juego> list = FXCollections.observableArrayList();

	private ArrayList<Juego> listaJuegos = new ArrayList<Juego>();

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

	public String usuario;

	@FXML
	private TableView<Juego> tabla;

	@FXML
	private TableColumn<Juego, LocalDate> fechaInicio, fechaFin;

	@FXML
	private TableColumn<Juego, String> nombre;

	@FXML
	private TableColumn<Juego, Integer> nota;

	@FXML
	private DatePicker fi;

	@FXML
	private DatePicker ff;

	@FXML
	private TextField name;

	@FXML
	private ChoiceBox<Integer> note;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		fechaInicio.setCellValueFactory(new PropertyValueFactory<Juego, LocalDate>("fecha_inicio"));
		fechaInicio.setCellFactory(tc -> new TableCell<Juego, LocalDate>() {
			@Override
			protected void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (empty) {
					setText(null);
				} else {
					setText(formatter.format(date));
				}
			}
		});

		fechaFin.setCellValueFactory(new PropertyValueFactory<Juego, LocalDate>("fecha_fin"));
		fechaFin.setCellFactory(tc -> new TableCell<Juego, LocalDate>() {
			@Override
			protected void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				if (empty) {
					setText(null);
				} else {
					setText(formatter.format(date));
				}
			}
		});
		;

		nombre.setCellValueFactory(new PropertyValueFactory<Juego, String>("nombre"));
		nombre.setCellFactory(TextFieldTableCell.forTableColumn());

		nota.setCellValueFactory(new PropertyValueFactory<Juego, Integer>("nota"));
		nota.setCellFactory(ChoiceBoxTableCell.forTableColumn(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0));

		tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		note.getItems().addAll(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

		tabla.setItems(list);
	}

	public void addJuegos(ArrayList<Juego> lista) {
		// TODO Auto-generated method stub
		list.clear();
		for (Juego juego : lista) {
			list.add(juego);
		}
	}

	public void borrarJuego() {
		tabla.getItems().removeAll(tabla.getSelectionModel().getSelectedItems());
		renovarArray();
	}

	public void guardarJuego() {
		renovarArray();
		listaJuegos.add(
				new Juego(fi.getValue(), ff.getValue(), name.getText(), note.getSelectionModel().getSelectedItem()));
		addJuegos(listaJuegos);
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("lista.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root, 245, 486);
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
	public void editarNombre(TableColumn.CellEditEvent<Juego, String> evento) {
		Juego aux = tabla.getSelectionModel().getSelectedItem();
		for (Juego juego : list) {
			if (juego.getNombre().equalsIgnoreCase(aux.getNombre())) {
				juego.setNombre(evento.getNewValue());
			}
		}
	}

	@FXML
	public void editarNota(TableColumn.CellEditEvent<Juego, Integer> evento) {
		Juego aux = tabla.getSelectionModel().getSelectedItem();
		for (Juego juego : list) {
			if (juego.getNombre().equalsIgnoreCase(aux.getNombre())) {
				juego.setNota(evento.getNewValue());
			}
		}
	}

}
