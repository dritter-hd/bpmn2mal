package sCADBuilder.model.eom;

public class Root {
    private String xmi_version="2.0";
    private String xmlns_xmi="http://www.omg.org/XMI";
    private String xmlns_kernalCAD =  "http:///com/foreseeti/ObjectModel.ecore";
    private String samplingMethod="FORWARD";
    private String integerUniformJumpRange="0";
    private String integerPrunedUniformJumpStep="0";
    private String warningThreshold="100";

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

    public String getXmlns_kernalCAD() {
        return xmlns_kernalCAD;
    }

    public void setXmlns_kernalCAD(String xmlns_kernalCAD) {
        this.xmlns_kernalCAD = xmlns_kernalCAD;
    }

    public String getSamplingMethod() {
        return samplingMethod;
    }

    public void setSamplingMethod(String samplingMethod) {
        this.samplingMethod = samplingMethod;
    }

    public String getIntegerUniformJumpRange() {
        return integerUniformJumpRange;
    }

    public void setIntegerUniformJumpRange(String integerUniformJumpRange) {
        this.integerUniformJumpRange = integerUniformJumpRange;
    }

    public String getIntegerPrunedUniformJumpStep() {
        return integerPrunedUniformJumpStep;
    }

    public void setIntegerPrunedUniformJumpStep(String integerPrunedUniformJumpStep) {
        this.integerPrunedUniformJumpStep = integerPrunedUniformJumpStep;
    }

    public String getWarningThreshold() {
        return warningThreshold;
    }

    public void setWarningThreshold(String warningThreshold) {
        this.warningThreshold = warningThreshold;
    }

    @Override
    public String toString() {
        return "<com.foreseeti.kernalCAD:XMIObjectModel xmi:version=\""+getXmi_version()+
                "\" xmlns:xmi=\""+getXmlns_xmi()+"\" " +
                "xmlns:com.foreseeti.kernalCAD=\""+getXmlns_kernalCAD()+
                "\" samplingMethod=\""+getSamplingMethod()+"\" " +
                "integerUniformJumpRange=\""+getIntegerUniformJumpRange()+
                "\" integerPrunedUniformJumpStep=\""+getIntegerPrunedUniformJumpStep()+
                "\" warningThreshold=\""+getWarningThreshold()+
                "\">"+System.lineSeparator();
    }
}
