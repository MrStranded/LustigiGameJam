package Logic;

public enum Attributes {
    SPEED(0),
    HEALTH(1),
    COLOR(2),
    LOOT(3),
    WIDTH(4),
    SLOTS(5),
    DAMAGE(6),
    RANGE(7),
    COOLDOWN(8),
    SLOTSIZE(9),
    ANGLE(10),
    ARMOR(11),
    ACCELERATION(12),
    CATEGORY(13),
    IMAGE(14),
    LENGTH(15),
    DIRECTION(16),
    CONTROL(17);

    private final int att;

    private Attributes(int att) {
        this.att = att;
    }

    public int valueOf() {
        return this.att;
    }
}
