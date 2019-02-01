package Logic;

import Globals.MasterSwitch;
import Translater.Encoder;
import Translater.Sender;

import java.util.ArrayList;

public class Logic {
    private static WorldState worldState;
    private static int tileSize;

    private static long lastTick;

    private static StringBuilder changes;

    public static void init(WorldState ws) {
        worldState = ws;
        tileSize = worldState.getTileSize();
        changes = new StringBuilder();

        processInput();

        ArrayList<Component> ships = getShips();
        for (Component c : ships) {
//            if(c.getAttribute(Attributes.SPEED) > 0) {
//                move(c);
//            }
            move(c);
        }

        Sender.sendMessage(changes.toString());
        lastTick = System.currentTimeMillis();
    }

    public static void processInput() {

    }

    public static void move(Component component) {
        Position inputVector = getVector(component);
        Position p = component.getPosition();

        int[] index = getTileIndex(p);

        Tiles xTile = getTile(new Position(p.getX() + inputVector.getX(), p.getY()));
        Tiles yTile = getTile(new Position(p.getX(), p.getY() + inputVector.getY()));

        int xBorder = tileSize * (index[0] + (inputVector.getX() >= 0 ? 1 : 0));
        int yBorder = tileSize * (index[1] + (inputVector.getY() >= 0 ? 1 : 0));

        if (xTile.isCollision()) {
            component.getPosition().setX(Math.abs(xBorder - p.getX()));
        }

        if (yTile.isCollision()) {
            component.getPosition().setY(Math.abs(yBorder - p.getY()));
        }

        changes.append(Encoder.createPositionMsg(component));

    }

    public static void shoot(Component ship, Component[] components) {
        for (Component c : components) {
            Component projectile = new Component();
            projectile.set(Attributes.CATEGORY, Categories.PROJECTILE.valueOf());
            projectile.set(Attributes.HEALTH, 1);
            projectile.set(Attributes.SPEED, c.getAttribute(Attributes.SPEED));
            double angle = projectile.set(Attributes.ANGLE, ship.getAttribute(Attributes.ANGLE) + c.getAttribute(Attributes.ANGLE));

            projectile.setPosition(Position.add(getVector(1, angle), Position.add(ship.getPosition(), c.getPosition())));

            worldState.addUnit(projectile);
            changes.append(Encoder.createFullComponentMsg(c));
        }
    }

    public static void fly(Component projectile) {
        boolean hitSomething = false;

        for (Component c : getNearestUnits(projectile)) {
            Position unitNew = getRotatedPosition(c.getPosition(), c.getAttribute(Attributes.ANGLE));
            Position projectileNew = getRotatedPosition(projectile.getPosition(), c.getAttribute(Attributes.ANGLE));

            int numberOfParts = (int) (getDistance(projectile) / (worldState.getTileSize() / 2)) + 1;
            Position projectileVectorPart = scaleVector(getVector(projectile), 1.0 / numberOfParts);

            for (int i = 0; i < numberOfParts; i++) {
                Position p = Position.add(projectileNew, new Position(projectileVectorPart.getX(), projectileVectorPart.getY()));

                if (p.getX() >= unitNew.getX() - c.getAttribute(Attributes.LENGTH) / 2
                        && p.getX() <= unitNew.getX() + c.getAttribute(Attributes.LENGTH) / 2
                        && p.getY() >= unitNew.getY() - c.getAttribute(Attributes.WIDTH) / 2
                        && p.getY() <= unitNew.getY() + c.getAttribute(Attributes.WIDTH) / 2) {

                    hitSomething = true;
                    if (MasterSwitch.isServer) {
                        hit(c, projectile);
                    }
                    break;
                }
                projectileVectorPart = addVector(projectileVectorPart, projectileVectorPart);
            }
            if (!hitSomething) {
                projectile.setPosition(addVector(projectile.getPosition(), getVector(projectile)));
                changes.append(Encoder.createPositionMsg(projectile));
            }
        }
    }

    public static void hit(Component target, Component projectile) {
        double armor = target.getAttribute(Attributes.ARMOR);
        double health = target.getAttribute(Attributes.HEALTH);
        double damage = projectile.getAttribute(Attributes.DAMAGE);

        projectile.set(Attributes.HEALTH, 0);

        if (armor > 0) {
            armor = target.set(Attributes.ARMOR, armor - damage);
            if (armor < 0) {
                damage = Math.abs(armor);
                target.set(Attributes.ARMOR, 0);
            }
        }

        health = target.set(Attributes.HEALTH, health - damage);

        if (health < 0) {
            target.set(Attributes.HEALTH, 0);
        }

        changes.append(Encoder.createAttributesMsg(target));
        changes.append(Encoder.createAttributesMsg(projectile));

        worldState.removeUnit(target);
        worldState.removeUnit(projectile);
    }

//    public static void accelerate(Component component) {
//        double acceleration = 0;
//    }

    public static Position getRotatedPosition(Position position, double angle) {
        return new Position(Math.cos(angle) * position.getX() - Math.sin(angle) * position.getY(), Math.sin(angle) * position.getX() + Math.sin(angle) * position.getY());
    }

    public static ArrayList<Component> getNearestUnits(Component component) {
        ArrayList<Component> nearestUnits = new ArrayList<Component>();

        for (Component c : getShips()) {
            if (Position.squaredDistance(Position.subtract(c.getPosition(), component.getPosition())) < Math.pow(component.getAttribute(Attributes.LENGTH) + getDistance(component), 2)) {
                nearestUnits.add(c);
            }
        }
        return nearestUnits;
    }

    public static Position getVector(Component component) {
        double distance = getDistance(component);

        return new Position(Math.cos(component.getAttribute(Attributes.ANGLE)) * distance, Math.sin(component.getAttribute(Attributes.ANGLE)) * distance);
    }

    public static Position getVector(double distance, double angle) {
        return new Position(Math.cos(angle) * distance, Math.sin(angle) * distance);
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

    public static Position scaleVector(Position vector, double scalar) {
        return new Position(vector.getX() * scalar, vector.getY() * scalar);
    }

    public static Position addVector(Position vector1, Position vector2) {
        return new Position(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }

    public static ArrayList<Component> getShips() {
        ArrayList<Component> list = new ArrayList<>();

        for (Component c : worldState.getUnits()) {
            if (c.getAttribute(Attributes.CATEGORY) == Categories.SHIP.valueOf()) {
                list.add(c);
            }
        }
        return list;
    }

    public static ArrayList<Component> getProjectiles() {
        ArrayList<Component> list = new ArrayList<>();

        for (Component c : worldState.getUnits()) {
            if (c.getAttribute(Attributes.CATEGORY) == Categories.PROJECTILE.valueOf()) {
                list.add(c);
            }
        }
        return list;
    }
}
