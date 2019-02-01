package Graphics.Gui.Actions;

import Logic.WorldState;
import Graphics.Screen;

public interface Action {

	public void perform(Screen screen, WorldState worldState);
}
