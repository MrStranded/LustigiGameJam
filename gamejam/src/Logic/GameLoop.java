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
	}

	@Override
	public void run() {
		while (true) {
			t = System.currentTimeMillis();

			window.registerInput(worldState);
			window.drawWorld(worldState);

			try {
				sleep(millisPerFrame - (System.currentTimeMillis()-t));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
