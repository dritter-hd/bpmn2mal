package sCADBuilder.model.eom;

public class GammaTtc extends LocalTtc{
    protected GammaTtc(double shape, double scale) {
        this.parameters.put("shape",shape);
        this.parameters.put("scale",scale);
        this.type = Type.GAMMA;
    }
}
