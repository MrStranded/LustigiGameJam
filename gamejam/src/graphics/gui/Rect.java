package graphics.gui;

public class Rect {

	private int x,y,w,h;

	public Rect(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean inside(int mx, int my) {
		return (mx >= x && my >= y && mx < x+w && my < y+h);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}
}
