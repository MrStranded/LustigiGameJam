package Loader;

import Globals.Components;
import Logic.Attributes;
import Logic.Categories;
import Logic.Component;

import javax.management.Attribute;
import java.io.BufferedReader;
import java.io.FileReader;

public class ComponentLoader {

	public static void loadComponents() {
		parseFile("ships.txt");
		parseFile("canons.txt");
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

							String att = line.substring(0,sep).trim();
							String val = line.substring(sep+1).trim();

							Attributes a = getAttributeId(att);

							if (a != null) {
								component.set(a, Double.parseDouble(val));
								//System.out.println("["+att+"|"+val+"]");
							}

						}
					} else {
						int cat = getCategoryId(line.trim());

						if (cat >= 0) {
							if (component != null) {
								Components.addComponent(component);
							}

							component = new Component();
							component.set(Attributes.CATEGORY, cat);
						}
					}
				}
			}

			if (component != null) {
				Components.addComponent(component);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static int getCategoryId(String categoryName) {
		for (Categories categories : Categories.values()) {
			if (categories.name().equals(categoryName)) {
				return categories.valueOf();
			}
		}
		return -1;
	}

	private static Attributes getAttributeId(String attributeName) {
		for (Attributes attribute : Attributes.values()) {
			if (attribute.name().equals(attributeName)) {
				return attribute;
			}
		}
		return null;
	}

}
