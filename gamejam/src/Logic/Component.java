package Logic;

public class Component {
    private double[] attributes = new double[32];
    private int id = -1;
    private Component[] subComponents = null;
    private Position position = null;

    public double getAttribute(Attributes a) {
        return attributes[a.valueOf()];
    }

    public void set(Attributes a, double value) {
        attributes[a.valueOf()] = value;
    }

    // #################################################################################################################

    public double[] getAttributes() {
        return attributes;
    }

    public void setAttributes(double[] attributes) {
        this.attributes = attributes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Component[] getSubComponents() {
        return subComponents;
    }

    public void setSubComponents(Component[] subComponents) {
        this.subComponents = subComponents;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
