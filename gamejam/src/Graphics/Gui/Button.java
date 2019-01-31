package Graphics.Gui;

import Graphics.Gui.Actions.Action;

public class Button extends UIComponent{

	private String text;
	private Action action;

	public Button(String text, Action action, Rect position) {
		super(position);
		this.text = text;
		this.action = action;
	}

	public String getText() {
		return text;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
}
