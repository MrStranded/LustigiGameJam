package Translater;

import Globals.MasterSwitch;
import Logic.WorldState;
import Network.Client;
import Network.ClientModel;
import Network.Server;

import java.io.IOException;

public class Sender {

	private static Server server;
	private static Client client;

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

	private static void sendWorldState(WorldState worldState) {
		if (worldState != null) {
			StringBuilder stringBuilder = new StringBuilder();
		}
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
					server.broadcast(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			if (client != null) {
				try {
					client.send(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
