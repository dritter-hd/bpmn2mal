package sCADBuilder.model.eom;

import java.util.LinkedList;
import java.util.List;

public class DefenseDefaultValue {
    private String metaConcept;
    private List<AttributeConfiguration> attributes = new LinkedList<>();

    public void addDefense(String name) {
        attributes.add(new AttributeConfiguration(name));
    }

    public void setMetaConcept(String metaConcept) {
        this.metaConcept = metaConcept;
    }

    public String getMetaConcept() {
        return metaConcept;
    }

    public List<AttributeConfiguration> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<defenseDefaultValueConfigurations metaConcept=\""+getMetaConcept()+"\">"+System.lineSeparator());

        for(AttributeConfiguration a: getAttributes()) {
            sb.append(a.toString());
        }

        sb.append("</defenseDefaultValueConfigurations>"+System.lineSeparator());

        return sb.toString();
    }
}
