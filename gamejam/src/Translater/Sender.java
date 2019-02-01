package Translater;

import Globals.MasterSwitch;
import Logic.Component;
import Logic.WorldState;
import Network.Client;
import Network.ClientModel;
import Network.Server;

import java.io.IOException;

public class Sender {

	private static Server server;
	private static Client client;

	private static WorldState worldState;

	public static void setWorldState(WorldState worldState) {
		Sender.worldState = worldState;
	}

	public static void setServer(Server server) {
		Sender.server = server;
	}

	public static void setClient(Client client) {
		Sender.client = client;
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## SENDING STUFF ########################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	public static void sendWorldState() {
		if (worldState != null) {
			StringBuilder stringBuilder = new StringBuilder(Encoder.createMapMsg(worldState));

			for (Component component : worldState.getUnits()) {
				stringBuilder.append(Separator.INFO);
				stringBuilder.append(Encoder.createFullComponentMsg(component));
			}

			send(stringBuilder.toString());
		}
	}

	public static void sendMap() {
		if (worldState != null) {
			send(Encoder.createMapMsg(worldState));
		}
	}

	public static void sendPlayers() {
		if (worldState != null) {
			send(Encoder.createPlayerListMsg(worldState));
		}
	}

	public static void sendLogin() {
		if (worldState != null) {
			send(Encoder.createPlayerLoginMsg(worldState.userName));
		}
	}

	public static void sendMessages() {
		if (worldState != null) {
			if (worldState.getChatMessages().size() > 0) {
				send(Encoder.createChatMsgAll(worldState));
			}
		}
	}

	public static void sendMessage(String msg) {
		if (msg != null) {
			send(Encoder.createChatMsg(msg));
		}
	}

	public static void sendDisconnect(int playerId) {
		send(Encoder.createDisconnect(playerId));
	}

	public static void sendStart() {
		send(Encoder.createStartMsg(0));
	}

	// -----------------------------------------------------------------------------------------------------------------
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// ########################################################## THE ACTUAL SENDING ###################################
	// &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	// -----------------------------------------------------------------------------------------------------------------

	private static void send(String msg) {
		if (MasterSwitch.isServer) {
			if (server != null) {
				try {
					server.broadcastGameState(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (client != null) {
				try {
					client.sendGameState(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
