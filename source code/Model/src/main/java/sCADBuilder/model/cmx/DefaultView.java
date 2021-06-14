package sCADBuilder.model.cmx;

import java.util.LinkedList;
import java.util.List;

public class DefaultView extends View{
    private double zoom = 0.6;
    private boolean guideLines = true;
    private boolean loadOnStart = false;
    private List<ViewItem> items = new LinkedList<>();
    private List<ViewConnection> connections = new LinkedList<>();

    public void addItem(ViewItem item) {
        items.add(item);
    }

    public void addConnection(ViewConnection connection) {
        connections.add(connection);
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void setGuideLines(boolean guideLines) {
        this.guideLines = guideLines;
    }

    public void setLoadOnStart(boolean loadOnStart) {
        this.loadOnStart = loadOnStart;
    }

    public double getZoom() {
        return zoom;
    }

    public boolean isGuideLines() {
        return guideLines;
    }

    public boolean isLoadOnStart() {
        return loadOnStart;
    }

    public List<ViewItem> getItems() {
        return items;
    }

    public List<ViewConnection> getConnections() {
        return connections;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<view name=\"View 1\" editor=\"com.foreseeti.kernelcad.canvasui.editor.DefaultEditor\" zoom=\"0.6\" guideLines=\"true\" loadOnStart=\"true\" routingMethod=\"SHORTEST\" index=\"-1\">"+System.lineSeparator());

        //items
        for (ViewItem item: items) {
            sb.append(item.toString());
        }

        //connections
        for (ViewConnection connection: connections) {
            sb.append(connection.toString());
        }

        sb.append("</view>"+System.lineSeparator());

        return sb.toString();
    }
}
