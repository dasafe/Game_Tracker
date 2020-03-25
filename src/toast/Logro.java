package toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Logro {

	public static void ultimoLogro(String ruta) throws FileNotFoundException {
		File file = new File(ruta);
		Scanner sc = new Scanner(file);

		String linea;
		String logroId = "";
		String logroAux = "";
		float tiempo = 0;

		while (sc.hasNextLine()) {
			linea = sc.nextLine();
			if (linea.startsWith("[") && linea.endsWith("]") && !linea.equalsIgnoreCase("[SteamAchievements]")) {
				logroAux = linea.substring(linea.indexOf("[") + 1, linea.indexOf("]"));
			}
			if (linea.contains("UnlockTime")) {
				if (Float.parseFloat(linea.replace(" ", "").replace("UnlockTime=", "")) - tiempo >= 0) {
					tiempo = Float.parseFloat(linea.replace(" ", "").replace("UnlockTime=", ""));
					logroId = logroAux;
				}
			}
		}
		String[] r = ruta.replace("\\", "__").split("__");

		String appId = "";
		for (int i = 0; i < r.length - 1; i++) {
			appId = r[i];
		}

		try {
			SteamAPI.listaLogros(appId, logroId);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
