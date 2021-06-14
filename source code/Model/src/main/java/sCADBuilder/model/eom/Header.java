package sCADBuilder.model.eom;

public class Header {
    private String version="1.0";
    private String encoding="utf-8";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    @Override
    public String toString() {
        return "<?xml version=\""+getVersion()+"\" encoding=\""+getEncoding()+"\"?>"+System.lineSeparator();
    }
}
