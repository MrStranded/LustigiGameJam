package graphics;

import java.awt.*;

public class Screen extends Canvas {

	private int width, height;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;

		initialize();
	}

	private void initialize() {
		setBackground(Color.BLUE);
		setSize(width, height);
	}

	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int) (Math.random()*width), (int) (Math.random()*height), 10, 10);
	}
}
