package Graphics.Gui.GraphicalComponents;

import Graphics.Gui.Actions.IpFromInputAction;
import Graphics.Gui.Actions.IpSetAction;
import Graphics.Gui.Rect;
import Graphics.Screen;
import Logic.WorldState;

import java.util.HashMap;
import java.util.Set;

public class UpdatingButtonsComponent extends UIComponent implements UpdatingComponent {

	public static final int GAMELIST = 0;
	public static final int COMPONENTLIST = 1;

	private String text;
	private int whatToUpdate = 0;
	private Screen screen;

	private SmallUpdateTimer timer;

	public UpdatingButtonsComponent(int whatToUpdate, Screen screen, Rect position) {
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
		WorldState worldState = screen.getWorldState();
		if (worldState != null) {

			int xPos = getPosition().getX() + 10;
			int yPos = getPosition().getY() + 10;
			int w = getPosition().getW() - 20;
			int h = getPosition().getH() - 20;

			switch (whatToUpdate) {
				case GAMELIST:
					HashMap<String, String> ipNameTuples = worldState.getIpNameTuples();
					if (ipNameTuples != null) {
						Set<String> gameIps = ipNameTuples.keySet();
						int n = gameIps.size();
						clearComponents();

						int dh = h / n;
						int i = 0;
						for (String ip : gameIps) {
							ButtonComponent button = new ButtonComponent(ip + " - " + ipNameTuples.get(ip), new IpSetAction(ip), new Rect(xPos, yPos + i * dh, w, dh));
							addUIComponent(button);
						}
					}

					break;

				case COMPONENTLIST:
					break;
			}
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
