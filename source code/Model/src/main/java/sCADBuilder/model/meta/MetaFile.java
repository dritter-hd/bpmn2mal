package sCADBuilder.model.meta;

public class MetaFile {
    private String scadVersion = "1.0.0";
    private String langVersion;
    private String langId;
    private String malVersion = "0.1.0-SNAPSHOT";
    private String info = "Created with sCAD file builder";

    public String getScadVersion() {
        return scadVersion;
    }

    public void setScadVersion(String scadVersion) {
        this.scadVersion = scadVersion;
    }

    public String getLangVersion() {
        return langVersion;
    }

    public void setLangVersion(String langVersion) {
        this.langVersion = langVersion;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getMalVersion() {
        return malVersion;
    }

    public void setMalVersion(String malVersion) {
        this.malVersion = malVersion;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("{"+System.lineSeparator());

        sb.append("\"scadVersion\": \""+getScadVersion()+"\"," + System.lineSeparator());
        sb.append("\"langVersion\": \""+getLangVersion()+"\"," + System.lineSeparator());
        sb.append("\"langID\": \""+getLangId()+"\"," + System.lineSeparator());
        sb.append("\"malVersion\": \""+getMalVersion()+"\"," + System.lineSeparator());
        sb.append("\"info\": \""+getInfo()+"\"" + System.lineSeparator());

        sb.append("}"+System.lineSeparator());

        return sb.toString();
    }
}
