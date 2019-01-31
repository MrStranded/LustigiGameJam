package Logic;

import java.util.concurrent.ConcurrentLinkedDeque;

public class WorldState {

	private final int tilesize = 32;

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

	public double[] shoot() {
		

		return null;
	}

	public double[] fly() {

		return null;
	}

	public double[] move(Position p, double[] inputVector) {
		int[] index = getTileIndex(p);

		Tiles xTile = getTile(new Position(p.getX() + inputVector[0], p.getY()));
		Tiles yTile = getTile(new Position(p.getX(), p.getY() + inputVector[1]));

		int xBorder = tilesize * (index[0] + (inputVector[0] >= 0 ? 1 : 0));
		int yBorder = tilesize * (index[1] + (inputVector[1] >= 0 ? 1 : 0));

		if (xTile.isCollision()) {
			inputVector[0] = Math.abs(xBorder - p.getX());
		}

		if (yTile.isCollision()) {
			inputVector[1] = Math.abs(yBorder - p.getY());
		}

		return inputVector;
	}

	public Tiles getTile(Position p) {
		return Tiles.valueOf(map[(int)p.getX()/tilesize][(int)p.getY()/tilesize]);
	}

	public int[] getTileIndex(Position p) {
		int[] index = {(int)p.getX()/tilesize, (int)p.getY()/tilesize};
		return index;
	}


	public int[][] getMap() {
		return map;
	}

	public int getSize() {
		return size;
	}

	public ConcurrentLinkedDeque<Component> getUnits() {
		return units;
	}
}
