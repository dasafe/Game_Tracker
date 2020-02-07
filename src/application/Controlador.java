package application;

import java.awt.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class Controlador implements Initializable {

	private ObservableList<Juego> list = FXCollections.observableArrayList();

	@FXML
	private TableView<Juego> tabla;

	@FXML
	private TableColumn<Juego, String> fechaInicio, fechaFin, nombre;

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
		
		
		//NO FUNCIONA CHOICEBOX
		
		fechaInicio.setCellValueFactory(new PropertyValueFactory<Juego, String>("fecha_inicio"));
		fechaInicio.setCellFactory(TextFieldTableCell.forTableColumn());
		
		fechaFin.setCellValueFactory(new PropertyValueFactory<Juego, String>("fecha_fin"));
		fechaFin.setCellFactory(TextFieldTableCell.forTableColumn());
		
		nombre.setCellValueFactory(new PropertyValueFactory<Juego, String>("nombre"));
		nombre.setCellFactory(TextFieldTableCell.forTableColumn());
		
		nota.setCellValueFactory(new PropertyValueFactory<Juego, Integer>("nota"));
		nota.setCellFactory(ChoiceBoxTableCell.forTableColumn(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0));

		tabla.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		note.getItems().addAll(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

		cargarDatosPrueba();

		tabla.setItems(list);
	}

	public void cargarDatosPrueba() {

		ArrayList<Juego> listaJuegos = new ArrayList<Juego>();
		listaJuegos.add(new Juego("07/02/20", "07/02/20", "Kingdom Hearts", 10));
		listaJuegos.add(new Juego("02/02/20", "03/02/20", "Loco Roco", 8));
		listaJuegos.add(new Juego("05/02/20", "07/02/20", "Patapon", 8));
		listaJuegos.add(new Juego("09/02/20", "22/02/20", "Crash Bandicoot", 5));

		for (int i = 0; i < listaJuegos.size(); i++) {
			añadirJuego(listaJuegos.get(i));
		}

	}

	public void añadirJuego(Juego juego) {
		// TODO Auto-generated method stub
		list.add(juego);
	}

	public void borrarJuego() {
		tabla.getItems().removeAll(tabla.getSelectionModel().getSelectedItems());
	}
	
	public void guardarJuego() {
		añadirJuego(new Juego(fi.getValue().toString(), ff.getValue().toString(), name.getText(), note.getSelectionModel().getSelectedItem()));
	}

}
