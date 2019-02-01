package Graphics.Gui.GraphicalComponents;

public class SmallUpdateTimer extends Thread {

	private UpdatingTextComponent parent;
	private long t;
	private boolean running = true;

	public SmallUpdateTimer(UpdatingTextComponent parent) {
		this.parent = parent;

		setDaemon(true);

		t = System.currentTimeMillis();
		start();
	}

	public void run() {
		while (running) {
			parent.update();

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopRunning() {
		running = false;
	}
}
