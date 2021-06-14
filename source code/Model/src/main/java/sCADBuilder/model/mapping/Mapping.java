package sCADBuilder.model.mapping;

public class Mapping {
    private String fromProcess;
    private String toMal;

    public String getFromProcess() {
        return fromProcess;
    }

    public String getToMal() {
        return toMal;
    }

    public Mapping(String fromProcess, String toMal) {
        this.fromProcess = fromProcess;
        this.toMal = toMal;
    }
}
