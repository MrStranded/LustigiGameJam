package Graphics.Gui.GraphicalComponents;

import Graphics.Gui.Rect;
import Graphics.Screen;
import Logic.Player;
import Logic.WorldState;

import java.util.HashMap;

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

			switch (whatToUpdate) {
				case GAMELIST:

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
