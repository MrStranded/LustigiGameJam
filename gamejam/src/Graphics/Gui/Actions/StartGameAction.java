package Graphics.Gui.Actions;

import Graphics.Screen;
import Logic.WorldState;

public class StartGameAction implements Action {
	@Override
	public void perform(Screen screen, WorldState worldState) {
		if (screen != null) {
			screen.updateGui(null);

			
		}
	}
}
