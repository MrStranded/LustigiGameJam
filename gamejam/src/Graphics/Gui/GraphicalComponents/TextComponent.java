package Graphics.Gui.GraphicalComponents;

import Graphics.Gui.Rect;

public class TextComponent extends UIComponent{

	private String text;

	public TextComponent(String text, Rect position) {
		super(position);
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

}
