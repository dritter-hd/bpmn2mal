package sCADBuilder.input;

import sCADBuilder.model.mapping.ProcessMalMapping;
import com.thoughtworks.xstream.XStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MappingParser {
    public static ProcessMalMapping parseMapping(File file) {
        XStream stream = new XStream();

        ProcessMalMapping mapping = (ProcessMalMapping) stream.fromXML(file);

        return mapping;
    }

    public static void writeMapping(File file, ProcessMalMapping mapping) throws IOException {
        XStream stream = new XStream();
        stream.toXML(mapping,new FileWriter(file));
    }
}
