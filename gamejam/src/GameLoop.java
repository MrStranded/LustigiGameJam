import Logic.WorldState;
import graphics.Window;

public class GameLoop extends Thread {

	private Window window;
	private WorldState worldState;

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

			window.drawWorld(worldState);

			try {
				synchronized (this) {
					wait(millisPerFrame - (System.currentTimeMillis()-t));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
