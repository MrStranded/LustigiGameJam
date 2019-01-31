package input;

public class InputBuffer {

	private static boolean[] keyPressed = new boolean[223];
	private static boolean[] mousePressed = new boolean[3];
	private static boolean[] mouseClicked = new boolean[3];
	private static int mx, my;

	public static void keyIsPressed(int keyCode) {
		if (keyCode >= 0 && keyCode < keyPressed.length) {
			keyPressed[keyCode] = true;
		}
	}
	public static void keyIsReleased(int keyCode) {
		if (keyCode >= 0 && keyCode < keyPressed.length) {
			keyPressed[keyCode] = false;
		}
	}

	public static void mouseIsPressed(int mouseCode) {
		if (mouseCode >= 0 && mouseCode < mousePressed.length) {
			mousePressed[mouseCode] = true;
		}
	}
	public static void mouseIsReleased(int mouseCode) {
		if (mouseCode >= 0 && mouseCode < mousePressed.length) {
			mousePressed[mouseCode] = false;
		}
	}
	public static void mouseIsClicked(int mouseCode) {
		if (mouseCode >= 0 && mouseCode < mouseClicked.length) {
			mouseClicked[mouseCode] = true;
		}
	}

	public static void setMousePosition(int x, int y) {
		mx = x; my = y;
	}

	public static int isKeyPressed(int keyCode) {
		if (keyCode >= 0 && keyCode < keyPressed.length) {
			return keyPressed[keyCode] ? 1 : 0;
		}
		return 0;
	}

	public static int isMousePressed(int mouseCode) {
		if (mouseCode >= 0 && mouseCode < mousePressed.length) {
			return mousePressed[mouseCode] ? 1 : 0;
		}
		return 0;
	}

	public static int isMouseClicked(int mouseCode) {
		if (mouseCode >= 0 && mouseCode < mouseClicked.length) {
			int r = mouseClicked[mouseCode] ? 1 : 0;
			mouseClicked[mouseCode] = false;
			return r;
		}
		return 0;
	}

	public static int getMouseX() { return mx; }
	public static int getMouseY() { return my; }

}
