package Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	public Mouse () {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		InputBuffer.mouseIsClicked(e.getButton());
		InputBuffer.setMousePosition(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		InputBuffer.mouseIsPressed(e.getButton());
		InputBuffer.setMousePosition(e.getX(), e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		InputBuffer.mouseIsReleased(e.getButton());
		InputBuffer.setMousePosition(e.getX(), e.getY());
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		InputBuffer.setMousePosition(e.getX(), e.getY());
	}
}
