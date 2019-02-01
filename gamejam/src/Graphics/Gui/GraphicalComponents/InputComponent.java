package Graphics.Gui.GraphicalComponents;

import Graphics.Gui.Actions.Action;
import Graphics.Gui.Actions.ChatAction;
import Graphics.Gui.Actions.UserNameAction;
import Graphics.Gui.Rect;
import Input.InputBuffer;

import java.awt.*;
import java.awt.event.KeyEvent;

public class InputComponent extends UIComponent{

	private String text;
	private String content = "";
	private Action action;
	private boolean ready = false;
	private Color secondaryColor = new Color(150,150,150);

	public InputComponent(String text, Action action, Rect position) {
		super(position);
		this.text = text;
		this.action = action;

		if (action.getClass() == ChatAction.class) {
			((ChatAction) action).setParent(this);
		} else if (action.getClass() == UserNameAction.class) {
			((UserNameAction) action).setParent(this);
		}
	}

	public boolean isReady() {
		return ready;
	}

	public void checkInput() {
		for (int keyCode : InputBuffer.getLastInput()) {
			if (keyCode == KeyEvent.VK_BACK_SPACE) {
				if (content.length() > 0) {
					content = content.substring(0,content.length() - 1);
				}
			} else {
				if (Character.isLetterOrDigit((char) keyCode)) {
					content += (char) keyCode;
				}
			}
		}

		if (InputBuffer.isKeyPressed(KeyEvent.VK_ENTER) > 0 && content.length() > 0) {
			ready = true;

		}
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
