package Graphics;

import Logic.WorldState;
import Graphics.Gui.GUI;
import Input.InputBuffer;
import Input.Keyboard;
import Input.Mouse;

import javax.swing.*;
import java.awt.*;
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);

		Insets insets = frame.getInsets();
		InputBuffer.setMouseDisplacement(insets.left, insets.top);
		//frame.setSize(width, height + insets.top);

		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int) (fullScreen.getWidth() - width) / 2, (int) (fullScreen.getHeight() - height) / 2);

		frame.addKeyListener(new Keyboard());
		Mouse mouse = new Mouse();
		frame.addMouseListener(mouse);
		frame.addMouseMotionListener(mouse);
	}

	public void close() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	public void registerInput(WorldState worldState) {
		screen.registerInput(worldState);
	}

	public void drawWorld(WorldState worldState) {
		screen.updateWorldState(worldState);
		screen.repaint();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Screen getScreen() { return screen; }
}
