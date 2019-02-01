package Logic;

public class CosSinLookup {
    double[] cos = new double[361];
    double[] sin = new double[361];
    private static CosSinLookup table = new CosSinLookup();

    private CosSinLookup() {
        for (int i = 0; i <= 360; i++) {
            cos[i] = Math.cos(Math.toRadians(i));
            sin[i] = Math.sin(Math.toRadians(i));
        }
    }

    public double getSine(int angle) {
        int angleCircle = angle % 360;
        return sin[angleCircle];
    }

    public double getCos(int angle) {
        int angleCircle = angle % 360;
        return cos[angleCircle];
    }

    public static CosSinLookup getTable() {
        return table;
    }
}