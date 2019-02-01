package Graphics.Gui.Actions;

import Graphics.Gui.GUICreater;
import Logic.WorldState;
import Graphics.Screen;
import Network.Network;
import Network.Client;
import Translater.Sender;

public class JoinAction implements Action {
	@Override
	public void perform(Screen screen, WorldState worldState) {
		screen.updateGui(GUICreater.createPlayerMenu(screen));

		Network network = new Network();
		Client client;
		String ip = "127.0.0.1";
		//ip = "192.168.178.20";
		int port = 42069;
		String username = worldState.userName;

		try {
			client = network.connect(ip, port, username);
			Sender.setClient(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
