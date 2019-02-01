package Logic;

import java.util.HashMap;
import java.util.Map;

public enum Tiles {
    GROUND(0, true),
    DEEPWATER(1, false),
    SHALLOWWATER(2, true);

    private int id;
    private boolean collision;
//    private boolean shootable;

    private static Map<Integer, Tiles> map = new HashMap<>();

    public static Tiles valueOf(int key) {
        return map.get(key);
    }

    Tiles(int id, boolean collision) {
        this.id = id;
        this.collision = collision;
//        this.shootable = shootable;
    }

    static void initTiles() {
        for(Tiles t : Tiles.values()) {
            map.put(t.id, t);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
//
//    public boolean isShootable() {
//        return shootable;
//    }
//
//    public void setShootable(boolean shootable) {
//        this.shootable = shootable;
//    }
}
