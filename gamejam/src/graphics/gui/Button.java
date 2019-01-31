package graphics.gui;

import graphics.gui.actions.Action;

import java.awt.event.MouseEvent;

public class Button {

	private Rect position = new Rect(0,0,0,0);
	private String text;
	private Action action;

	public Button(String text, Rect position, Action action) {
		this.text = text;
		this.position = position;
		this.action = action;
	}

	public Rect getPosition() {
		return position;
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

	public boolean inside(int[] mousePosition) {
		return position.inside(mousePosition[0], mousePosition[1]);
	}
}
