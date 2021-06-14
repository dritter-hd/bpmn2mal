package sCADBuilder;

import sCADBuilder.input.MappingParser;
import sCADBuilder.model.mapping.AttributeMapping;
import sCADBuilder.model.mapping.ProcessMalMapping;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MappingParserTest {
    private File resourcesDirectory = new File("src/resources/sCADBuilder");
    private File mapping = new File(resourcesDirectory.getAbsolutePath()+File.separator +"mapping.xml");

    @Test
    public void testReadMapping() throws IOException {
        ProcessMalMapping processMalMapping = MappingParser.parseMapping(mapping);

        assertEquals(5,processMalMapping.size());
    }

    @Test
    public void testReadAttributes() throws IOException {
        ProcessMalMapping processMalMapping = MappingParser.parseMapping(mapping);

        assertEquals(6,processMalMapping.amountAttributes());
    }

    @Test //Test for writing an exemplary mapping.xml
    public void testWrite() {
        ProcessMalMapping processMalMapping = new ProcessMalMapping();
        processMalMapping.addMapping("DataObject","Data");
        processMalMapping.addMapping("Process","Application");
        processMalMapping.addMapping("Vuln_Low","ManualLowImpactVulnerability");
        processMalMapping.addMapping("Vuln_High","ManualHighImpactVulnerability");
        processMalMapping.addMapping("Exploit","ManualHighComplexityExploit");

        processMalMapping.addMapping(AttributeMapping.Attribute.NAME,"name");
        processMalMapping.addMapping(AttributeMapping.Attribute.ID,"id");
        processMalMapping.addMapping(AttributeMapping.Attribute.TYPE,"server");
        processMalMapping.addMapping(AttributeMapping.Attribute.PROBABILITY,"prob");
        processMalMapping.addMapping(AttributeMapping.Attribute.PROBABILITY_ATTR,"prob_attr");
        processMalMapping.addMapping(AttributeMapping.Attribute.PROBABILITY_STEP,"prob_step");

        try {
            MappingParser.writeMapping(mapping,processMalMapping);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
