package graphics.gui;

import Logic.WorldState;
import graphics.Screen;
import graphics.gui.actions.Action;
import input.InputBuffer;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class GUI {

	List<Box> boxList = new ArrayList<Box>();

	public GUI () {
	}

	public void addBox(Box box) {
		boxList.add(box);
	}

	public List<Box> getBoxList() {
		return boxList;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## INPUT ################################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	public void registerInput(Screen screen, WorldState worldState) {
		int scrollSpeed = 3;

		//dx += scrollSpeed * (InputBuffer.isKeyPressed(KeyEvent.VK_A) - InputBuffer.isKeyPressed(KeyEvent.VK_D));
		//dy += scrollSpeed * (InputBuffer.isKeyPressed(KeyEvent.VK_W) - InputBuffer.isKeyPressed(KeyEvent.VK_S));
	}

	public void registerMenus(Screen screen, WorldState worldState) {
		Action actionToPerform = null;

		if (actionToPerform != null) {
			actionToPerform.perform(screen, worldState);
		}
	}

}
