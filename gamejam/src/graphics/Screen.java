package graphics;

import Logic.WorldState;

import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

	private int width, height;
	private WorldState worldState;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;

		initialize();
	}

	private void initialize() {
		setBackground(Color.BLUE);
		setSize(width, height);

	}

	public void draw(WorldState worldState) {
		this.worldState = worldState;
	}

	public void paintComponent(Graphics g) {
		g.clearRect(0,0,width, height);

		int s = worldState.getSize();
		int[][] map = worldState.getMap();

		Color[] tileColors = new Color[] {
				Color.BLUE,
				Color.YELLOW,
				Color.GRAY
		};

		int tileSize = 32;

		for (int x=0; x<s; x++) {
			for (int y=0; y<s; y++) {
				int xPos = x*tileSize;
				int yPos = y*tileSize;

				g.setColor(tileColors[map[x][y]]);
				g.fillRect(xPos, yPos, tileSize, tileSize);
				g.setColor(Color.BLACK);
				g.drawString(String.valueOf(map[x][y]),xPos+tileSize/2, yPos+tileSize/2);
			}
		}
	}
}
