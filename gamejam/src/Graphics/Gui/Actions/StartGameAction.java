package Graphics.Gui.Actions;

import Globals.MasterSwitch;
import Graphics.Screen;
import Logic.ComponentGen;
import Logic.WorldState;
import Translater.Sender;

public class StartGameAction implements Action {
	@Override
	public void perform(Screen screen, WorldState worldState) {
		if (screen != null) {
			screen.updateGui(null);

			if (MasterSwitch.isServer) {
				Sender.sendWorldState();
				Sender.sendStart();

				ComponentGen.placeShip(0, 0, worldState);
				worldState.setGameRunning(true);
			}
		}
	}
}
