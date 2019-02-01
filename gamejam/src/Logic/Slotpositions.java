package Logic;

public enum Slotpositions {
    LEFT(0),
    RIGHT(1),
    UP(2),
    DOWN(3);

    private int id;

    Slotpositions(int id) {
        this.id = id;
    }

    public int valueOf() {
        return this.id;
    }
}
