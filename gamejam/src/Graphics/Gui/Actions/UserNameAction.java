package Graphics.Gui.Actions;

import Graphics.Gui.GUICreater;
import Graphics.Gui.GraphicalComponents.InputComponent;
import Graphics.Screen;
import Logic.WorldState;

public class UserNameAction implements Action {

	private InputComponent parent;

	public void setParent(InputComponent parent) {
		this.parent = parent;
	}

	@Override
	public void perform(Screen screen, WorldState worldState) {
		if (parent != null && parent.getContent().length() > 0) {
			worldState.userName = parent.getContent();
			parent.setContent("");

			screen.updateGui(GUICreater.createMainMenu(screen));
		}
	}
}
