package sCADBuilder.model.mal;

import java.util.LinkedList;
import java.util.List;

public class Asset {
    private List<AttackStep> attackSteps = new LinkedList<>();
    private List<Defense> defenses = new LinkedList<>();
    private String parent;
    private String name;
    private boolean _abstract;

    public boolean hasParent() {
        return parent != null || "".equals(parent);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AttackStep> getAttackSteps() {
        return attackSteps;
    }

    public List<Defense> getDefenses() {
        return defenses;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void addAttackStep(AttackStep attackStep) {
        this.attackSteps.add(attackStep);
    }

    public void addDefense(Defense defense) {
        this.defenses.add(defense);
    }

    public void setAbstract(boolean _abstract) {
        this._abstract = _abstract;
    }

    public boolean getAnAbstract() {
        return _abstract;
    }
}
