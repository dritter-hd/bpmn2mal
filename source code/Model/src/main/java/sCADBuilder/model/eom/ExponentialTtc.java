package sCADBuilder.model.eom;

public class ExponentialTtc extends LocalTtc {
    protected ExponentialTtc(double mean) {
        this.parameters.put("mean",mean);
        this.type = Type.EXPONENTIAL;
    }
}
