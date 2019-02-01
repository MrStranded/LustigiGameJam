package Globals;

import Graphics.Image;

import java.awt.image.BufferedImage;

public class Images {

	public static final int DIRECTIONS = 8;
	public static final int COMPONENTS = 1;

	public static Image[] components = new Image[COMPONENTS];

	public static void setComponentImage(int id, Image image) {
		components[id] = image;
	}

	public static BufferedImage getComponentImage(int imageId, double angle) {
		if (imageId >= 0 && imageId < COMPONENTS) {
			return components[imageId].getImage(angle);
		}
		return null;
	}

	public static Image getSpecific(int i) {
		return components[i];
	}

}
