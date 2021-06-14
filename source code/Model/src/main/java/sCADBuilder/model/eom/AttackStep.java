package sCADBuilder.model.eom;

public class AttackStep {
    private String metaConcept;
    private int consequence;
    private boolean consequenceSet=false;
    private String description;
    private boolean descriptionSet=false;
    private double lowerBoundary;
    private double upperBoundary;
    private boolean boundariesSet=false;
    private Distribution distribution;
    private LocalTtc localTtc;
    private boolean localTtcSet=false;

    public void setBoundaries(double lowerBoundary, double upperBoundary) {
        this.boundariesSet = true;
        this.lowerBoundary = lowerBoundary;
        this.upperBoundary = upperBoundary;
    }

    public void setDescription(String description) {
        this.descriptionSet = true;
        this.description = description;
    }

    public void setLocalTtc(LocalTtc localTtc) {
        this.localTtcSet = true;
        this.localTtc = localTtc;
    }

    public String getDescription() {
        return description;
    }

    public double getLowerBoundary() {
        return lowerBoundary;
    }

    public double getUpperBoundary() {
        return upperBoundary;
    }

    public LocalTtc getLocalTtc() {
        return localTtc;
    }

    public String getMetaConcept() {
        return metaConcept;
    }

    public void setMetaConcept(String metaConcept) {
        this.metaConcept = metaConcept;
    }

    public int getConsequence() {
        return consequence;
    }

    public void setConsequence(int consequence) {
        this.consequence = consequence;
        consequenceSet = true;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<evidenceAttributes metaConcept=\""+getMetaConcept()+ "\"" +
                (consequenceSet?" consequence=\""+consequence+"\"":"") +
                (descriptionSet?" description=\""+description+"\"":"") +
                (boundariesSet?" costLowerLimit=\""+lowerBoundary+"\" costUpperLimit=\""+upperBoundary+"\"\"":"")
                );
        if(distribution==null&&!localTtcSet) { //no inner content
            sb.append("/>"+ System.lineSeparator());
        } else {
            sb.append(">"+ System.lineSeparator());
            if (distribution != null)//distribution given
                sb.append(distribution.toString());
            if (localTtcSet) //local ttc given
                sb.append(localTtc.toString());

            //close element
            sb.append("</evidenceAttributes>"+System.lineSeparator());
        }

        return sb.toString();
    }
}
