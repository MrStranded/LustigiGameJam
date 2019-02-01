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
			System.out.println("load " + "res/"+path);
			BufferedImage original = ImageIO.read(new File("res/"+path));

			double w = original.getWidth();
			double h = original.getHeight();

			double da = Math.PI*2d/Images.DIRECTIONS;

			for (int i=0; i<Images.DIRECTIONS; i++) {
				int s = (int) (Math.max(w,h) * f);

				images[i] = new BufferedImage(s, s, BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = images[i].createGraphics();

				AffineTransform at = new AffineTransform();
				at.translate(s/2d, s/2d);
				at.rotate(-i*da);
				at.translate(-w/2d,-h/2d);
				g.drawImage(original, at, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getImage(double angle) {
		int i = (int) ((double) (Images.DIRECTIONS) * angle / (Math.PI*2d)) % Images.DIRECTIONS;
		i = Math.min(Math.max(i,0),Images.DIRECTIONS-1);

		return images[i];
	}

	public BufferedImage getSpecific(int i) {
		return images[i];
	}

}
