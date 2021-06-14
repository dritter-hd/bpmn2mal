package sCADBuilder;

import sCADBuilder.ScadFactory;
import sCADBuilder.input.MappingParser;
import sCADBuilder.model.eom.DefenseDefaultValue;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mal_lang.compiler.lib.CompilerException;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScadFactoryTest {
    private static Graph process;
    private static ScadFactory scadFactory;
    private static File resourcesDirectory = new File("src/resources/sCADBuilder");
    private static File mapping = new File(resourcesDirectory.getAbsolutePath()+File.separator +"mapping.xml");
    private static File coreLang = new File(resourcesDirectory.getAbsolutePath()+File.separator +"coreLang.mal");

    @BeforeAll
    public static void init() {

    }

    @BeforeEach
    public void reset() {
        process = new DefaultGraph("Process");
        process.addNode("A");
        process.getNode("A").setAttribute("server","DataObject");
        process.getNode("A").setAttribute("name","DO1");
        process.getNode("A").setAttribute("id",1);
        process.addNode("B");
        process.getNode("B").setAttribute("server","Process");
        process.getNode("B").setAttribute("name","P1");
        process.getNode("B").setAttribute("id",2);
        process.addEdge("AB","A","B");
        process.getEdge("AB").setAttribute("id",3);

        try {
            scadFactory = new ScadFactory(process, mapping, coreLang);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CompilerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void simpleTest() {
        assertAll( () -> assertEquals(3,scadFactory.getEomFile().getObjects().size()),
                () -> assertEquals(1, scadFactory.getCmxFile().getViews().size())
        );
    }

    @Test
    public void testEomGeneration() {
        assertTrue(scadFactory.getEomFile().toString().length()>0);
    }

    @Test
    public void testCmxGeneration() {
        assertTrue(scadFactory.getCmxFile().toString().length()>0);
    }

    @Test
    public void testAllDefensesIncluded() {
        assertEquals(32, scadFactory.getEomFile().getDefenses().size());
    }

    @Test
    public void testOverwritingDefensesByInheritance() {
        DefenseDefaultValue vulnerability = new DefenseDefaultValue();
        for(DefenseDefaultValue defenseDefaultValue: scadFactory.getEomFile().getDefenses()) {
            if(defenseDefaultValue.getMetaConcept().equals("NHLRVulnerability"))
                vulnerability = defenseDefaultValue;
        }
        assertEquals(2,vulnerability.getAttributes().size());
    }
}
