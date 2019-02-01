package Logic;


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

        //TODO: broadcast new projectiles
    }

    public void fly(Component projectile) {
        for (Component c : getNearestUnits(projectile)) {
            Position unitNew = getRotatedPosition(c.getPosition(), c.getAttribute(Attributes.ANGLE));
            Position projectileNew = getRotatedPosition(projectile.getPosition(), c.getAttribute(Attributes.ANGLE));

            int numberOfParts = (int) (getDistance(projectile) / (worldState.getTileSize() / 2)) + 1;
            double[] projectileVectorPart = scaleVector(getVector(projectile), 1.0 / numberOfParts);

            for (int i = 0; i < numberOfParts; i++) {
                Position p = Position.add(projectileNew, new Position(projectileVectorPart[0], projectileVectorPart[1]));

                if (p.getX() >= unitNew.getX() - c.getAttribute(Attributes.LENGTH) / 2
                        && p.getX() <= unitNew.getX() + c.getAttribute(Attributes.LENGTH) / 2
                        && p.getY() >= unitNew.getY() - c.getAttribute(Attributes.WIDTH) / 2
                        && p.getY() <= unitNew.getY() + c.getAttribute(Attributes.WIDTH) / 2) {
                    //TODO: broadcast collision
                    break;
                }
                projectileVectorPart = addVector(projectileVectorPart, projectileVectorPart);
            }
        }
    }

    public static Position getRotatedPosition(Position position, double angle) {
        return new Position(Math.cos(angle) * position.getX() - Math.sin(angle) * position.getY(), Math.sin(angle) * position.getX() + Math.sin(angle) * position.getY());
    }

    public static ArrayList<Component> getNearestUnits(Component component) {
        ArrayList<Component> nearestUnits = new ArrayList<Component>();

        for (Component c : worldState.getUnits()) {
            if (c.getAttribute(Attributes.CATEGORY) != Categories.SHIP.valueOf()) {
                continue;
            }
            if (Position.squaredDistance(Position.subtract(c.getPosition(), component.getPosition())) < Math.pow(component.getAttribute(Attributes.LENGTH) + getDistance(component), 2)) {
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
        return new int[]{(int) p.getX() / tileSize, (int) p.getY() / tileSize};
    }

    public static double[] scaleVector(double[] vector, double scalar) {
        return new double[]{vector[0] * scalar, vector[1] * scalar};
    }

    public static double[] addVector(double[] vector1, double[] vector2) {
        return new double[]{vector1[0] + vector2[0], vector1[1] + vector2[1]};
    }
}
