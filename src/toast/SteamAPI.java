package toast;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SteamAPI {

	private static String key = "B6C926577DE2BBAFC6BA06B8EEE44CC3";

	public static void listaLogros(String appId, String logroId)
			throws SAXException, IOException, ParserConfigurationException {

		URL url = new URL("https://api.steampowered.com/ISteamUserStats/GetSchemaForGame/v0002/?key=" + key + "&appid="
				+ appId + "&l=spanish&format=xml");

		String img = "https://steamcdn-a.akamaihd.net/steam/apps/" + appId + "/header.jpg";

		String apiLogro;
		String titulo;
		String descripcion;
		String icono;
		String juego;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(url.openStream());

		NodeList nList = doc.getElementsByTagName("achievement");

		NodeList gameList = doc.getElementsByTagName("game");
		Node gameNode = gameList.item(0);
		Element gameElement = (Element) gameNode;
		juego = gameElement.getElementsByTagName("gameName").item(0).getTextContent();

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

					NodeJS.toast(titulo, descripcion, icono, juego, img);
					break;
				}

			}
		}

	}

}
