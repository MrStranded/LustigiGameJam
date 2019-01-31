package graphics.gui.actions;

import Logic.WorldState;
import graphics.Screen;

public class CloseAction implements Action {
	@Override
	public void perform(Screen screen, WorldState worldState) {
		if (screen != null) {
			screen.close();
		}
	}
}
