package sCADBuilder.model.cmx;

public class ViewItem {
    private String type = "com.foreseeti.securiCAD:ViewNode";
    private int id;
    private Location location = new Location();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<viewItem xsi:type=\""+getType()+"\" id=\""+getId()+"\">"+System.lineSeparator());
        sb.append(location.toString());
        sb.append("</viewItem>"+System.lineSeparator());

        return sb.toString();
    }
}
