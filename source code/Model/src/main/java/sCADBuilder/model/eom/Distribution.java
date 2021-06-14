package sCADBuilder.model.eom;

import java.util.HashMap;

public class Distribution {
    private String type;
    private HashMap<String, String> parameters = new HashMap();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }

    public void addParameter(String key, String value) {
        this.parameters.put(key,value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<evidenceDistribution type=\""+getType()+"\">"+System.lineSeparator());

        for(String key: getParameters().keySet()) {
            sb.append("<parameters name=\""+key+"\" value=\""+getParameters().get(key) +"\"/>"+System.lineSeparator());
        }

        sb.append("</evidenceDistribution>"+System.lineSeparator());

        return sb.toString();
    }
}
