package graphics;

import Logic.WorldState;
import graphics.gui.GUI;
import input.Keyboard;
import input.Mouse;

import javax.swing.*;
import java.awt.event.WindowEvent;

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
		frame.add(screen = new Screen(width, height, this));
		frame.setLayout(null);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		frame.addKeyListener(new Keyboard());
		frame.addMouseListener(new Mouse());
	}

	public void close() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public void drawWorld(WorldState worldState, GUI gui) {
		screen.draw(worldState, gui);
		screen.repaint();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
