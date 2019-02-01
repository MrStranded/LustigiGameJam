package Logic;

public enum Categories {
    SHIP(0),
    CANON(1),
    TURRET(2),
    TREASURE(3),
    ARMOR(4),
    SAIL(5),
    SLOT(6),
    PROJECTILE(7),
    MOTOR(8);

    private final int cat;

    private Categories(int cat) {
        this.cat = cat;
    }

    public int valueOf() {
        return this.cat;
    }
}
