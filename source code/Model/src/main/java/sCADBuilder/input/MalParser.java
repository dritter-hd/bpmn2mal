package sCADBuilder.input;

import sCADBuilder.model.mal.*;
import sCADBuilder.model.meta.MetaFile;
import org.apache.commons.lang3.StringUtils;
import org.mal_lang.compiler.lib.AST;
import org.mal_lang.compiler.lib.CompilerException;
import org.mal_lang.compiler.lib.Parser;

import java.io.File;
import java.io.IOException;

public class MalParser {
    /**
     * The method parses a MAL specification and extracts the assets and associations.
     * @param file That contains a MAL specification.
     * @throws IOException
     * @throws CompilerException
     */
    public static MalSpecification readMalSpecification(File file) throws IOException, CompilerException {
        MalSpecification spec = new MalSpecification();
        AST ast = Parser.parse(file);

        //read associations
        for (AST.Association source: ast.getAssociations()) {
            Association target = new Association();
            target.setName(source.linkName.id);
            target.setTargetAttribute(source.leftField.id);
            target.setTargetObject(source.rightAsset.id);
            target.setSourceAttribute(source.rightField.id);
            target.setSourceObject(source.leftAsset.id);

            spec.addAssociation(target);
        }

        //read assets
        for (AST.Category cat: ast.getCategories()) {
            for(AST.Asset source: cat.assets) {
                Asset target = new Asset();
                if(source.parent.isPresent())
                    target.setParent(source.parent.get().id);
                target.setName(source.name.id);
                target.setAbstract(source.isAbstract);

                //read attack steps and defenses
                for (AST.AttackStep source_attack: source.attackSteps) {
                    //we're not interested in hidden steps
                    if(!isHidden(source_attack)) {
                        //defenses
                        if (source_attack.type == AST.AttackStepType.DEFENSE) {
                            Defense target_defense = new Defense();
                            target_defense.setName(StringUtils.capitalize(source_attack.name.id));

                            //read distributions

                            target.addDefense(target_defense);
                        } else { //attack steps
                            AttackStep target_attack = new AttackStep();
                            target_attack.setName(StringUtils.capitalize(source_attack.name.id));

                            //read distributions
                            //if(source_attack.ttc.isPresent()) {
                            //    switch (source_attack.ttc.get().getClass().toString()) {
                            //
                            //    }
                            //}

                            target.addAttackStep(target_attack);
                        }
                    }
                }
                spec.addAsset(target);
            }
        }
        return spec;
    }

    private static boolean isHidden(AST.AttackStep source_attack) {
        for(AST.ID id: source_attack.tags) {
            if(id.id.equals("hidden"))
                return true;
        }
        return false;
    }

    public static MetaFile getMetaInformation(File malSpec) throws IOException, CompilerException {
        MetaFile metaFile = new MetaFile();
        AST ast = Parser.parse(malSpec);

        for(AST.Define define: ast.getDefines()) {
            switch (define.key.id) {
                case "id":
                    metaFile.setLangId(define.value);
                    break;
                case "version":
                    metaFile.setLangVersion(define.value);
                    break;
            }
        }

        return metaFile;
    }
}
