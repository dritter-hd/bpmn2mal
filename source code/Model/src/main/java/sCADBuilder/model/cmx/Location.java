package sCADBuilder.model.cmx;

public class Location {
    private int x;
    private int y;
    private int width = -1;
    private int height = -1;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "<location x=\""+getX()+"\" y=\""+getY()+"\" width=\""+getWidth()+"\" height=\""+getHeight()+"\"/>"+
                System.lineSeparator();
    }
}
