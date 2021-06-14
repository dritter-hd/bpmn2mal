package sCADBuilder.model.eom;

import java.util.HashMap;

public class Existence {
    private String type;
    private HashMap<String,String> parameters = new HashMap();

    public Existence() {
        type = "FixedBoolean";
        parameters.put("fixed","1.0");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<existence type=\""+type+"\">"+System.lineSeparator());

        for (String p: parameters.keySet()) {
            sb.append("<parameters name=\""+p+"\" value=\""+parameters.get(p)+"\"/>"+System.lineSeparator());
        }

        sb.append("</existence>"+System.lineSeparator());

        return sb.toString();
    }
}
