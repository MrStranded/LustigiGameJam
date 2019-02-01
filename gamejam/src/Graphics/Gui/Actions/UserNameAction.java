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
		if (parent != null) {
			String content = parent.getContent().trim();
			if (content.length() > 0) {
				worldState.userName = content;
				parent.setContent("");
				parent.setReady(false);

				screen.updateGui(GUICreater.createMainMenu(screen));
			}
		}
	}
}
