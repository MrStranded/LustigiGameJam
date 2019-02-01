package Logic;

import Globals.MasterSwitch;
import Graphics.Window;
import Graphics.Gui.GUI;
import Graphics.Gui.GUICreater;
import Translater.Parser;
import Translater.Sender;

public class GameLoop extends Thread {

	private Window window;
	private WorldState worldState;
	private GUI gui;

	private long t;
	private long tLastPlayerListSend = 0;
	private long millisPerFrame = 1000/60;

	public GameLoop() {
		window = new Window();

		worldState = new WorldState();
		worldState.createTestMap();

		Sender.setWorldState(worldState);
		Parser.setWorldState(worldState);
	}

	@Override
	public void run() {
		while (true) {
			t = System.currentTimeMillis();

			if (MasterSwitch.isServer) {
				if (System.currentTimeMillis() - tLastPlayerListSend > 1000) {
					Sender.sendPlayers();
					tLastPlayerListSend = System.currentTimeMillis();
				}
			}

			Parser.update();

			window.registerInput(worldState);
			window.drawWorld(worldState);

			long waitTime = millisPerFrame - (System.currentTimeMillis()-t);
			if (waitTime > 0) {
				try {
					sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
