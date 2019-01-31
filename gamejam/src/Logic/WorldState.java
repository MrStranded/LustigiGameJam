package Logic;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class WorldState {

	private int[][] map;
	private int size;

	private ConcurrentLinkedDeque<String> units;



	public void createTestMap() {
		size = 32;
		map = new int[size][size];

		for (int x=0; x<size; x++) {
			for (int y=0; y<size; y++) {
				map[x][y] = (int) (Math.random()*3d);
			}
		}

		units = new ConcurrentLinkedDeque<String>();

		units.add("One");
		units.add("Two");
	}



	public int[][] getMap() {
		return map;
	}

	public int getSize() {
		return size;
	}

	public ConcurrentLinkedDeque<String> getUnits() {
		return units;
	}
}
