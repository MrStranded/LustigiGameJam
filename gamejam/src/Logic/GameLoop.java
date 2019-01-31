package Logic;

import Graphics.Window;
import Graphics.Gui.GUI;
import Graphics.Gui.GUICreater;

public class GameLoop extends Thread {

	private Window window;
	private WorldState worldState;
	private GUI gui;

	private long t;
	private long millisPerFrame = 1000/60;

	public GameLoop() {
		window = new Window();

		worldState = new WorldState();
		worldState.createTestMap();

		gui = GUICreater.createMainMenu(window);
	}

	@Override
	public void run() {
		while (true) {
			t = System.currentTimeMillis();

			gui.registerInput(window.getScreen(), worldState);
			window.drawWorld(worldState, gui);

			try {
				sleep(millisPerFrame - (System.currentTimeMillis()-t));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
