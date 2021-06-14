package sCADBuilder;

import sCADBuilder.input.MalParser;
import sCADBuilder.input.MappingParser;
import sCADBuilder.model.cmx.CmxFile;
import sCADBuilder.model.cmx.DefaultView;
import sCADBuilder.model.cmx.ViewConnection;
import sCADBuilder.model.cmx.ViewItem;
import sCADBuilder.model.eom.*;
import sCADBuilder.model.eom.EomFile;
import sCADBuilder.model.mal.AttackStep;
import sCADBuilder.model.mal.Defense;
import sCADBuilder.model.mal.MalSpecification;
import sCADBuilder.model.mapping.AttributeMapping;
import sCADBuilder.model.mapping.ProcessMalMapping;
import sCADBuilder.model.meta.MetaFile;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.mal_lang.compiler.lib.CompilerException;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class ScadFactory {
    private CmxFile cmxFile = new CmxFile();
    private EomFile eomFile = new EomFile();
    private MetaFile metaFile = new MetaFile();
    private ProcessMalMapping mapping;
    private Graph process;
    private MalSpecification spec;
    private DefaultView defaultView;
    //List of dummy parents that might be never appear in the final file
    private List<Asset> dummies = new LinkedList<>();
    private String TYPE;
    private String ID;
    private String NAME;
    private String PROB;
    private String PROB_ATTR;
    private String PROB_STEP;
    private String ATTACKER = "Attacker";

    public ScadFactory(Graph process, File mapping, File malSpec) throws IOException, CompilerException {
        this.mapping = MappingParser.parseMapping(mapping);
        this.spec = MalParser.readMalSpecification(malSpec);
        this.metaFile = MalParser.getMetaInformation(malSpec);
        this.process = process;

        //Create default cmx view
        defaultView = new DefaultView();
        cmxFile.addView(defaultView);

        TYPE = this.mapping.get(AttributeMapping.Attribute.TYPE);
        ID = this.mapping.get(AttributeMapping.Attribute.ID);
        NAME = this.mapping.get(AttributeMapping.Attribute.NAME);

        PROB = this.mapping.get(AttributeMapping.Attribute.PROBABILITY);
        PROB_ATTR = this.mapping.get(AttributeMapping.Attribute.PROBABILITY_ATTR);
        PROB_STEP = this.mapping.get(AttributeMapping.Attribute.PROBABILITY_STEP);

        init();
    }

    private void init() {
        //Iterate Nodes
        for (Node n: process) {
            if (n.getId().equals(ATTACKER)) {
                handleAttacker(n);
            } else {
                parseNode(n);
            }
        }

        //Take care for the dummies
        for (Asset a: dummies) {
            handleDummy(a);
        }

        //Iterate Edges
        for (int i=0;i<process.getEdgeCount();i++) {
            parseEdge(process.getEdge(i));
        }

        //Add defenses
        for(sCADBuilder.model.mal.Asset asset: spec.getAssets()) {
            addDefensesToEom(asset);
        }
    }

    private void handleAttacker(Node n) {
        int id = Integer.MIN_VALUE;

        //add the object
        Asset attacker = new Asset();
        attacker.setMetaConcept("Attacker");
        attacker.setId(id);
        attacker.setName("Attacker");
        attacker.setTemplate(false);

        sCADBuilder.model.eom.AttackStep attackStep = new sCADBuilder.model.eom.AttackStep();
        attackStep.setMetaConcept("EntryPoint");
        attacker.addAttackStep(attackStep);
        attacker.setExistence(new Existence());

        eomFile.addObject(attacker);
        addViewItemToCxm(id);

        //add the associations
        int edgeId=id;
        for(Object targetIdObject: n.attributeKeys().toArray()) {
            String targetId = targetIdObject.toString();
            int targetIdInt = Integer.parseInt(targetId);
            String attributeValue = n.getAttribute(targetId,String.class);
            attackerAssociation(id,++edgeId,targetIdInt,attributeValue);
        }
    }

    private void attackerAssociation(int id,int edgeId, int targetId, String targetStep) {
        //eom
        Association association = new Association();
        association.setId(edgeId);
        association.setTargetObject(targetId);
        association.setTargetProperty(targetStep+".attacker");
        association.setSourceObject(id);
        association.setSourceProperty("firstSteps");
        eomFile.addAssociation(association);

        //cmx
        ViewConnection vc = new ViewConnection();

        vc.setId(edgeId);
        vc.setTargetId(targetId);
        vc.setTargetProperty(targetStep+".attacker");
        vc.setSourceId(id);
        vc.setSourceProperty("firstSteps");

        defaultView.addConnection(vc);
    }

    private void handleDummy(Asset a) {
        Asset eomAsset = new Asset();
        sCADBuilder.model.mal.Asset malAsset = spec.getAsset(a.getMetaConcept());

        eomAsset.setAbstract(true);

        for(AttackStep as: malAsset.getAttackSteps()) {
            sCADBuilder.model.eom.AttackStep attackStep = new sCADBuilder.model.eom.AttackStep();
            attackStep.setMetaConcept(as.getName());
            eomAsset.addAttackStep(attackStep);
        }

        eomFile.addObject(eomAsset);
    }

    private void parseEdge(Edge edge) {
        int edgeId = edge.getAttribute(ID,Integer.class);
        int targetId = edge.getTargetNode().getAttribute(ID,Integer.class);
        int sourceId = edge.getSourceNode().getAttribute(ID,Integer.class);
        String sourceType = mapping.get(edge.getSourceNode().getAttribute(TYPE,String.class));
        String targetType = mapping.get(edge.getTargetNode().getAttribute(TYPE,String.class));

        sCADBuilder.model.mal.Association malAssoc = spec.getAssociation(sourceType, targetType);

        String sourceProperty = malAssoc.getSourceAttribute();
        String targetProperty = malAssoc.getTargetAttribute();
        //eom
        Association association = new Association();
        association.setId(edgeId);
        association.setTargetObject(targetId);
        association.setTargetProperty(targetProperty);
        association.setSourceObject(sourceId);
        association.setSourceProperty(sourceProperty);
        eomFile.addAssociation(association);

        //cmx
        ViewConnection vc = new ViewConnection();

        vc.setId(edgeId);
        vc.setTargetId(targetId);
        vc.setTargetProperty(targetProperty);
        vc.setSourceId(sourceId);
        vc.setSourceProperty(sourceProperty);

        defaultView.addConnection(vc);
    }

    private void parseNode(Node n) {
        int id = n.getAttribute(ID,Integer.class);
        String type = n.getAttribute(TYPE,String.class);
        String name = n.getAttribute(NAME,String.class);

        //add to eom
        //asset
        String malType = mapping.get(type);
        sCADBuilder.model.mal.Asset malAsset = spec.getAsset(malType);

        Asset eomAsset = mapAssetFromMalToEom(id, name, malType, malAsset);

        //add probability attributes if exist
        if(n.hasAttribute(PROB) && n.hasAttribute(PROB_ATTR)) {
            String prob_type = n.getAttribute(PROB,String.class);
            String prob_step = n.getAttribute(PROB_STEP,String.class);

            LocalTtc.Type prob_type_enum = LocalTtc.Type.NORMAL;
            for(LocalTtc.Type enumType: LocalTtc.Type.values()) {
                if(enumType.toString().equals(prob_type)) {
                    prob_type_enum = enumType;
                }
            }

            Object[] prob_attr_obj = n.getAttribute(PROB_ATTR,Object[].class);
            Double[] prob_attr = new Double[2];
            for (int i=0;i<prob_attr_obj.length;i++) {
                prob_attr[i] = (Double) prob_attr_obj[i];
            }
            LocalTtc prob = null;
            switch (prob_type_enum) {
                case NORMAL:
                    prob = LocalTtcFactory.getNormal(prob_attr[0],prob_attr[1]);
                    break;
                case GAMMA:
                    prob = LocalTtcFactory.getGamma(prob_attr[0],prob_attr[1]);
                    break;
                case PARETO:
                    prob = LocalTtcFactory.getPareto(prob_attr[0],prob_attr[1]);
                    break;
                case TRUNCATED:
                    prob = LocalTtcFactory.getTruncated(prob_attr[0],prob_attr[1]);
                    break;
                case EXPONENTIAL:
                    prob = LocalTtcFactory.getExponential(prob_attr[0]);
                    break;
            }
            //add the probability to the related attack step
            eomAsset.getAttackStep(prob_step).setLocalTtc(prob);
        }

        eomFile.addObject(eomAsset);

        //add to cxm
        addViewItemToCxm(id);
    }

    private Asset mapAssetFromMalToEom(int id, String name, String malType, sCADBuilder.model.mal.Asset malAsset) {
        Asset eomAsset;
        if(eomFile.assetExists(malType)) { //Check if asset already exists as dummy due to inheritance
            eomAsset = getDummy(malAsset.getParent());
            dummies.remove(eomAsset); // it won't be a dummy anymore
        } else {
            eomAsset = new Asset();
        }

        eomAsset.setName(name);
        eomAsset.setMetaConcept(malType);
        eomAsset.setId(id);

        //replicate information from MAL spec
        //attacks
        for(AttackStep as: malAsset.getAttackSteps()) {
            sCADBuilder.model.eom.AttackStep attackStep = new sCADBuilder.model.eom.AttackStep();
            attackStep.setMetaConcept(as.getName());
            eomAsset.addAttackStep(attackStep);
        }

        //inheritance
        if(malAsset.getParent()!= null) {
            Asset parent;
            //Check if parent is already in eom file
            if (eomFile.assetExists(malAsset.getParent())) {
                parent = eomFile.getAsset(malAsset.getParent());
            } else if (containsDummy(malAsset.getParent())) { // check if there is already a dummy in use
                parent = getDummy(malAsset.getParent());
            } else { // else create a dummy and fill the rest later
                parent = new Asset();
                parent.setMetaConcept(malAsset.getParent());
                dummies.add(parent);
            }

            eomAsset.setParent(parent);
        }
        return eomAsset;
    }

    private void addViewItemToCxm(int id) {
        ViewItem vi = new ViewItem();

        vi.setId(id);

        defaultView.addItem(vi);
    }

    private void addDefensesToEom(sCADBuilder.model.mal.Asset malAsset) {
        //ignore abstract assets
        if(!malAsset.getAnAbstract()) {
            //collect the defenses recursively through inheritance structure
            HashMap<String, Defense> defenses = new HashMap<>(); //use HashMap to make sure every defense appears once
            getDefenses(malAsset, defenses);
            //just include ddv if there are defenses or the parent has defenses
            if (defenses.size() > 0) {
                DefenseDefaultValue ddv = new DefenseDefaultValue();
                ddv.setMetaConcept(malAsset.getName());
                for (Defense d : defenses.values()) {
                    ddv.addDefense(d.getName());
                }
                eomFile.addDefense(ddv);
            }
        }
    }

    private void getDefenses(sCADBuilder.model.mal.Asset malAsset, HashMap<String, Defense> defenses) {
        for(Defense defense: malAsset.getDefenses()) {
            defenses.put(defense.getName(),defense);
        }
        if(malAsset.hasParent())
            getDefenses(spec.getAsset(malAsset.getParent()),defenses);
    }


    private boolean containsDummy(String metaConcept) {
        for(int i=0;i<dummies.size();i++) {
            if(dummies.get(i).getName().equals(metaConcept))
                return true;
        }
        return false;
    }

    private Asset getDummy(String metaConcept) {
        for(int i=0;i<dummies.size();i++) {
            if(dummies.get(i).getName().equals(metaConcept))
                return dummies.get(i);
        }
        throw new NoSuchElementException();
    }

    public void writeScadFile(File target) {

    }

    public CmxFile getCmxFile() {
        return cmxFile;
    }

    public EomFile getEomFile() {
        return eomFile;
    }

    public MetaFile getMetaFile() {
        return metaFile;
    }

}
