package Translater;

import Logic.Component;
import Logic.WorldState;

public class Encoder {

	public static String createMapMsg(WorldState worldState) {
		StringBuilder stringBuilder = new StringBuilder(KeyWord.MAP.name());

		if (worldState != null) {
			stringBuilder.append(Separator.KEYWORD);

			int s = worldState.getSize();
			stringBuilder.append(s);
			for (int x=0; x<s; x++) {
				for (int y=0; y<s; y++) {
					stringBuilder.append(Separator.VALUE);
					stringBuilder.append(String.valueOf(worldState.getMap()[x][y]));
				}
			}
		}

		return stringBuilder.toString();
	}

	public static String createFullComponentMsg(Component component) {
		if (component != null) {
			StringBuilder stringBuilder = new StringBuilder(createComponentMsg(component));

			stringBuilder.append(Separator.INFO);
			stringBuilder.append(createAttributesMsg(component));

			stringBuilder.append(Separator.INFO);
			stringBuilder.append(createPositionMsg(component));

			for (Component sub : component.getSubComponents()) {
				stringBuilder.append(Separator.INFO);
				stringBuilder.append(createFullComponentMsg(sub));
			}

			return stringBuilder.toString();
		}
		return "";
	}

	public static String createComponentMsg(Component component) {
		StringBuilder stringBuilder = new StringBuilder(KeyWord.COMPONENT.name());

		if (component != null) {
			stringBuilder.append(Separator.KEYWORD);

			stringBuilder.append(component.getId());

			for (Component sub : component.getSubComponents()) {
				stringBuilder.append(Separator.VALUE);
				stringBuilder.append(sub.getId());
			}
		}

		return stringBuilder.toString();
	}

	public static String createAttributesMsg(Component component) {
		StringBuilder stringBuilder = new StringBuilder(KeyWord.ATTRIBUTES.name());

		if (component != null) {
			stringBuilder.append(Separator.KEYWORD);

			stringBuilder.append(component.getId());

			for (double d : component.getAttributes()) {
				stringBuilder.append(Separator.VALUE);
				stringBuilder.append(d);
			}
		}

		return stringBuilder.toString();
	}

	public static String createPositionMsg(Component component) {
		StringBuilder stringBuilder = new StringBuilder(KeyWord.POSITION.name());

		if (component != null) {
			stringBuilder.append(Separator.KEYWORD);

			stringBuilder.append(component.getId());

			stringBuilder.append(Separator.VALUE);
			stringBuilder.append(component.getPosition().getX());
			stringBuilder.append(Separator.VALUE);
			stringBuilder.append(component.getPosition().getY());
		}

		return stringBuilder.toString();
	}



}
