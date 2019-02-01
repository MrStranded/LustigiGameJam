package Graphics.Gui.Actions;

import Graphics.Gui.GUICreater;
import Graphics.Screen;
import Logic.WorldState;
import Network.Client;
import Network.Network;
import Translater.Sender;

public class ConnectAction implements Action {
	@Override
	public void perform(Screen screen, WorldState worldState) {
		screen.updateGui(GUICreater.createPlayerMenu(screen));

		Network network = new Network(worldState.userName);
		Client client;
		int port = 42069;
		String username = worldState.userName;

		try {
			client = network.connect(worldState.ip, port);
			Sender.setClient(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
