package Graphics.Gui.Actions;

import Globals.MasterSwitch;
import Graphics.Gui.GUICreater;
import Logic.Player;
import Logic.WorldState;
import Graphics.Screen;
import Network.Network;
import Network.Server;
import Translater.Sender;

public class HostAction implements Action {
	@Override
	public void perform(Screen screen, WorldState worldState) {
		screen.updateGui(GUICreater.createHostMenu(screen));

		MasterSwitch.isServer = true;
		
		Player serverPlayer = new Player(0);
		serverPlayer.setName("Phantom");
		worldState.addPlayer(serverPlayer);

		try {
			Network network = new Network();
			Server server;
			int port = 42069;

			server = network.startServer(port);
			Sender.setServer(server);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
