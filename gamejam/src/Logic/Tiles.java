package Logic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Tiles {
    GROUND(0, true, true),
    DEEPWATER(1, false, true),
    SHALLOWWATER(2, true, true);

    private int id;
    private boolean collision;
    private boolean shootable;

    private static Map<Integer, Tiles> map = new HashMap<Integer, Tiles>();

    public static Tiles valueOf(int value) {
        return map.get(value);
    }

    private Tiles(int id, boolean collision, boolean shootable) {
        this.id = id;
        this.collision = collision;
        this.shootable = shootable;
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

    public boolean isShootable() {
        return shootable;
    }

    public void setShootable(boolean shootable) {
        this.shootable = shootable;
    }
}
