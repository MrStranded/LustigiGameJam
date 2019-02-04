package Logic;

import Globals.MasterSwitch;
import Input.InputBuffer;
import Translater.Encoder;
import Translater.Sender;

import java.util.ArrayList;
import java.util.HashMap;

public class Logic {
    private static WorldState worldState;

    private static long lastTick;
    private static long tickDuration;

    private static StringBuilder changes;

    private static CosSinLookup cosSinLookup;


    // --------------------------------------------- INITIALIZE --------------------------------------------------------
    public static void init(WorldState ws) {
        worldState = ws;
        cosSinLookup = CosSinLookup.getTable();
        Tiles.initTiles();
    }


    // --------------------------------------------- ITERATE -----------------------------------------------------------
    public static void locigIteration(WorldState ws) {
        tickDuration = System.currentTimeMillis() - lastTick;

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

    private static void processInput() {
        ArrayList<Component> accelerators = new ArrayList<>();
        boolean sailUp;

        if (MasterSwitch.isServer) {
            HashMap<Integer, Integer[]> controlsAll = worldState.getControls();
            controlsAll.put(worldState.userId, InputBuffer.getControls());

            for (Player p : worldState.getPlayers()) {
                Integer[] controls = controlsAll.get(p.getId());
                Component ship = getShipById(p.getId());

                assert ship != null;

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

                if (controls[Actions.SHOOTLEFTMOUSE.valueOf()] == 1) {
                }

                // ---------------------------  TOGGLE SAIL  ---------------------------------------------
                if (controls[Actions.TOGGLESAIL.valueOf()] == 1) {
                    sailUp = toggleSails(ship);
                } else {
                    sailUp = ship.getAttribute(Attributes.SAIL) == 1;
                }

                // --------------------------- ACCELERATE -------------------------------------------
                processAcceleration(accelerators, sailUp, ship);

                accelerate(ship, accelerators, controls[Actions.MOVEUPDOWN.valueOf()]);

                // -------------------------- STEER -----------------------------------------------------
                steer(ship, controls[Actions.MOVELEFTRIGHT.valueOf()]);

                move(ship);
                fly();

                changes.append(Encoder.createAttributesMsg(ship));
            }
        } else {
            Integer[] controls = InputBuffer.getControls();
            Component ship = getShipById(worldState.getUserId());

            assert ship != null;

            // ---------------------------  TOGGLE SAIL  ---------------------------------------------
            if (controls[Actions.TOGGLESAIL.valueOf()] == 1) {
                sailUp = toggleSails(ship);
            } else {
                sailUp = ship.getAttribute(Attributes.SAIL) == 1;
            }

            // --------------------------- ACCELERATION -------------------------------------------
            processAcceleration(accelerators, sailUp, ship);

            accelerate(ship, accelerators, controls[Actions.MOVEUPDOWN.valueOf()]);

            // -------------------------- STEER -----------------------------------------------------
            steer(ship, controls[Actions.MOVELEFTRIGHT.valueOf()]);

            move(ship);
            fly();
        }
    }

    // ----------------------------------------- LOGIC -----------------------------------------------------------------

    private static void move(Component component) {
        Position inputVector = getVector(component);
        Position p = component.getPosition();
        boolean collision = false;

        int[] index = getTileIndex(p);

        Tiles xTile = getTile(new Position(p.getX() + inputVector.getX(), p.getY()));
        Tiles yTile = getTile(new Position(p.getX(), p.getY() + inputVector.getY()));

        double xBorder = ((double) index[0] + (inputVector.getX() >= 0 ? 1 : 0));
        double yBorder = ((double) index[1] + (inputVector.getY() >= 0 ? 1 : 0));

        if (xTile == null || xTile.isCollision()) {
            collision = true;
            component.getPosition().setX(xBorder);
        }

        if (yTile == null || yTile.isCollision()) {
            collision = true;
            component.getPosition().setY(yBorder);
        }

        if (MasterSwitch.isServer) {
            changes.append(Encoder.createPositionMsg(component));
        }
        if (!collision) {
            component.setPosition(new Position(p.getX() + inputVector.getX(), p.getY() + inputVector.getY()));
        } else {
            System.out.println(xTile);
            System.out.println(yTile);
            System.out.println("---------------");
        }
    }

    private static void shoot(Component ship, Slotpositions position) {
        for (Component c : ship.getSubComponents()) {
            if (c.getAttribute(Attributes.SLOTPOSITION) == position.valueOf()) {
                Component projectile = new Component();
                projectile.set(Attributes.CATEGORY, Categories.PROJECTILE.valueOf());
                projectile.set(Attributes.HEALTH, 1);
                projectile.set(Attributes.SPEED, c.getAttribute(Attributes.SPEED));
                double angle = projectile.set(Attributes.ANGLE, ship.getAttribute(Attributes.ANGLE) + c.getAttribute(Attributes.ANGLE));

                projectile.setPosition(Position.add(getVector(1, angle), Position.add(ship.getPosition(), c.getPosition())));

                if (MasterSwitch.isServer) {
                    worldState.addUnit(projectile);
                    changes.append(Encoder.createFullComponentMsg(c));
                }
            }
        }
    }

    private static void fly() {
        for (Component projectile : getProjectiles()) {
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
                    if (MasterSwitch.isServer) {
                        changes.append(Encoder.createPositionMsg(projectile));
                    }
                }
            }
        }
    }

    private static void hit(Component target, Component projectile) {
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

        if (MasterSwitch.isServer) {
            changes.append(Encoder.createAttributesMsg(target));
            changes.append(Encoder.createAttributesMsg(projectile));

            worldState.removeUnit(target);
            worldState.removeUnit(projectile);
        }
    }

    private static boolean toggleSails(Component ship) {
        int x = ship.getAttribute(Attributes.SAIL) == 0 ? 1 : 0;
        ship.set(Attributes.SAIL, x);

        return x == 1;
    }

    private static void steer(Component ship, int input) {
        double angle = ship.getAttribute(Attributes.ANGLE) - (ship.getAttribute(Attributes.TURNANGLE) * input);

        if (angle < 0) {
            angle += 360;
        }

        ship.set(Attributes.ANGLE, angle);
    }

    private static void processAcceleration(ArrayList<Component> accelerators, boolean sailUp, Component ship) {
        for (Component c : ship.getSubComponents()) {
            if (c.getAttribute(Attributes.CATEGORY) == Categories.SAIL.valueOf() && sailUp) {
                accelerators.add(c);
            }

            if (c.getAttribute(Attributes.CATEGORY) == Categories.MOTOR.valueOf()) {
                accelerators.add(c);
            }
        }
    }

    private static void accelerate(Component ship, ArrayList<Component> accelerators, int input) {
        double speed = ship.getAttribute(Attributes.SPEED);

        speed += ship.getAttribute(Attributes.ACCELERATION) * tickDuration * input;

        for (Component c : accelerators) {
            if (c.getAttribute(Attributes.CATEGORY) == Categories.SAIL.valueOf()) {
                speed += c.getAttribute(Attributes.ACCELERATION) * tickDuration;
            } else {
                speed += c.getAttribute(Attributes.ACCELERATION) * tickDuration * input;
            }
        }

        if (speed < (-ship.getAttribute(Attributes.MAXSPEED) * 0.6)) {
            speed = -ship.getAttribute(Attributes.MAXSPEED) * 0.6;
        } else if (speed > ship.getAttribute(Attributes.MAXSPEED)) {
            speed = ship.getAttribute(Attributes.MAXSPEED);
        }

        ship.set(Attributes.SPEED, speed);
    }

    private static void shootTurret(Component ship, Component turret) {

    }


    // ----------------------------------------- GET -------------------------------------------------------------------

    private static Position getRotatedPosition(Position position, double angle) {
        return new Position(cosSinLookup.getCos((int) angle) * position.getX() - cosSinLookup.getSine((int) angle) * position.getY(),
                cosSinLookup.getSine((int) angle) * position.getX() + cosSinLookup.getCos((int) angle) * position.getY());
    }

    private static ArrayList<Component> getNearestUnits(Component component) {
        ArrayList<Component> nearestUnits = new ArrayList<Component>();

        for (Component c : getShips()) {
            if (Position.squaredDistance(Position.subtract(c.getPosition(), component.getPosition())) < Math.pow(component.getAttribute(Attributes.LENGTH) + getDistance(component), 2)) {
                nearestUnits.add(c);
            }
        }
        return nearestUnits;
    }

    private static Position getVector(Component component) {
        double distance = getDistance(component);

        return new Position(cosSinLookup.getCos((int) component.getAttribute(Attributes.ANGLE)) * distance, cosSinLookup.getSine((int) component.getAttribute(Attributes.ANGLE)) * distance);
    }

    private static Position getVector(double distance, double angle) {
        return new Position(Math.cos(angle) * distance, Math.sin(angle) * distance);
    }

    private static double getDistance(Component component) {
        return (tickDuration / 1000.0) * component.getAttribute(Attributes.SPEED);
    }

    private static Tiles getTile(Position p) {
        int[][] map = worldState.getMap();
        return Tiles.valueOf(map[(int) p.getX()][(int) p.getY()]);
    }

    private static int[] getTileIndex(Position p) {
        return new int[]{(int) p.getX(), (int) p.getY()};

    }

    private static Position scaleVector(Position vector, double scalar) {
        return new Position(vector.getX() * scalar, vector.getY() * scalar);
    }

    private static Position addVector(Position vector1, Position vector2) {
        return new Position(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }

    private static ArrayList<Component> getShips() {
        ArrayList<Component> list = new ArrayList<>();

        for (Component c : worldState.getUnits()) {
            if (c.getAttribute(Attributes.CATEGORY) == Categories.SHIP.valueOf()) {
                list.add(c);
            }
        }
        return list;
    }

    private static ArrayList<Component> getProjectiles() {
        ArrayList<Component> list = new ArrayList<>();

        for (Component c : worldState.getUnits()) {
            if (c.getAttribute(Attributes.CATEGORY) == Categories.PROJECTILE.valueOf()) {
                list.add(c);
            }
        }
        return list;
    }

    private static Component getShipById(int playerId) {
        for (Component c : worldState.getUnits()) {
            if (c.getAttribute(Attributes.CATEGORY) == Categories.SHIP.valueOf() && c.getAttribute(Attributes.PLAYER) == playerId) {
                return c;
            }
        }
        System.err.println("No ship has been found for the player: " + playerId + "!!!");
        return null;
    }
}
