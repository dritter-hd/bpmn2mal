package sCADBuilder.model.eom;

public class ParetoTtc extends LocalTtc {
    protected ParetoTtc(double shape, double scale) {
        this.parameters.put("shape",shape);
        this.parameters.put("scale",scale);
        this.type = Type.PARETO;
    }
}
