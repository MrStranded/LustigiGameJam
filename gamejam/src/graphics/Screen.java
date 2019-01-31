package graphics;

import Logic.WorldState;
import graphics.gui.*;
import graphics.gui.Box;
import graphics.gui.Button;
import input.InputBuffer;
import input.Keyboard;
import input.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Screen extends JPanel {

	private int width, height;
	private Window window;
	private WorldState worldState;
	private GUI gui;

	public Screen(int width, int height, Window window) {
		this.width = width;
		this.height = height;

		this.window = window;

		initialize();
	}

	private void initialize() {
		setBackground(Color.BLUE);
		setSize(width, height);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## DRAW #################################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	public void draw(WorldState worldState, GUI gui) {
		this.worldState = worldState;
		this.gui = gui;
	}

	public void paintComponent(Graphics g) {
		// clearing the screen
		g.clearRect(0,0,width, height);

		// drawing the map
		drawWorld(g);

		// drawing the gui
		drawGui(g);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## DRAW Map #############################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	private void drawWorld(Graphics g) {
		if (worldState != null) {
			int s = worldState.getSize();
			int[][] map = worldState.getMap();

			Color[] tileColors = new Color[]{
					Color.BLUE,
					Color.YELLOW,
					Color.GRAY
			};

			int tileSize = 32;

			for (int x = 0; x < s; x++) {
				for (int y = 0; y < s; y++) {
					int xPos = x * tileSize;
					int yPos = y * tileSize;

					g.setColor(tileColors[map[x][y]]);
					g.fillRect(xPos, yPos, tileSize, tileSize);
					g.setColor(Color.BLACK);
					g.drawString(String.valueOf(map[x][y]), xPos + tileSize / 2, yPos + tileSize / 2);
				}
			}
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## DRAW Gui #############################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	private void drawGui(Graphics g) {
		if (gui != null) {
			for (Box box : gui.getBoxList()) {
				drawBox(g, box);
			}
		}
	}

	private void drawBox(Graphics g, Box box) {
		g.setColor(Color.GREEN);
		drawRect(g, box.getPosition());

		for (Button button : box.getButtonList()) {
			drawButton(g, button);
		}
	}

	private void drawButton(Graphics g, Button button) {g.setColor(Color.CYAN);
		drawRect(g, button.getPosition());

		g.setColor(Color.BLACK);
		g.drawString(button.getText(), button.getPosition().getX()+10, button.getPosition().getY() + (button.getPosition().getH()) / 2);
	}

	private void drawRect(Graphics g, Rect r) {
		g.fillRect(r.getX(), r.getY(), r.getW(), r.getH());
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## CLOSE ################################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	public void close() {
		window.close();
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## GnSs #################################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	public WorldState getWorldState() {
		return worldState;
	}
}
