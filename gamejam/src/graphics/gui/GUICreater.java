package graphics.gui;

import graphics.Screen;
import graphics.Window;
import graphics.gui.actions.*;

public class GUICreater {

	private static int width, height;

	public static GUI createMainMenu(Window window) {
		width = window.getWidth();
		height = window.getHeight();

		GUI gui = new GUI();

		Box box = createBox(0.1,0.1,0.8,0.8);
		gui.addBox(box);

		box.addButton(createButton("Host", new HostAction(),0.2, 0.2, 0.6, 0.1));
		box.addButton(createButton("Join", new JoinAction(),0.2, 0.4, 0.6, 0.1));
		box.addButton(createButton("Close", new CloseAction(),0.2, 0.6, 0.6, 0.1));

		return gui;
	}

	private static Box createBox(double x, double y, double w, double h) {
		return new Box(new Rect((int) (x*width), (int) (y*height), (int) (w*width), (int) (h*height)));
	}

	private static Button createButton(String text, Action action, double x, double y, double w, double h) {
		return new Button(text, new Rect((int) (x*width), (int) (y*height), (int) (w*width), (int) (h*height)), action);
	}

}
