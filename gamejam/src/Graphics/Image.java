package Graphics;

import Globals.Images;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

	private BufferedImage[] images = new BufferedImage[Images.DIRECTIONS];
	private final double f = Math.sqrt(2d);

	public Image(String path) {
		try {
			BufferedImage original = ImageIO.read(new File("res/"+path));

			double w = images[0].getWidth();
			double h = images[0].getHeight();

			double da = Math.PI*2d/Images.DIRECTIONS;

			for (int i=1; i<Images.DIRECTIONS; i++) {
				int s = (int) (Math.max(w,h) * f);
				images[i] = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = images[i].createGraphics();

				AffineTransform at = new AffineTransform();
				at.rotate(i*da);
				g.drawImage(original, at, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage(double angle) {
		int i = (int) ((double) (Images.DIRECTIONS) * angle / (Math.PI*2d)) % Images.DIRECTIONS;
		i = Math.min(Math.max(i,0),Images.DIRECTIONS-1);

		return images[i];
	}

}
