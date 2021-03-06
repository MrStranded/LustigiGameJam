package Graphics.Gui;

import Graphics.Gui.GraphicalComponents.*;
import Graphics.Gui.GraphicalComponents.TextComponent;
import Graphics.Screen;
import Graphics.Gui.Actions.*;

import java.awt.*;

public class GUICreater {

	private static int width, height;

	public static GUI createUserNameInputMenu(Screen screen) {
		width = screen.getWidth();
		height = screen.getHeight();

		GUI gui = new GUI();

		UIComponent mainBox = createUIComponent(0.1,0.45,0.8,0.1);
		gui.addUIComponent(mainBox);
		mainBox.setColor(new Color(200,200,200));

		mainBox.addUIComponent(createInput("Username: ", new UserNameAction(), 0.11, 0.46, 0.78, 0.08));

		return gui;
	}

	public static GUI createHostMenu(Screen screen) {
		width = screen.getWidth();
		height = screen.getHeight();

		GUI gui = new GUI();

		UIComponent mainBox = createUIComponent(0.1,0.1,0.8,0.8);
		gui.addUIComponent(mainBox);
		mainBox.setColor(new Color(200,200,200));

		String ipAddress = Network.Utility.getOwnIp();
		mainBox.addUIComponent(createButton("Your IP Address: " + ipAddress, new CopyContentAction(ipAddress),0.2, 0.125, 0.6, 0.05));

		mainBox.addUIComponent(createUpdatingText(UpdatingTextComponent.PLAYERLIST, screen, 0.2, 0.2, 0.6, 0.175));
		mainBox.addUIComponent(createUpdatingText(UpdatingTextComponent.CHAT, screen, 0.2, 0.4, 0.6, 0.175));

		mainBox.addUIComponent(createInput(screen.getWorldState().userName + ": ", new ChatAction(), 0.2, 0.6, 0.6, 0.05));

		mainBox.addUIComponent(createButton("Start", new StartGameAction(),0.2, 0.7, 0.6, 0.05));

		mainBox.addUIComponent(createButton("Close", new CloseAction(),0.2, 0.8, 0.6, 0.05));

		return gui;
	}

	public static GUI createPlayerMenu(Screen screen) {
		width = screen.getWidth();
		height = screen.getHeight();

		GUI gui = new GUI();

		UIComponent mainBox = createUIComponent(0.1,0.1,0.8,0.8);
		gui.addUIComponent(mainBox);
		mainBox.setColor(new Color(200,200,200));

		mainBox.addUIComponent(createUpdatingText(UpdatingTextComponent.PLAYERLIST, screen, 0.2, 0.2, 0.6, 0.175));
		mainBox.addUIComponent(createUpdatingText(UpdatingTextComponent.CHAT, screen, 0.2, 0.4, 0.6, 0.175));

		mainBox.addUIComponent(createInput(screen.getWorldState().userName + ": ", new ChatAction(), 0.2, 0.6, 0.6, 0.05));

		mainBox.addUIComponent(createButton("Close", new CloseAction(),0.2, 0.8, 0.6, 0.05));

		return gui;
	}

	public static GUI createLobbyMenu(Screen screen) {
		width = screen.getWidth();
		height = screen.getHeight();

		GUI gui = new GUI();

		UIComponent mainBox = createUIComponent(0.1,0.1,0.8,0.8);
		gui.addUIComponent(mainBox);
		mainBox.setColor(new Color(200,200,200));

		mainBox.addUIComponent(createButton("Scan", new ScanAction(),0.2, 0.2, 0.6, 0.05));

		mainBox.addUIComponent(createUpdatingButtons(UpdatingButtonsComponent.GAMELIST, screen, 0.2, 0.3, 0.6, 0.275));

		mainBox.addUIComponent(createInput("IP to connect to: ", new IpFromInputAction(), 0.2, 0.6, 0.275, 0.05));
		mainBox.addUIComponent(createUpdatingText(UpdatingTextComponent.IP, screen, 0.525, 0.6, 0.275, 0.05));

		mainBox.addUIComponent(createButton("Connect", new ConnectAction(),0.2, 0.7, 0.6, 0.05));
		mainBox.addUIComponent(createButton("Close", new CloseAction(),0.2, 0.8, 0.6, 0.05));

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
		mainBox.addUIComponent(createButton("Close", new CloseAction(),0.2, 0.8, 0.6, 0.05));

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

	private static UpdatingTextComponent createUpdatingText(int whatToUpdate, Screen screen, double x, double y, double w, double h) {
		return new UpdatingTextComponent(whatToUpdate, screen, new Rect((int) (x*width), (int) (y*height), (int) (w*width), (int) (h*height)));
	}

	private static UpdatingButtonsComponent createUpdatingButtons(int whatToUpdate, Screen screen, double x, double y, double w, double h) {
		return new UpdatingButtonsComponent(whatToUpdate, screen, new Rect((int) (x*width), (int) (y*height), (int) (w*width), (int) (h*height)));
	}

	private static InputComponent createInput(String text, Action action, double x, double y, double w, double h) {
		return new InputComponent(text, action, new Rect((int) (x*width), (int) (y*height), (int) (w*width), (int) (h*height)));
	}

}
