package Graphics;

import Globals.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

	private BufferedImage[] images = new BufferedImage[Images.DIRECTIONS];

	public Image(String path) {
		try {
			images[0] = ImageIO.read(new File("res/"+path));

			double da = Math.PI*2d/Images.DIRECTIONS;

			Graphics2D g = images[0].createGraphics();

			for (int i=1; i<Images.DIRECTIONS; i++) {
				//images[i] = g.rotate();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
