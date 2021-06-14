package sCADBuilder.model.mal;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AttackStep {
    private String name;
    private HashMap<String,String> distributions = new HashMap<>();
    private List<String> annotations = new LinkedList<>();

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

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setAnnotation(String annotation) {
        this.annotations.add(annotation);
    }
}
