package Graphics.Gui.GraphicalComponents;

import Graphics.Gui.Rect;
import Graphics.Screen;
import Logic.Player;
import Logic.WorldState;

public class UpdatingTextComponent extends UIComponent{

	public static final int PLAYERLIST = 0;
	public static final int PING = 1;
	public static final int CHAT = 2;
	public static final int PLAYERATTRIBUTES = 3;

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
		StringBuilder players = new StringBuilder();

		WorldState worldState = screen.getWorldState();
		if (worldState != null) {

			switch (whatToUpdate) {
				case PLAYERLIST:
					for (Player player : worldState.getPlayers()) {
						players.append(player.getName());
						players.append(" : ");
						players.append(player.getPing());
						players.append("\n");
					}
					break;

				case CHAT:
					for (String msg : worldState.getChatMessages()) {
						players.append(msg);
						players.append("\n");
					}
					break;

			}
		}

		text = players.toString();
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
