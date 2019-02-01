package Logic;

public class Main {
    public static void main(String[] args) {
        Component c = new Component();

        c.set(Attributes.SPEED, 10);
        c.set(Attributes.CATEGORY, Categories.SHIP.valueOf());
        c.set(Attributes.HEALTH, 500);
        c.set(Attributes.WIDTH, 3.5);
        c.set(Attributes.LENGTH, 1.5);

        c.setPosition(new Position(15.3, 199.0));
        c.setId(0);
        c.setSubComponents(null);

        for (double a: c.getAttributes()) {
            System.out.println(a);
        }
    }
}
