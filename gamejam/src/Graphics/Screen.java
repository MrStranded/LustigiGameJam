package Graphics;

import Globals.Images;
import Logic.Attributes;
import Logic.WorldState;
import Logic.Component;
import Graphics.Gui.*;
import Graphics.Gui.GraphicalComponents.*;
import Graphics.Gui.GraphicalComponents.TextComponent;
import Input.InputBuffer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class Screen extends JPanel {

	private int width, height;
	private Window window;
	private WorldState worldState;
	private GUI gui;

	private final int tileSize = 40;

	public Screen(int width, int height, Window window) {
		this.width = width;
		this.height = height;

		this.window = window;

		initialize();
	}

	private void initialize() {
		setBackground(Color.BLUE);
		setSize(width, height);

		gui = GUICreater.createUserNameInputMenu(this);
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

		// drawing the units
		drawUnits(g);

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
	// ########################################################## DRAW Units ###########################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	private void drawUnits(Graphics g) {
		if (worldState != null) {
			for (Component unit : worldState.getUnits()) {
				int xPos = (int) ((unit.getPosition().getX()+0.5d)*tileSize);
				int yPos = (int) ((unit.getPosition().getY()+0.5d)*tileSize);

				BufferedImage unitImage = Images.getComponentImage((int) unit.getAttribute(Attributes.IMAGE), unit.getAttribute(Attributes.ANGLE));
				if (unitImage != null) {
					int offset = unitImage.getWidth()/2;
					g.drawImage(unitImage, xPos-offset, yPos-offset, null);
				} else {
					g.setColor(Color.RED);
					g.fillOval(xPos - 20, yPos - 20, 40, 40);
				}
				g.setColor(Color.BLACK);
				g.drawString(String.valueOf((int) unit.getAttribute(Attributes.PLAYER)), xPos, yPos);
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

		} else if (uiComponent.getClass() == InputComponent.class) { // button
			if (uiComponent.inside(InputBuffer.getMousePosition())) {
				g.setColor(((InputComponent) uiComponent).getSecondaryColor());
			} else {
				g.setColor(uiComponent.getColor());
			}
			drawRect(g, uiComponent.getPosition());

			((InputComponent) uiComponent).checkInput();
			if (((InputComponent) uiComponent).isReady()) {
				((InputComponent) uiComponent).getAction().perform(this, worldState);
			}

			g.setColor(Color.BLACK);
			String out = ((InputComponent) uiComponent).getText() + ((InputComponent) uiComponent).getContent();
			g.drawString(out, uiComponent.getPosition().getX() + 10, uiComponent.getPosition().getY() + (uiComponent.getPosition().getH()) / 2);

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
