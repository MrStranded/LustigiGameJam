package Logic;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public enum Controls {
    LEFT(0, KeyEvent.VK_LEFT),
    RIGHT(1, KeyEvent.VK_RIGHT),
    UP(2, KeyEvent.VK_UP),
    DOWN(3, KeyEvent.VK_DOWN),
    MOUSE(4,MouseEvent.BUTTON3);

    private int id;
    private int event;

    private static Map<Integer, Controls> map = new HashMap<Integer, Controls>();

    public static Controls valueOf(int value) {
        return map.get(value);
    }

    private Controls(int id, int event) {
        this.id = id;
        this.event = event;
    }
}
