package Logic;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public enum Controls {
    SHOOTLEFT(0, KeyEvent.VK_LEFT),
    SHOOTRIGHT(1, KeyEvent.VK_RIGHT),
    SHOOTUP(2, KeyEvent.VK_UP),
    SHOOTDOWN(3, KeyEvent.VK_DOWN),
    SHOOTLEFTMOUSE(4, MouseEvent.BUTTON3),
    MOVELEFT(5, KeyEvent.VK_L),
    MOVERIGHT(6, KeyEvent.VK_R),
    MOVEUP(7, KeyEvent.VK_W),
    MOVEDOWN(8, KeyEvent.VK_S),
    TOGGLESAIL(9, KeyEvent.VK_F),
    MOUSEX(10, 0),
    MOUSEY(11, 1),
    MOUSELEFT(12, MouseEvent.BUTTON1);

    private int id;
    private int event;

    private static Map<Integer, Controls> map = new HashMap<Integer, Controls>();

    public static Controls controlOf(int value) {
        return map.get(value);
    }

    Controls(int id, int event) {
        this.id = id;
        this.event = event;
    }

    public int valueOf() {
        return this.event;
    }
}
