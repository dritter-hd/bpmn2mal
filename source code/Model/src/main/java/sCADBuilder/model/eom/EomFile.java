package sCADBuilder.model.eom;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class EomFile {
    private Header header = new Header();
    private Root root = new Root();
    private List<Asset> objects = new LinkedList<>();
    private List<Association> associations = new LinkedList<>();
    private List<DefenseDefaultValue> defenses = new LinkedList<>();

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    public List<Asset> getObjects() {
        return objects;
    }

    public void addObject(Asset object) {
        this.objects.add(object);
    }

    public List<Association> getAssociations() {
        return associations;
    }

    public void addAssociation(Association association) {
        this.associations.add(association);
    }

    public List<DefenseDefaultValue> getDefenses() {
        return defenses;
    }

    public void addDefense(DefenseDefaultValue defense) {
        this.defenses.add(defense);
    }

    public Asset getAsset(String metaConcept) {
        for(int i=0;i<objects.size();i++) {
            if(objects.get(i).getName().equals(metaConcept))
                return objects.get(i);
        }
        throw new NoSuchElementException();
    }

    public boolean assetExists(String metaConcept) {
        try {
            getAsset(metaConcept);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        //Header
        sb.append(header.toString());
        //root
        sb.append(root.toString());

        //objects == assets
        for (Asset a: getObjects()) {
            if(!a.isAbstract())
                sb.append(a.toString());
        }

        //associations
        for (Association a: getAssociations()){
            sb.append(a.toString());
        }

        //defenseDefaultConfigurations == defenses
        for(DefenseDefaultValue d: getDefenses()) {
            sb.append(d.toString());
        }

        //close root
        sb.append("</com.foreseeti.kernalCAD:XMIObjectModel>"+System.lineSeparator());

        return sb.toString();
    }
}
