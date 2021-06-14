package sCADBuilder.model.cmx;

public class ViewConnection {
    private int id;
    private int sourceId;
    private int targetId;
    private String sourceProperty;
    private String targetProperty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getSourceProperty() {
        return sourceProperty;
    }

    public void setSourceProperty(String sourceProperty) {
        this.sourceProperty = sourceProperty;
    }

    public String getTargetProperty() {
        return targetProperty;
    }

    public void setTargetProperty(String targetProperty) {
        this.targetProperty = targetProperty;
    }

    @Override
    public String toString() {
        return "<viewConnection id=\""+getId()+"\" sourceId=\""+getSourceId()+"\" targetId=\""+getTargetId()+
                "\" sourceProperty=\""+getSourceProperty()+"\" targetProperty=\""+getTargetProperty()+"\"/>"+
                System.lineSeparator();
    }
}
