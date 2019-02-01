package Graphics.Gui.Actions;

import Globals.MasterSwitch;
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
			if (parent.getContent().length() > 0) {
				String msg = parent.getText() + parent.getContent();
				if (MasterSwitch.isServer) {
					worldState.addChatMessage(msg);
				}
				Sender.sendMessage(msg);
				parent.setContent("");
				parent.setReady(false);
			}
		}
	}
}
