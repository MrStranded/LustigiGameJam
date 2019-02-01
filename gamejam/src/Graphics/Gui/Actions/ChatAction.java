package Graphics.Gui.Actions;

import Graphics.Gui.GUICreater;
import Graphics.Gui.GraphicalComponents.InputComponent;
import Graphics.Screen;
import Input.InputBuffer;
import Logic.WorldState;
import Network.Client;
import Network.Network;
import Translater.Sender;

public class ChatAction implements Action {

	private InputComponent parent;

	public void setParent(InputComponent parent) {
		this.parent = parent;
	}

	@Override
	public void perform(Screen screen, WorldState worldState) {
		if (parent != null) {
			Sender.sendMessage(worldState.userName + ": " + parent.getContent());
			parent.setContent("");
			parent.setReady(false);
		}
	}
}
