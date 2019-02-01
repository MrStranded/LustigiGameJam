package Graphics;

import Graphics.Gui.*;
import Graphics.Gui.GraphicalComponents.ButtonComponent;
import Graphics.Gui.GraphicalComponents.TextComponent;
import Graphics.Gui.GraphicalComponents.UIComponent;
import Graphics.Gui.GraphicalComponents.UpdatingTextComponent;
import Input.InputBuffer;
import Logic.WorldState;

import javax.swing.*;
import java.awt.*;


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

		gui = GUICreater.createMainMenu(this);
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## DRAW #################################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	public void updateGui(GUI gui) {
		if (this.gui != null && this.gui != gui) {
			this.gui.cleanUp();
		}
		this.gui = gui;
	}

	public void registerInput(WorldState worldState) {
		if (gui != null) {
			gui.registerInput(this, worldState);
		}
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## DRAW #################################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	public void updateWorldState(WorldState worldState) {
		this.worldState = worldState;
	}

	public void paintComponent(Graphics g) {
		// clearing the screen
		g.clearRect(0,0,width, height);

		// drawing the map
		drawWorld(g);

		// drawing the Gui
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

			int tileSize = 40;

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
			for (UIComponent uiComponent : gui.getUIComponentList()) {
				drawUIComponent(g, uiComponent);
			}
		}
	}

	private void drawUIComponent(Graphics g, UIComponent uiComponent) {

		if (uiComponent.getClass() == ButtonComponent.class) { // button
			if (uiComponent.inside(InputBuffer.getMousePosition())) {
				g.setColor(((ButtonComponent) uiComponent).getSecondaryColor());
			} else {
				g.setColor(uiComponent.getColor());
			}
			drawRect(g, uiComponent.getPosition());

			g.setColor(Color.BLACK);
			g.drawString(((ButtonComponent) uiComponent).getText(), uiComponent.getPosition().getX() + 10, uiComponent.getPosition().getY() + (uiComponent.getPosition().getH()) / 2);

		} else if (uiComponent.getClass() == TextComponent.class) { // text
			g.setColor(uiComponent.getColor());
			drawRect(g, uiComponent.getPosition());

			g.setColor(Color.BLACK);
			String[] lines = ((TextComponent) uiComponent).getText().split("\n");
			int i = 0;
			int xPos = uiComponent.getPosition().getX() + 5;
			int yPos = uiComponent.getPosition().getY() + 10;
			for (String line : lines) {
				g.drawString(line, xPos, yPos + i * 15);
				i++;
			}

		} else if (uiComponent.getClass() == UpdatingTextComponent.class) { // updating text
			g.setColor(uiComponent.getColor());
			drawRect(g, uiComponent.getPosition());

			g.setColor(Color.BLACK);
			String[] lines = ((UpdatingTextComponent) uiComponent).getText().split("\n");
			int i = 0;
			int xPos = uiComponent.getPosition().getX() + 5;
			int yPos = uiComponent.getPosition().getY() + 10;
			for (String line : lines) {
				g.drawString(line, xPos, yPos + i * 15);
				i++;
			}

		} else { // default
			g.setColor(uiComponent.getColor());
			drawRect(g, uiComponent.getPosition());
		}

		for (UIComponent subComponent : uiComponent.getUIComponentList()) {
			drawUIComponent(g, subComponent);
		}
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
		if (gui != null) {
			gui.cleanUp();
		}
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
