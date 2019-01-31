package graphics.gui;

import Logic.WorldState;

import java.awt.event.MouseEvent;

public abstract class Button {

	private Rect position = new Rect(0,0,0,0);
	private String text;

	public Button(String text, Rect position) {
		this.text = text;
		this.position = position;
	}

	public Rect getPosition() {
		return position;
	}

	public String getText() {
		return text;
	}

	public boolean inside(MouseEvent e) {
		return position.inside(e.getX(), e.getY());
	}

	public abstract void trigger();
}
