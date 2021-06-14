package sCADBuilder;


import sCADBuilder.input.MalParser;
import sCADBuilder.model.mal.Asset;
import sCADBuilder.model.mal.MalSpecification;
import sCADBuilder.model.meta.MetaFile;

import org.junit.jupiter.api.Test;
import org.mal_lang.compiler.lib.CompilerException;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MalParserTest {
    private File resourcesDirectory = new File("src/test/resources");
    private File coreLang = new File(resourcesDirectory.getAbsolutePath()+File.separator +"coreLang.mal");

    @Test
    public void testLoadingAssets() {
        try {
            MalSpecification spec = MalParser.readMalSpecification(coreLang);

            assertEquals(77, spec.getAssets().size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CompilerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIgnoreHiddenSteps() {
        try {
            MalSpecification spec = MalParser.readMalSpecification(coreLang);
            Asset application = spec.getAsset("Application");

            assertEquals(17, application.getAttackSteps().size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CompilerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadingAssociations() {
        try {
            MalSpecification spec = MalParser.readMalSpecification(coreLang);

            assertEquals(46, spec.getAssociations().size());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CompilerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadingMeta() {
        try {
            MetaFile file = MalParser.getMetaInformation(coreLang);
            assertAll( () -> assertEquals("0.1.0", file.getLangVersion()),
                    () -> assertEquals("org.mal-lang.coreLang", file.getLangId()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CompilerException e) {
            e.printStackTrace();
        }
    }
}
