package sCADBuilder.output;

import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ScadWriter {
    public static void createScad(File target, String name, String eom, String cmx, String meta) throws IOException {
        Path temp = Files.createTempDirectory("scad_temp");

        writeFile(temp, eom, name, "eom");
        writeFile(temp, cmx, name, "cmxCanvas");
        writeFile(temp, meta, "meta", "json");

        zipFiles(temp, target);
    }

    private static void zipFiles(Path temp, File target) {
        ZipUtil.pack(temp.toFile(),target);
    }

    private static void writeFile(Path temp, String content, String name, String extension) throws IOException {
        FileWriter writer = new FileWriter(temp.toString()+File.separator+name+"."+extension);
        writer.write(content);
        writer.close();
    }
}
