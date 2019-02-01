package Logic;

import Globals.MasterSwitch;
import Input.InputBuffer;
import Translater.Encoder;
import Translater.Sender;

import java.util.ArrayList;
import java.util.HashMap;

public class Logic {
    private static WorldState worldState;
    private static int tileSize;

    private static long lastTick;

    private static StringBuilder changes;

    private static CosSinLookup cosSinLookup;


    // --------------------------------------------- INITIALIZE --------------------------------------------------------
    public static void init(WorldState ws) {
        worldState = ws;
        tileSize = worldState.getTileSize();
        cosSinLookup = CosSinLookup.getTable();
    }


    // --------------------------------------------- ITERATE -----------------------------------------------------------
    public static void locigIteration(WorldState ws) {
        worldState = ws;

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
        ArrayList<Component> accelerators = new ArrayList<>();
        boolean sailUp;

        if (MasterSwitch.isServer) {
            HashMap<Integer, Integer[]> controlsAll = worldState.getControls();

            for (Player p : worldState.getPlayers()) {
                Integer[] controls = controlsAll.get(p.getId());
                Component ship = getShipById(p.getId());

                // ---------------------------- SHOOT ----------------------------------------------------
                if (controls[Actions.SHOOTLEFT.valueOf()] == 1) {
                    shoot(ship, Slotpositions.LEFT);
                }

                if (controls[Actions.SHOOTRIGHT.valueOf()] == 1) {
                    shoot(ship, Slotpositions.LEFT);
                }

                if (controls[Actions.SHOOTUP.valueOf()] == 1) {
                    shoot(ship, Slotpositions.LEFT);
                }

                if (controls[Actions.SHOOTDOWN.valueOf()] == 1) {
                    shoot(ship, Slotpositions.LEFT);
                }

                // ---------------------------  TOGGLE SAIL  ---------------------------------------------
                if (controls[Actions.TOGGLESAIL.valueOf()] == 1) {
                    sailUp = toggleSails(ship);
                } else {
                    sailUp = ship.getAttribute(Attributes.SAIL) == 1;
                }

                // --------------------------- MOVE UP DOWN -------------------------------------------
                for (Component c : ship.getSubComponents()) {
                    if (c.getAttribute(Attributes.CATEGORY) == Categories.SAIL.valueOf() && sailUp) {
                        accelerators.add(c);
                    }

                    if (c.getAttribute(Attributes.CATEGORY) == Categories.MOTOR.valueOf()) {
                        accelerators.add(c);
                    }
                }

                accelerate(ship, accelerators, controls[Actions.MOVEUPDOWN.valueOf()]);

                // -------------------------- STEER -----------------------------------------------------
                steer(ship, controls[Actions.MOVELEFTRIGHT.valueOf()]);
            }
        } else {
            int[] controls = InputBuffer.getControls();
            Component ship = getShipById(worldState.getUserId());

            // ---------------------------  TOGGLE SAIL  ---------------------------------------------
            if (controls[Actions.TOGGLESAIL.valueOf()] == 1) {
                sailUp = toggleSails(ship);
            } else {
                sailUp = ship.getAttribute(Attributes.SAIL) == 1;
            }

            // --------------------------- MOVE UP DOWN -------------------------------------------
            for (Component c : ship.getSubComponents()) {
                if (c.getAttribute(Attributes.CATEGORY) == Categories.SAIL.valueOf() && sailUp) {
                    accelerators.add(c);
                }

                if (c.getAttribute(Attributes.CATEGORY) == Categories.MOTOR.valueOf()) {
                    accelerators.add(c);
                }
            }

            accelerate(ship, accelerators, controls[Actions.MOVEUPDOWN.valueOf()]);

            // -------------------------- STEER -----------------------------------------------------
            steer(ship, controls[Actions.MOVELEFTRIGHT.valueOf()]);
        }
    }

    // ----------------------------------------- LOGIC -----------------------------------------------------------------

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

    public static void shoot(Component ship, Slotpositions position) {
        for (Component c : ship.getSubComponents()) {
            if (c.getAttribute(Attributes.SLOTPOSITION) == position.valueOf()) {
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

    public static void accelerate(Component ship, ArrayList<Component> accelerators, int input) {
        double speed = ship.getAttribute(Attributes.SPEED);
        long t = (System.currentTimeMillis() - lastTick);

        speed += ship.getAttribute(Attributes.ACCELERATION) * t * input;

        for (Component c : accelerators) {
            if (c.getAttribute(Attributes.CATEGORY) == Categories.SAIL.valueOf()) {
                speed += c.getAttribute(Attributes.ACCELERATION) * t;
            } else {
                speed += c.getAttribute(Attributes.ACCELERATION) * t * input;
            }
        }

        if (speed < 0) {
            speed = 0;
        } else if (speed > ship.getAttribute(Attributes.MAXSPEED)) {
            speed = ship.getAttribute(Attributes.MAXSPEED);
        }

        ship.set(Attributes.SPEED, speed);

        if (MasterSwitch.isServer) {
            changes.append(Encoder.createAttributesMsg(ship));
        }
    }

    public static boolean toggleSails(Component ship) {
        int x = ship.getAttribute(Attributes.SAIL) == 0 ? 1 : 0;
        ship.set(Attributes.SAIL, x);

        return x == 1;
    }

    public static void steer(Component ship, int input) {
        ship.set(Attributes.ANGLE, ship.getAttribute(Attributes.ANGLE) + (ship.getAttribute(Attributes.TURNANGLE) * input));
    }


    // ----------------------------------------- GET -------------------------------------------------------------------

    public static Position getRotatedPosition(Position position, double angle) {
        return new Position(cosSinLookup.getCos((int) angle) * position.getX() - cosSinLookup.getSine((int) angle) * position.getY(),
                cosSinLookup.getSine((int) angle) * position.getX() + cosSinLookup.getCos((int) angle) * position.getY());
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

        return new Position(cosSinLookup.getCos((int) component.getAttribute(Attributes.ANGLE)) * distance, cosSinLookup.getSine((int) component.getAttribute(Attributes.ANGLE)) * distance);
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

    public static Component getShipById(int playerId) {
        for (Component c : worldState.getUnits()) {
            if (c.getAttribute(Attributes.CATEGORY) == Categories.SHIP.valueOf() && c.getAttribute(Attributes.PLAYER) == playerId) {
                return c;
            }
        }
        System.err.println("No ship has been found for the player: " + playerId + "!!!");
        return null;
    }
}
