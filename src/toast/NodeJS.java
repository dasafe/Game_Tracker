package toast;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

public class NodeJS {

	static String ruta = System.getProperty("user.dir");

	public static void toast(String titulo, String descripcion, String icono, String juego, String img) {
		ProcessBuilder pb = new ProcessBuilder("node", ruta + "\\app.js", titulo, descripcion, icono, juego, img);
		try {
			Process p = pb.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error: Necesario instalar NodeJS y reiniciar.");
			e.printStackTrace();
		}
	}

}
