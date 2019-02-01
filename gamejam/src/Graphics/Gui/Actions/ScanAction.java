package Graphics.Gui.Actions;

import Graphics.Gui.GUICreater;
import Graphics.Screen;
import Logic.WorldState;
import Network.Network;

public class ScanAction implements Action {
	@Override
	public void perform(Screen screen, WorldState worldState) {
		new Network(worldState.userName).scan();
	}
}
