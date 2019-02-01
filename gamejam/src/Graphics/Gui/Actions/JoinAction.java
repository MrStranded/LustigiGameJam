package Graphics.Gui.Actions;

import Graphics.Gui.GUICreater;
import Graphics.Screen;
import Logic.WorldState;
import Network.Network;

public class JoinAction implements Action {
	@Override
	public void perform(Screen screen, WorldState worldState) {
		screen.updateGui(GUICreater.createLobbyMenu(screen));

		new Network().scan();
	}
}
