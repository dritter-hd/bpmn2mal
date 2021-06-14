package sCADBuilder.model.eom;

public class TruncatedTtc extends LocalTtc{
    protected TruncatedTtc(double mean, double sd) {
        this.parameters.put("mean",mean);
        this.parameters.put("sd",sd);
        this.type=Type.TRUNCATED;
    }
}
