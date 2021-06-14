package sCADBuilder.model.mapping;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class ProcessMalMapping {
    private List<Mapping> mapping = new LinkedList<>();
    private List<AttributeMapping> attributeMappings = new LinkedList<>();

    public void addMapping(String fromProcess, String toMal) {
        mapping.add(new Mapping(fromProcess,toMal));
    }

    public void addMapping(AttributeMapping.Attribute from, String to) {
        attributeMappings.add(new AttributeMapping(from,to));
    }

    public String get(String fromProcess) {
        for(Mapping map: mapping) {
            if(map.getFromProcess().equals(fromProcess))
                return map.getToMal();
        }
        throw new NoSuchElementException();
    }

    public String get(AttributeMapping.Attribute from) {
        for(AttributeMapping map: attributeMappings) {
            if(map.getFrom().equals(from)) {
                return map.getTo();
            }
        }
        throw new NoSuchElementException();
    }

    public int size() {
        return mapping.size();
    }

    public int amountAttributes() {
        return attributeMappings.size();
    }

}
