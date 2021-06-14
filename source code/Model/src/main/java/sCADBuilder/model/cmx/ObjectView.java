package sCADBuilder.model.cmx;

public class ObjectView extends View {
    private int objectId;
    private String type = "com.foreseeti.securiCAD:ObjectView";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "<view xsi:type=\""+getType()+"\" name=\""+getName()+"\" editor=\""+getEditor()+
                "\" routingMethod=\""+getRoutingMethod()+"\" index=\""+getIndex()+"\" objectId=\""+getObjectId()+"\"/>"
                +System.lineSeparator();
    }
}
