package sCADBuilder.model.mal;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class MalSpecification {
    private List<Asset> assets =new LinkedList<>();
    private List<Association> associations=new LinkedList<>();

    public List<Asset> getAssets() {
        return assets;
    }

    public List<Association> getAssociations() {
        return associations;
    }

    public void addAsset(Asset asset) {
        assets.add(asset);
    }

    public void addAssociation(Association association) {
        associations.add(association);
    }

    public Asset getAsset(String identifier) {
        for(int i=0;i<assets.size();i++) {
            if(assets.get(i).getName().equals(identifier))
                return assets.get(i);
        }
        throw new NoSuchElementException();
    }

    public Association getAssociation(String source, String target) {
        for(int i=0;i<associations.size();i++) {
            if(isInInheritanceHierarchy(associations.get(i).getSourceObject(),source) &&
                    isInInheritanceHierarchy(associations.get(i).getTargetObject(),target))
                return associations.get(i);
        }

        //The direction of the association needs to be correct. Otherwise, securiCAD will not load the file

        throw new NoSuchElementException();
    }

    private boolean isInInheritanceHierarchy(String reference, String input) {
        if(input.equals(reference))
            return true;
        Asset asset = getAsset(input);
        if(asset.hasParent())
            return isInInheritanceHierarchy(reference,asset.getParent());
        return false;
    }
}
