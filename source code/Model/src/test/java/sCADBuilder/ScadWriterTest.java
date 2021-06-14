package sCADBuilder;

import sCADBuilder.ScadFactory;
import sCADBuilder.output.ScadWriter;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mal_lang.compiler.lib.CompilerException;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScadWriterTest {
    private static Graph process;
    private static ScadFactory scadFactory;
    private static File resourcesDirectory = new File("src/resources/sCADBuilder");
    private static File mapping = new File(resourcesDirectory.getAbsolutePath()+File.separator +"mapping.xml");
    private static File coreLang = new File(resourcesDirectory.getAbsolutePath()+File.separator +"coreLang.mal");

    @BeforeEach
    public void reset() {
        process = new DefaultGraph("Process");

        //Nodes
        process.addNode("A");
        process.getNode("A").setAttribute("server","DataObject");
        process.getNode("A").setAttribute("name","DO1");
        process.getNode("A").setAttribute("id",1);
        process.addNode("B");
        process.getNode("B").setAttribute("server","Process");
        process.getNode("B").setAttribute("name","P1");
        process.getNode("B").setAttribute("id",2);

        //Add vulnerability to A (success rate is in vulnerability, additionally the severance, TTC in exploit)
        process.addNode("B_v1");
        process.getNode("B_v1").setAttribute("server","Vuln_Low");
        process.getNode("B_v1").setAttribute("name","VO1");
        process.getNode("B_v1").setAttribute("id",4);
        process.getNode("B_v1").setAttribute("prob","Pareto");
        process.getNode("B_v1").setAttribute("prob_attr",4.0,3.0);
        process.getNode("B_v1").setAttribute("prob_step","impact");
        process.addEdge("BBv1","B_v1","B");
        process.getEdge("BBv1").setAttribute("id",7);
        process.addNode("B_e1");
        process.getNode("B_e1").setAttribute("server","Exploit");
        process.getNode("B_e1").setAttribute("name","EO1");
        process.getNode("B_e1").setAttribute("id",5);
        process.getNode("B_e1").setAttribute("prob","LogNormal");
        process.getNode("B_e1").setAttribute("prob_attr",5.0,1.0);
        process.getNode("B_e1").setAttribute("prob_step","attemptExploit");
        process.addEdge("Bv1Be1","B_v1","B_e1");
        process.getEdge("Bv1Be1").setAttribute("id",6);

        //Edges
        process.addEdge("AB","A","B");
        process.getEdge("AB").setAttribute("id",3);

        //Attacker
        process.addNode("Attacker");
        process.getNode("Attacker").setAttribute("2","fullAccess"); //give the id of the object and the attack step

        try {
            scadFactory = new ScadFactory(process, mapping, coreLang);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CompilerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWriting() {
        File scadFile = new File(resourcesDirectory.getAbsolutePath()+File.separator+"test.sCAD");
        try {
            ScadWriter.createScad(scadFile,"test",
                    scadFactory.getEomFile().toString(),scadFactory.getCmxFile().toString(),
                    scadFactory.getMetaFile().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertAll( () -> assertTrue(scadFile.exists()), () -> assertTrue(scadFile.length()>0));
    }
}
