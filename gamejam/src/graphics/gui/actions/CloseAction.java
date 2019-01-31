package graphics.gui.actions;

import graphics.Screen;

public class CloseAction implements Action {
	@Override
	public void perform(Screen screen) {
		if (screen != null) {
			screen.close();
		}
	}
}
