package sCADBuilder.model.eom;

public class NormalTtc extends LocalTtc{
    protected NormalTtc(double shape, double scale) {
        this.parameters.put("shape",shape);
        this.parameters.put("scale",scale);
        this.type = Type.NORMAL;
    }
}
