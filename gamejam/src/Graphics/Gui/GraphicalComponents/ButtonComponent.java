package Graphics.Gui.GraphicalComponents;

import Graphics.Gui.Actions.Action;
import Graphics.Gui.Rect;

import java.awt.*;

public class ButtonComponent extends UIComponent{

	private String text;
	private Action action;
	private Color secondaryColor = new Color(150,150,150);

	public ButtonComponent(String text, Action action, Rect position) {
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

	public Color getSecondaryColor() {
		return secondaryColor;
	}

	public void setSecondaryColor(Color secondaryColor) {
		this.secondaryColor = secondaryColor;
	}
}
