package Logic;

public class Component {
    private double[] attributes = new double[32];
    private int id = -1;
    private Component[] subComponents = {};
    private Position position = null;

    public double getAttribute(Attributes a) {
        return attributes[a.valueOf()];
    }

    public double set(Attributes a, double value) {
        attributes[a.valueOf()] = value;
        return value;
    }

    public Component() {}
    
    public Component(Component other) {
        for (int i=0; i<32; i++) {
            attributes[i] = other.attributes[i];
        }
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
