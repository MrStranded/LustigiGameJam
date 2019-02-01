package Graphics.Gui.Actions;

import Graphics.Gui.GraphicalComponents.InputComponent;
import Graphics.Screen;
import Logic.WorldState;

public class IpSetAction implements Action {

	private String ip;

	public IpSetAction(String ip) {
		this.ip = ip;
	}

	@Override
	public void perform(Screen screen, WorldState worldState) {
		if (worldState != null && ip != null) {
			worldState.ip = ip;
		}
	}
}
