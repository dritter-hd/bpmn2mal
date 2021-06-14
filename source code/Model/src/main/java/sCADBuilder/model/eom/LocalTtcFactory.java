package sCADBuilder.model.eom;

public class LocalTtcFactory {
    public static ExponentialTtc getExponential(double mean) {
        return new ExponentialTtc(mean);
    }

    public static TruncatedTtc getTruncated(double mean, double sd) {
        return new TruncatedTtc(mean,sd);
    }

    public static GammaTtc getGamma(double shape, double scale) {
        return new GammaTtc(shape,scale);
    }

    public static NormalTtc getNormal(double shape, double scale) {
        return new NormalTtc(shape,scale);
    }

    public static ParetoTtc getPareto(double shape, double scale) {
        return new ParetoTtc(shape,scale);
    }
}
