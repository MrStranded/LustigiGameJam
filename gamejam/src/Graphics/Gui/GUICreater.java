package Graphics.Gui;

import Graphics.Screen;
import Graphics.Gui.Actions.*;

import java.awt.*;

public class GUICreater {

	private static int width, height;

	public static GUI createHostMenu(Screen screen) {
		width = screen.getWidth();
		height = screen.getHeight();

		GUI gui = new GUI();

		UIComponent mainBox = createUIComponent(0.1,0.1,0.8,0.8);
		gui.addUIComponent(mainBox);
		mainBox.setColor(new Color(200,200,200));

		String ipAddress = Network.Utility.getOwnIp();
		mainBox.addUIComponent(createButton("Your IP Address: " + ipAddress, new CopyContentAction(ipAddress),0.2, 0.125, 0.6, 0.05));

		TextComponent text = createText("A1 \n yolo \n\nlel\n",0.2, 0.2, 0.6, 0.4);
		mainBox.addUIComponent(text);
		text.setColor(new Color(190,190,190));

		mainBox.addUIComponent(createButton("Close", new CloseAction(),0.2, 0.6, 0.6, 0.1));

		return gui;
	}

	public static GUI createMainMenu(Screen screen) {
		width = screen.getWidth();
		height = screen.getHeight();

		GUI gui = new GUI();

		UIComponent mainBox = createUIComponent(0.1,0.1,0.8,0.8);
		gui.addUIComponent(mainBox);
		mainBox.setColor(new Color(200,200,200));

		mainBox.addUIComponent(createButton("Host", new HostAction(),0.2, 0.2, 0.6, 0.1));
		mainBox.addUIComponent(createButton("Join", new JoinAction(),0.2, 0.4, 0.6, 0.1));
		mainBox.addUIComponent(createButton("Close", new CloseAction(),0.2, 0.6, 0.6, 0.1));

		return gui;
	}

	private static UIComponent createUIComponent(double x, double y, double w, double h) {
		return new UIComponent(new Rect((int) (x*width), (int) (y*height), (int) (w*width), (int) (h*height)));
	}

	private static ButtonComponent createButton(String text, Action action, double x, double y, double w, double h) {
		return new ButtonComponent(text, action, new Rect((int) (x*width), (int) (y*height), (int) (w*width), (int) (h*height)));
	}

	private static TextComponent createText(String text, double x, double y, double w, double h) {
		return new TextComponent(text, new Rect((int) (x*width), (int) (y*height), (int) (w*width), (int) (h*height)));
	}

}
