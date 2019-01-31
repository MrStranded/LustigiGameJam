package Graphics.Gui.Actions;

import Graphics.Gui.GUICreater;
import Logic.WorldState;
import Graphics.Screen;

public class HostAction implements Action {
	@Override
	public void perform(Screen screen, WorldState worldState) {
		screen.updateGui(GUICreater.createHostMenu(screen));
	}
}
