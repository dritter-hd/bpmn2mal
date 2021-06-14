package sCADBuilder.model.cmx;

public class Root {
    private String xmi_version = "2.0";
    private String xmlns_xmi = "http://www.omg.org/XMI";
    private String xmlns_xsi = "http://www.w3.org/2001/XMLSchema-instance";
    private String securiCAD = "http:///com/foreseeti/ModelViews.ecore";

    public String getXmi_version() {
        return xmi_version;
    }

    public void setXmi_version(String xmi_version) {
        this.xmi_version = xmi_version;
    }

    public String getXmlns_xmi() {
        return xmlns_xmi;
    }

    public void setXmlns_xmi(String xmlns_xmi) {
        this.xmlns_xmi = xmlns_xmi;
    }

    public String getXmlns_xsi() {
        return xmlns_xsi;
    }

    public void setXmlns_xsi(String xmlns_xsi) {
        this.xmlns_xsi = xmlns_xsi;
    }

    public String getSecuriCAD() {
        return securiCAD;
    }

    public void setSecuriCAD(String securiCAD) {
        this.securiCAD = securiCAD;
    }

    @Override
    public String toString() {
        return "<com.foreseeti.securiCAD:ModelViews xmi:version=\""+getXmi_version()+
                "\" xmlns:xmi=\""+getXmlns_xmi()+"\" xmlns:xsi=\""+getXmlns_xsi()+
                "\" xmlns:com.foreseeti.securiCAD=\""+getSecuriCAD()+"\">"+System.lineSeparator();
    }
}
