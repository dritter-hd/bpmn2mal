package sCADBuilder.model.mapping;

public class AttributeMapping {
    private Attribute from;
    private String to;

    public AttributeMapping(Attribute from, String to) {
        this.from = from;
        this.to = to;
    }

    public Attribute getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public enum Attribute {
    ID, TYPE, NAME, PROBABILITY, PROBABILITY_ATTR, PROBABILITY_STEP
    }
}
