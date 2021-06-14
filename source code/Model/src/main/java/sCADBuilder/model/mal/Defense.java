package sCADBuilder.model.mal;

import java.util.HashMap;

public class Defense {
    private String name;
    private HashMap<String,String> distributions = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getDistributions() {
        return distributions;
    }

    public void setDistribution(String key, String value) {
        this.distributions.put(key,value);
    }
}
