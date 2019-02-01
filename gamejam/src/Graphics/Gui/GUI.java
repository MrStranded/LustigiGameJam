package Graphics.Gui;

import Graphics.Gui.GraphicalComponents.ButtonComponent;
import Graphics.Gui.GraphicalComponents.UIComponent;
import Logic.WorldState;
import Graphics.Screen;
import Graphics.Gui.Actions.Action;
import Input.InputBuffer;

import java.util.ArrayList;
import java.util.List;

public class GUI {

	List<UIComponent> UIComponentList = new ArrayList<UIComponent>();

	public GUI () {
	}

	public void addUIComponent(UIComponent UIComponent) {
		UIComponentList.add(UIComponent);
	}

	public List<UIComponent> getUIComponentList() {
		return UIComponentList;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## INPUT ################################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	public void registerInput(Screen screen, WorldState worldState) {
		//int scrollSpeed = 3;

		//dx += scrollSpeed * (InputBuffer.isKeyPressed(KeyEvent.VK_A) - InputBuffer.isKeyPressed(KeyEvent.VK_D));
		//dy += scrollSpeed * (InputBuffer.isKeyPressed(KeyEvent.VK_W) - InputBuffer.isKeyPressed(KeyEvent.VK_S));

		registerMenus(screen, worldState);

		InputBuffer.flushMouse();
	}

	public void registerMenus(Screen screen, WorldState worldState) {
		Action actionToPerform = null;
		int[] m = InputBuffer.getMousePosition();

		if (InputBuffer.isMouseClicked(1) > 0) {
			actionToPerform = drillClick(getUIComponentList());
		}

		if (actionToPerform != null) {
			actionToPerform.perform(screen, worldState);
		}
	}

	private Action drillClick(List<UIComponent> uiComponentList) {
		if (uiComponentList != null) {
			for (UIComponent uiComponent : uiComponentList) {
				if (uiComponent.inside(InputBuffer.getMousePosition())) {
					if (uiComponent.getClass() == ButtonComponent.class) {
						return ((ButtonComponent) uiComponent).getAction();
					} else {
						return drillClick(uiComponent.getUIComponentList());
					}
				}
			}
		}
		return null;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## CLEANUP ##############################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	public void cleanUp() {
		for (UIComponent uiComponent : getUIComponentList()) {
			killComponent(uiComponent);
		}
	}

	private void killComponent(UIComponent uiComponent) {
		for (UIComponent c : uiComponent.getUIComponentList()) {
			killComponent(c);
		}
		uiComponent.kill();
	}

}
