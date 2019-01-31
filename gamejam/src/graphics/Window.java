package graphics;

import javax.swing.*;
import java.awt.*;

public class Window {

	private int width, height;
	private Canvas canvas;
	private JFrame frame;

	public Window () {
		width = 800;
		height = 600;

		initialize();
	}

	private void initialize() {
		frame = new JFrame("LustigiGameJam");
		frame.add(canvas = new Screen(width, height));
		frame.setLayout(null);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
