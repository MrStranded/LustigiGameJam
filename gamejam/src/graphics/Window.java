package graphics;

import Logic.WorldState;

import javax.swing.*;

public class Window {

	private int width, height;
	private Screen screen;
	private JFrame frame;

	public Window () {
		width = 800;
		height = 600;

		initialize();
	}

	private void initialize() {
		frame = new JFrame("LustigiGameJam");
		frame.add(screen = new Screen(width, height));
		frame.setLayout(null);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void drawWorld(WorldState worldState) {
		screen.draw(worldState);
		screen.repaint();
	}

}
