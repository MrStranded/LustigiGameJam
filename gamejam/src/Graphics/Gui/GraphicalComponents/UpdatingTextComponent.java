package Graphics.Gui.GraphicalComponents;

import Graphics.Gui.Rect;

public class UpdatingTextComponent extends UIComponent{

	public static int PLAYERLIST = 0;
	public static int PING = 1;
	public static int CHAT = 2;
	public static int PLAYERATTRIBUTES = 3;

	private String text;
	private int whatToUpdate = 0;

	private SmallUpdateTimer timer;

	public UpdatingTextComponent(Rect position) {
		super(position);
		this.text = "";

		timer = new SmallUpdateTimer(this);
	}

	public void kill() {
		timer.stopRunning();
	}

	public void update() {

	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
