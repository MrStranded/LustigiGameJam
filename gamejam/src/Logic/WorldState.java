package Logic;

import java.util.concurrent.ConcurrentLinkedDeque;

public class WorldState {

	private final int tileSize = 32;

	private int[][] map;
	private int size;

	private ConcurrentLinkedDeque<Component> units;

	public void createTestMap() {
		size = 32;
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
}
