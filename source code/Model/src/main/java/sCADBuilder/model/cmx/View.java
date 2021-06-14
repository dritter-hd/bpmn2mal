package sCADBuilder.model.cmx;

public abstract class View {
    private String name;
    private String editor = "com.foreseeti.kernelcad.canvasui.editor.DefaultEditor";
    private String routingMethod = "SHORTEST";
    private int index = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getRoutingMethod() {
        return routingMethod;
    }

    public void setRoutingMethod(String routingMethod) {
        this.routingMethod = routingMethod;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
