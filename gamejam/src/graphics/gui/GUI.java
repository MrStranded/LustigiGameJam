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

		registerMenus(screen, worldState);

		InputBuffer.flushMouse();
	}

	public void registerMenus(Screen screen, WorldState worldState) {
		Action actionToPerform = null;
		int[] m = InputBuffer.getMousePosition();

		for (int i=0; i<3; i++) {
			System.out.println(i + ": " + InputBuffer.isMousePressed(i) + ", " + InputBuffer.isMouseClicked(i));
		}

		if (InputBuffer.isMouseClicked(1) > 0) {
			System.out.println("mouse is clicked " + m[0] + " , " + m[1]);
			for (Box box : boxList) {
				if (box.inside(m)) {
					System.out.println("box " + box.getPosition());
					for (Button button : box.getButtonList()) {
						System.out.println("button " + button.getPosition());
						if (button.inside(m)) {
							System.out.println("action: " + button.getText());
							actionToPerform = button.getAction();
						} else {
							System.out.println("notin " + m[0] + ", " + m[1] + " " + button.getPosition());
						}
					}
				}
			}
		}

		if (actionToPerform != null) {
			actionToPerform.perform(screen, worldState);
		}
	}

}
