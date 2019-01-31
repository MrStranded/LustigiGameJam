package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	public Keyboard () {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		InputBuffer.keyIsPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		InputBuffer.keyIsReleased(e.getKeyCode());
	}
}
