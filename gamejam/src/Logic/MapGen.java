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
                int nr = (int) (Math.abs(Math.random() - Math.random()) * 3d);
                nr = 0;
                map[x][y] = nr;
            }
        }

        map = getBlobs(map);

        return map;
    }

    public static int[][] getBlobs(int[][] map) {
        int blobs = map.length;
        for (int i = 0; i < blobs; i++) {
            int x = (int) (Math.random() * map.length);
            int y = (int) (Math.random() * map.length);
            map = setBlobs(map, x, y, 0.4f);
        }
        return map;
    }

    public static int[][] setBlobs(int[][] map, int x, int y, float p) {
        if (map[x][y] != 0) return map;
        if (Math.sin(Math.random()) <= p) {
            map[x][y] = (int) (Math.random() * 2d + 1);
            int xp = (x+1) % map.length;
            int xm = (x-1) % map.length;
            xm = (xm < 0) ? (xm + map.length) : xm;
            int yp = (y+1) % map.length;
            int ym = (y-1) % map.length;
            ym = (ym < 0) ? (ym + map.length) : ym;
            map = setBlobs(map, xp, y, p);
            map = setBlobs(map, x, yp, p);
            map = setBlobs(map, xm, y, p);
            map = setBlobs(map, x, ym, p);
        }
        return map;
    }
}
