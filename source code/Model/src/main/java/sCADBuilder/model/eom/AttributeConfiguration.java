package sCADBuilder.model.eom;

public class AttributeConfiguration {
    private String metaConcept;
    private String type = "FixedBoolean";
    private String name = "fixed";

    public AttributeConfiguration(String metaConcept) {
        this.metaConcept = metaConcept;
    }

    public void setMetaConcept(String metaConcept) {
        this.metaConcept = metaConcept;
    }

    public String getMetaConcept() {
        return metaConcept;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<attributeConfigurations metaConcept=\""+getMetaConcept()+"\">"+System.lineSeparator());
        sb.append("<defaultValue type=\""+getType()+"\">"+System.lineSeparator());
        sb.append("<parameters name=\""+getName()+"\"/>"+System.lineSeparator());
        sb.append("</defaultValue>"+System.lineSeparator());
        sb.append("</attributeConfigurations>"+System.lineSeparator());

        return sb.toString();
    }
}
