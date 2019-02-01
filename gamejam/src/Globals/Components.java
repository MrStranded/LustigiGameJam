package Globals;

import Logic.Attributes;
import Logic.Component;

import java.util.ArrayList;
import java.util.List;

public class Components {

	private static List<Component> componentsList = new ArrayList<>(10);

	public static Component copyOriginal(int typeId) {
		for (Component component : componentsList) {
			if ((int) component.getAttribute(Attributes.CATEGORY) == typeId) {
				return new Component(component);
			}
		}
		return null;
	}

	public static void addComponent(Component component) {
		componentsList.add(component);
	}

}
