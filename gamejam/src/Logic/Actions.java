package Logic;

public enum Actions {
    SHOOTLEFT(0),
    SHOOTRIGHT(1),
    SHOOTUP(2),
    SHOOTDOWN(3),
    SHOOTLEFTMOUSE(4),
    MOVEUPDOWN(5),
    MOVELEFTRIGHT(6),
    TOGGLESAIL(7);

    private int id;

    Actions(int id) {
        this.id = id;
    }

    public int valueOf() {
        return this.id;
    }
}
