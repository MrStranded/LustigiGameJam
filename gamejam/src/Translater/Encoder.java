package Translater;

import Logic.Attributes;
import Logic.Component;
import Logic.Player;
import Logic.WorldState;
import Network.Server;

import java.util.HashMap;

public class Encoder {

	public static String createMapMsg(WorldState worldState) {
		StringBuilder stringBuilder = new StringBuilder(KeyWord.MAP.name());

		if (worldState != null) {
			stringBuilder.append(Separator.KEYWORD);

			int s = worldState.getSize();
			stringBuilder.append(s);
			for (int x=0; x<s; x++) {
				for (int y=0; y<s; y++) {
					stringBuilder.append(Separator.VALUE);
					stringBuilder.append(String.valueOf(worldState.getMap()[x][y]));
				}
			}
		}

		return stringBuilder.toString();
	}

	public static String createFullComponentMsg(Component component) {
		if (component != null) {
			StringBuilder stringBuilder = new StringBuilder(createComponentMsg(component));

			stringBuilder.append(Separator.INFO);
			stringBuilder.append(createAttributesMsg(component));

			stringBuilder.append(Separator.INFO);
			stringBuilder.append(createPositionMsg(component));

			for (Component sub : component.getSubComponents()) {
				stringBuilder.append(Separator.INFO);
				stringBuilder.append(createFullComponentMsg(sub));
			}

			return stringBuilder.toString();
		}
		return "";
	}

	public static String createComponentMsg(Component component) {
		StringBuilder stringBuilder = new StringBuilder(KeyWord.COMPONENT.name());

		if (component != null) {
			stringBuilder.append(Separator.KEYWORD);

			stringBuilder.append(component.getId());

			for (Component sub : component.getSubComponents()) {
				stringBuilder.append(Separator.VALUE);
				stringBuilder.append(sub.getId());
			}
		}

		return stringBuilder.toString();
	}

	public static String createAttributesMsg(Component component) {
		StringBuilder stringBuilder = new StringBuilder(KeyWord.ATTRIBUTES.name());

		if (component != null) {
			stringBuilder.append(Separator.KEYWORD);

			stringBuilder.append(component.getId());

			for (double d : component.getAttributes()) {
				stringBuilder.append(Separator.VALUE);
				stringBuilder.append(d);
			}
		}

		return stringBuilder.toString();
	}

	public static String createPositionMsg(Component component) {
		StringBuilder stringBuilder = new StringBuilder(KeyWord.POSITION.name());

		if (component != null) {
			stringBuilder.append(Separator.KEYWORD);

			stringBuilder.append(component.getId());

			stringBuilder.append(Separator.VALUE);
			stringBuilder.append(component.getPosition().getX());
			stringBuilder.append(Separator.VALUE);
			stringBuilder.append(component.getPosition().getY());
			stringBuilder.append(Separator.VALUE);
			stringBuilder.append(component.getAttribute(Attributes.SPEED));
			stringBuilder.append(Separator.VALUE);
			stringBuilder.append(component.getAttribute(Attributes.ANGLE));

		}

		return stringBuilder.toString();
	}

	public static String createPlayerListMsg(WorldState worldState) {
		StringBuilder stringBuilder = new StringBuilder(KeyWord.PLAYERLIST.name());

		if (worldState != null) {
			stringBuilder.append(Separator.KEYWORD);

			boolean first = true;
			for (Player player : worldState.getPlayers()) {
				if (!first) {stringBuilder.append(Separator.VALUE);}
				stringBuilder.append(player.getId());
				stringBuilder.append(Separator.VALUE);
				stringBuilder.append(player.getName());
				stringBuilder.append(Separator.VALUE);
				stringBuilder.append(player.getPing());
				stringBuilder.append(Separator.VALUE);
				stringBuilder.append(player.getCash());
				first = false;
			}
		}

		return stringBuilder.toString();
	}

	public static String createChatMsg(String chatMessage) {
		StringBuilder stringBuilder = new StringBuilder(KeyWord.CHAT.name());

		if (chatMessage != null) {
			stringBuilder.append(Separator.KEYWORD);

			stringBuilder.append(chatMessage);
		}

		return stringBuilder.toString();
	}

	public static String createChatMsgAll(WorldState worldState) {
		StringBuilder stringBuilder = new StringBuilder();
		if (worldState != null) {
			boolean first = true;

			for (String msg : worldState.getChatMessages()) {
				if (!first) {
					stringBuilder.append(Separator.INFO);
				}

				stringBuilder.append(KeyWord.CHAT.name());
				stringBuilder.append(Separator.KEYWORD);
				stringBuilder.append(msg);
			}
		}

		return stringBuilder.toString();
	}

	public static String createGameList(HashMap<String, String> ipNameTuples) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(KeyWord.GAMELIST.name());
		boolean first = true;

		for (String ip : ipNameTuples.keySet()) {
			if (first) {
				stringBuilder.append(Separator.KEYWORD);
			} else {
				stringBuilder.append(Separator.VALUE);
			}
			stringBuilder.append(ip);
			stringBuilder.append(Separator.VALUE);
			stringBuilder.append(ipNameTuples.get(ip));
		}

		return stringBuilder.toString();
	}

	public static String createDisconnect(int playerId) {
		return KeyWord.DISCONNECT.name() + Separator.KEYWORD + playerId;
	}

	public static String createPlayerIdMsg(int playerId) {
		return KeyWord.PLAYERID.name() + Separator.KEYWORD + playerId;
	}

}
