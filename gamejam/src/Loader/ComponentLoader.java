package Loader;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class ComponentLoader {

	public static void loadComponents() {
		parseFile("ships.txt");
	}

	public static void parseFile(String path) {

		System.out.println("load " + "res/dat/" + path);

		try {

			BufferedReader bufferedReader = new BufferedReader(new FileReader("res/dat/" + path));

			String line;
			Component component = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (!line.startsWith("#")) {

					int sep = line.indexOf("=");

					if (sep >= 0) {
						if (component != null) {

						}
					} else {

					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
