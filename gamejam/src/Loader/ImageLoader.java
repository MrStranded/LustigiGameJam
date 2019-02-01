package Loader;

import Globals.Images;
import Graphics.Image;

public class ImageLoader {

	public static void loadImages() {
		Images.setComponentImage(0, new Image("ship01.png"));
		Images.setComponentImage(1, new Image("ship02.png"));
		Images.setComponentImage(2, new Image("ship03.png"));
		Images.setComponentImage(3, new Image("canon01.png"));

		Images.setComponentImage(4, new Image("slot.png"));
	}

}
