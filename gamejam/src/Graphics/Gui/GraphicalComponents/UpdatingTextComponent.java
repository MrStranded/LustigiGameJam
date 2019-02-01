package Graphics.Gui.GraphicalComponents;

import Graphics.Gui.Rect;
import Graphics.Screen;
import Logic.Player;
import Logic.WorldState;

import java.util.HashMap;

public class UpdatingTextComponent extends UIComponent implements UpdatingComponent {

	public static final int PLAYERLIST = 0;
	public static final int PING = 1;
	public static final int CHAT = 2;
	public static final int PLAYERATTRIBUTES = 3;
	public static final int GAMELIST = 4;
	public static final int IP = 5;

	private String text;
	private int whatToUpdate = 0;
	private Screen screen;

	private SmallUpdateTimer timer;

	public UpdatingTextComponent(int whatToUpdate, Screen screen, Rect position) {
		super(position);
		this.text = "";
		this.screen = screen;

		this.whatToUpdate = whatToUpdate;

		timer = new SmallUpdateTimer(this);
	}

	public void kill() {
		timer.stopRunning();
	}

	public void update() {
		StringBuilder textField = new StringBuilder();

		WorldState worldState = screen.getWorldState();
		if (worldState != null) {

			switch (whatToUpdate) {
				case PLAYERLIST:
					for (Player player : worldState.getPlayers()) {
						textField.append(player.getName());
						textField.append(" : ");
						textField.append(player.getPing());
						textField.append("\n");
					}
					break;

				case CHAT:
					for (String msg : worldState.getChatMessages()) {
						textField.append(msg);
						textField.append("\n");
					}
					break;

				case GAMELIST:
					HashMap<String, String> ipNameTuples = worldState.getIpNameTuples();
					if (ipNameTuples != null) {
						for (String ip : ipNameTuples.keySet()) {
							textField.append(ip);
							textField.append(" - ");
							textField.append(ipNameTuples.get(ip));
							textField.append("\n");
						}
					}
					break;

				case IP:
					textField.append("Connect to ");
					textField.append(worldState.ip);
					break;
			}
		}

		text = textField.toString();
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
