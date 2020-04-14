package application;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ControladorLogros implements Initializable {

	private Stage primaryStage;

	public static String appId;

	@FXML
	private Button volver;

	@FXML
	private VBox box;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String img;
		box.setSpacing(10);
		File file = new File(toast.WatchDir.ruta);
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});
//		System.out.println(Arrays.toString(directories));
		img = "";
		for (int i = 0; i < directories.length; i++) {
			ImageView imgBox = new ImageView();
			img = "https://steamcdn-a.akamaihd.net/steam/apps/" + directories[i] + "/header_292x136.jpg";
			imgBox.setImage(new Image(img));
			imgBox.setAccessibleText(directories[i]);
			imgBox.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
				appId = imgBox.getAccessibleText();
				logros();
				event.consume();
			});
			box.getChildren().addAll(imgBox);
		}
	}

	private void logros() {
		// TODO Auto-generated method stub
		primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("tablaLogros.fxml"));
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

	public void volverTabla() throws IOException {
		primaryStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("tabla.fxml"));
			Parent root = (Parent) loader.load();
			Scene scene = new Scene(root, 1280, 720);
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
}
