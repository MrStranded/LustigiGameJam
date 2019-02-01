package Logic;

import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;

public class WorldState {

	private final int tileSize = 40;
	private final int mapSize = 32;

	private int[][] map;
	private int size;

	public String userName = "-";
	public String ip = "127.0.0.1";

	private ConcurrentLinkedDeque<Player> players;
	private ConcurrentLinkedDeque<Component> units;
	private ConcurrentLinkedDeque<String> chatMessages;

	private HashMap<String, String> ipNameTuples;

	public HashMap<String, String> getIpNameTuples() {
		return ipNameTuples;
	}

	public void setIpNameTuples(HashMap<String, String> ipNameTuples) {
		this.ipNameTuples = ipNameTuples;
	}

	public WorldState() {
		players = new ConcurrentLinkedDeque<Player>();
		units = new ConcurrentLinkedDeque<Component>();
		chatMessages = new ConcurrentLinkedDeque<String>();

		createTestMap();
	}

	public void createTestMap() {
		size = mapSize;
		map = new int[size][size];

		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				map[x][y] = (int) (Math.random()*3d);
			}
		}

		units = new ConcurrentLinkedDeque<>();
	}

	public void addUnit(Component component) {
		units.add(component);
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public int getTileSize() {
		return tileSize;
	}

	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public int getSize() {
		return size;
	}

	public ConcurrentLinkedDeque<Component> getUnits() {
		return units;
	}

	public void removeUnit(Component unit) {
		units.remove(unit);
	}

	public Component getUnit(int id) {
		for (Component component : units) {
			if (component.getId() == id) {
				return component;
			}
		}
		return null;
	}

	public ConcurrentLinkedDeque<Player> getPlayers() {
		return players;
	}

	public Player getPlayer(int id) {
		for (Player player : players) {
			if (player.getId() == id) {
				return player;
			}
		}
		return null;
	}

	public void setPlayers(ConcurrentLinkedDeque<Player> players) {
		this.players = players;
	}

	public void setUnits(ConcurrentLinkedDeque<Component> units) {
		this.units = units;
	}

	public void addChatMessage(String msg) {
		chatMessages.add(msg);
		if (chatMessages.size() > 5) {
			chatMessages.pop();
		}
	}

	public ConcurrentLinkedDeque<String> getChatMessages() {
		return chatMessages;
	}
}
