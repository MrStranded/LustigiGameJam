package Graphics.Gui.Actions;

import Graphics.Screen;
import Logic.WorldState;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class CopyContentAction implements Action {

	private String toCopy;

	public CopyContentAction (String toCopy) {
		this.toCopy = toCopy;
	}

	@Override
	public void perform(Screen screen, WorldState worldState) {
		StringSelection stringSelection = new StringSelection(toCopy);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);
	}
}
