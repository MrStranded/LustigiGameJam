package Logic;


public class Position {

    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + " | " + y + ")";
    }

    public static Position add(Position p1, Position p2) {
        return new Position(p1.getX() + p2.getX(), p1.getY() + p2.getY());
    }

    public static Position subtract(Position p1, Position p2) {
        return new Position(p1.getX() - p2.getX(), p1.getY() - p2.getY());
    }

    public static double squaredDistance(Position p1) {
        return Math.pow(p1.getX(),2) + Math.pow(p1.getY(), 2);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
