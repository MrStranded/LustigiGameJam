package Logic;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by Lukas on 01.02.19.
 */
public class MapGen {

    public static int[][] generate(int size) {

        int[][] map = new int[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                map[x][y] = (int) (Math.random() * 3d);
            }
        }

        return map;
    }
}
