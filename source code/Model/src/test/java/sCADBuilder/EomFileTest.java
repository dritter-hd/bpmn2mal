package sCADBuilder;

import sCADBuilder.model.eom.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class EomFileTest {
    private static EomFile testFile;

    @BeforeAll
    public static void firstInit() {
        testFile = new EomFile();
    }

    @BeforeEach
    public void init(){
        Asset asset = new Asset();
        asset.setName("FirstAsset");
        asset.setDescription("Fancy Description");
        asset.setMetaConcept("System");
        asset.setId(1);
        asset.setExportedId(2);
        asset.setTemplate(false);
        asset.setExistence(new Existence());
        AttackStep step = new AttackStep();
        step.setMetaConcept("Step1");
        asset.addAttackStep(step);
        testFile.addObject(asset);

        asset = new Asset();
        asset.setName("SecondAsset");
        asset.setDescription("Fancy Description");
        asset.setMetaConcept("System");
        asset.setId(3);
        asset.setExportedId(4);
        asset.setTemplate(false);
        asset.setExistence(new Existence());
        step = new AttackStep();
        step.setMetaConcept("Step1");
        asset.addAttackStep(step);
        testFile.addObject(asset);

        Association association = new Association();
        association.setDescription("Wuhu");
        association.setId(6);
        association.setSourceObject(1);
        association.setSourceProperty("first");
        association.setTargetObject(3);
        association.setTargetProperty("second");
        testFile.addAssociation(association);

        DefenseDefaultValue defense = new DefenseDefaultValue();
        defense.setMetaConcept("FirstAsset");
        defense.addDefense("defense");
        testFile.addDefense(defense);
    }

    @Test
    public void testCreation() {
        String xml = testFile.toString();
        assertTrue(xml.length()>0);
    }

    @Test
    public void testEmpty() {
        EomFile testFile = new EomFile();

        String xml = testFile.toString();
        assertTrue(xml.length()>0);
    }

    @Test
    public void testInheritance() {
        testFile.getObjects().get(0).setParent(testFile.getObjects().get(1));
        String xml = testFile.toString();
        assertAll(
                () -> assertTrue(xml.length()>0),
                () -> assertEquals(2, testFile.getObjects().get(0).getAttackSteps().size())
        );
    }
}
