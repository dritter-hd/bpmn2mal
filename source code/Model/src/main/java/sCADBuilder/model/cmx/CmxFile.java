package sCADBuilder.model.cmx;

import java.util.LinkedList;
import java.util.List;

public class CmxFile {
    private Header header = new Header();
    private Root root = new Root();
    private List<View> views = new LinkedList<>();

    public List<View> getViews() {
        return views;
    }

    public void addView(View view) {
        views.add(view);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //Header
        sb.append(header.toString());

        //Root
        sb.append(root.toString());

        //views
        for (View v: views) {
            sb.append(v.toString());
        }

        //close root
        sb.append("</com.foreseeti.securiCAD:ModelViews>"+System.lineSeparator());

        return sb.toString();
    }
}
