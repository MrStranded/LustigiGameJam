package Logic;


import javafx.geometry.Pos;

import java.util.ArrayList;

public class Logic {
    private static WorldState worldState;
    private static int tileSize;

    private static long lastTick;

    public static void init(WorldState ws) {
        worldState = ws;
        tileSize = worldState.getTileSize();
    }

    public static void move(Component component) {
        double[] inputVector = getVector(component);
        Position p = component.getPosition();

        int[] index = getTileIndex(p);

        Tiles xTile = getTile(new Position(p.getX() + inputVector[0], p.getY()));
        Tiles yTile = getTile(new Position(p.getX(), p.getY() + inputVector[1]));

        int xBorder = tileSize * (index[0] + (inputVector[0] >= 0 ? 1 : 0));
        int yBorder = tileSize * (index[1] + (inputVector[1] >= 0 ? 1 : 0));

        if (xTile.isCollision()) {
            component.getPosition().setX(Math.abs(xBorder - p.getX()));
        }

        if (yTile.isCollision()) {
            component.getPosition().setY(Math.abs(yBorder - p.getY()));
        }
    }

    public void shoot(Component ship, Component[] components) {
        for (Component c : components) {
            Component projectile = new Component();
            projectile.set(Attributes.CATEGORY, Categories.PROJECTILE.valueOf());
            projectile.set(Attributes.SPEED, c.getAttribute(Attributes.SPEED));
            projectile.set(Attributes.ANGLE, ship.getAttribute(Attributes.ANGLE) + c.getAttribute(Attributes.ANGLE));
            projectile.setPosition(Position.add(ship.getPosition(), c.getPosition()));

            worldState.addUnit(projectile);
        }

        //TODO: broadcast new prjectiles
    }

    public double[] fly() {


        return null;
    }

    public ArrayList<Component> getNearestUnits(Component component) {
        ArrayList<Component> nearestUnits = new ArrayList<Component>();

        for (Component c : worldState.getUnits()) {
            if (c.getAttribute(Attributes.CATEGORY) != Categories.SHIP.valueOf()) {
                continue;
            }
            if (Position.squaredDistance(Position.subtract(c.getPosition(), component.getPosition())) < Math.pow(component.getAttribute(Attributes.HEIGHT) + getDistance(component), 2)) {
                nearestUnits.add(c);
            }
        }
        return nearestUnits;
    }

    public static double[] getVector(Component component) {
        double distance = getDistance(component);

        return new double[]{Math.cos(component.getAttribute(Attributes.ANGLE)) * distance, Math.sin(component.getAttribute(Attributes.ANGLE)) * distance};
    }

    public static double getDistance(Component component) {
        return (System.currentTimeMillis() - lastTick) * 1000 * component.getAttribute(Attributes.SPEED);
    }

    public static Tiles getTile(Position p) {
        return Tiles.valueOf(worldState.getMap()[(int) p.getX() / tileSize][(int) p.getY() / tileSize]);
    }

    public static int[] getTileIndex(Position p) {
        int[] index = {(int) p.getX() / tileSize, (int) p.getY() / tileSize};
        return index;
    }
}
