package sCADBuilder.model.eom;

public class Association {
    private String description;
    private int sourceObject;
    private int targetObject;
    private int id;
    private String sourceProperty;
    private String targetProperty;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSourceObject() {
        return sourceObject;
    }

    public void setSourceObject(int sourceObject) {
        this.sourceObject = sourceObject;
    }

    public int getTargetObject() {
        return targetObject;
    }

    public void setTargetObject(int targetObject) {
        this.targetObject = targetObject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        StringBuilder sb = new StringBuilder();

        sb.append("<associations description=\""+getDescription()+"\" sourceObject=\""+getSourceObject()+
                "\" targetObject=\""+getTargetObject()+"\" id=\""+getId()+"\" sourceProperty=\""+getSourceProperty()+
                "\" targetProperty=\""+getTargetProperty()+"\"/>"+System.lineSeparator());

        return sb.toString();
    }
}
