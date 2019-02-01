package Translater;

import Globals.MasterSwitch;
import Graphics.Screen;
import Logic.Component;
import Logic.Player;
import Logic.WorldState;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Parser {

	private static WorldState worldState;
	private static Screen screen;

	private static Map<Integer, ConcurrentLinkedDeque<String>> playerMessages = new HashMap<>(32);

	public static void setScreen(Screen screen) {
		Parser.screen = screen;
	}

	public static void setWorldState(WorldState worldState) {
		Parser.worldState = worldState;
	}

	public static void parse(int playerId, String msg) {
		ConcurrentLinkedDeque<String> messages = null;
		if ((messages = playerMessages.get(playerId)) == null) {
			messages = new ConcurrentLinkedDeque<String>();
		}
		messages.add(msg);
		playerMessages.put(playerId, messages);
	}


	public static void update() {
		if (worldState != null) {
			for (int playerId : playerMessages.keySet()) {
				ConcurrentLinkedDeque<String> messages = playerMessages.get(playerId);
				//if (messages.size() > 0) {
					//System.out.println("looking at player " + playerId);
					//System.out.println(messages.size() + " messages");
				//}
				Iterator<String> messageIterator = messages.iterator();
				String msg;

				while (messageIterator.hasNext()) {
					msg = messageIterator.next();
					messageIterator.remove();

					if (msg.startsWith("GAME: ")) {
						msg = msg.substring(6);
					}
					String[] infos = msg.split(Separator.INFO);

					for (String info : infos) {
						parseInfo(playerId, info);
					}
				}
			}
		} else {
			System.out.println("Cannot parse, because there is no world state.");
		}
	}

	private static void parseInfo(int playerId, String msg) {
		System.out.println("parse " + msg);
		int sep = msg.indexOf(Separator.KEYWORD);

		if (sep >= 0) {
			String keyword = msg.substring(0, sep);
			String[] values = (msg.substring(sep+1)).split(Separator.VALUE);

			KeyWord key = getFittingKeyWord(keyword);

			Player player;

			switch (key) {
				case MAP:
					int s = Integer.parseInt(values[0]);

					int[][] newMap = new int[s][s];

					for (int x=0; x<s; x++) {
						for (int y=0; y<s; y++) {
							newMap[x][y] = Integer.parseInt(values[1 + y + x*s]);
						}
					}

					worldState.setMap(newMap);

					break;

				case COMPONENT:
					//int id = Integer
					//Component component = worldState.getUnit()

					break;

				case PLAYERLIST:
					if (values.length > 0) {
						int p = values.length / 4;
						for (int i = 0; i < p; i++) {
							int id = Integer.parseInt(values[0+i*4]);
							player = worldState.getPlayer(id);

							if (player == null) {
								player = new Player(id);
								worldState.addPlayer(player);
							}

							player.setName(values[1+i*4]);
							player.setPing(Integer.parseInt(values[2+i*4]));
							player.setCash(Integer.parseInt(values[3+i*4]));
						}
					}

					break;

				case PING:
					player = worldState.getPlayer(playerId);
					if (player != null) {
						player.setPing(Integer.valueOf(values[0]));
					}

					break;

				case PLAYER:
					player = worldState.getPlayer(playerId);
					if (player != null) {
						player.setName(values[0]);
					} else {
						player = new Player(playerId);
						player.setName(values[0]);
						worldState.addPlayer(player);
					}

					break;

				case CHAT:
					System.out.println("received " + values[0]);

					if (MasterSwitch.isServer) {
						worldState.addChatMessage(values[0]);
						Sender.sendMessage(values[0]);
					} else {
						worldState.addChatMessage(values[0]);
					}
					break;

				case GAMELIST:
					if (worldState != null) {
						HashMap<String, String> ipNameTuples = new HashMap<>();

						if (values.length > 1) {
							int t = values.length / 2;
							for (int i = 0; i < t; i++) {
								String ip = values[0+i*2];
								String name = values[1+i*2];

								ipNameTuples.put(ip, name);
							}
						}

						worldState.setIpNameTuples(ipNameTuples);
					}

					break;

				case DISCONNECT:
					if (worldState != null) {
						Player p = worldState.getPlayer(Integer.parseInt(values[0]));
						if (p != null) {
							worldState.getPlayers().remove(p);
						}
					}

					break;

				case PLAYERID:
					if (worldState != null) {
						worldState.setUserId(Integer.parseInt(values[0]));
						System.out.println("--------------------------------------------- i am " + worldState.getUserId());
					}

					break;

				case START:
					if (!MasterSwitch.isServer) {
						if (screen != null) {
							screen.updateGui(null);
						}
					}

					break;
			}
		}
	}

	private static KeyWord getFittingKeyWord(String keyword) {
		for (KeyWord keyWord : KeyWord.values()) {
			if (keyWord.name().equals(keyword)) {
				return keyWord;
			}
		}
		return null;
	}

}
