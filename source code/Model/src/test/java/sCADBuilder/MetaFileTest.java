package sCADBuilder;

import sCADBuilder.model.meta.MetaFile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MetaFileTest {
    private static MetaFile testFile;

    @BeforeAll
    public static void init() {
        testFile = new MetaFile();
    }

    @Test
    public void testCreation() {
        String xml = testFile.toString();
        assertTrue(xml.length()>0);
    }
}
