package sCADBuilder.model.eom;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Asset {
    private List<AttackStep> attackSteps = new LinkedList();
    private String metaConcept = "";
    private String description = "";
    private int id;
    private int exportedId;
    private boolean template;
    private Asset parent;
    private Existence existence = new Existence();
    private String name = "";
    private boolean _abstract;

    public boolean isAbstract() {
        return _abstract;
    }

    public void setAbstract(boolean _abstract) {
        this._abstract = _abstract;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Existence getExistence() {
        return existence;
    }

    public void setExistence(Existence existence) {
        this.existence = existence;
    }

    public Asset getParent() {
        return parent;
    }

    public void setParent(Asset parent) {
        this.parent = parent;
    }

    public String getMetaConcept() {
        return metaConcept;
    }

    public void setMetaConcept(String metaConcept) {
        this.metaConcept = metaConcept;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExportedId() {
        return exportedId;
    }

    public void setExportedId(int exportedId) {
        this.exportedId = exportedId;
    }

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public List<AttackStep> getAttackSteps() {
        if( parent == null)
            return attackSteps;

        //If inheritance -> inherit parent attack steps
        List<AttackStep> combinedList = new LinkedList<>();
        combinedList.addAll(parent.attackSteps);
        combinedList.addAll(this.attackSteps);
        return combinedList;
    }

    public void addAttackStep(AttackStep attackStep) {
        this.attackSteps.add(attackStep);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //root
        sb.append("<objects description=\"").append(getDescription()).append("\" id=\"").append(getId()).append("\" name=\"").append(getName()).append("\" metaConcept=\"").append(getMetaConcept()).append("\" template=\"").append(isTemplate()).append("\" exportedId=\"").append(getExportedId()).append("\">").append(System.lineSeparator());

        //evidenceAttributes == attack steps
        for(AttackStep a: getAttackSteps()) {
            sb.append(a.toString());
        }

        //existence
        sb.append(getExistence().toString());

        //close root
        sb.append("</objects>").append(System.lineSeparator());

        return sb.toString();
    }

    public AttackStep getAttackStep(String prob_step) {
        for(AttackStep step: attackSteps) {
            if(step.getMetaConcept().equalsIgnoreCase(prob_step)) {
                return step;
            }
        }
        throw new NoSuchElementException();
    }
}
