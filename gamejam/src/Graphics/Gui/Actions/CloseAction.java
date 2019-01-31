package Graphics.Gui.Actions;

import Logic.WorldState;
import Graphics.Screen;

public class CloseAction implements Action {
	@Override
	public void perform(Screen screen, WorldState worldState) {
		if (screen != null) {
			screen.close();
		}
	}
}
