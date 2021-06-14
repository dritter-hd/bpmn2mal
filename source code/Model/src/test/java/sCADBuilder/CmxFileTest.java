package sCADBuilder;

import sCADBuilder.model.cmx.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CmxFileTest {
    private static CmxFile testFile;

    @BeforeAll
    public static void firstInit() {
        testFile = new CmxFile();
    }

    @BeforeEach
    public void init(){
        ObjectView view = new ObjectView();
        view.setName("FunnyName");
        view.setObjectId(1);
        testFile.addView(view);

        DefaultView dView = new DefaultView();
        dView.setName("Default View");

        ViewItem item = new ViewItem();
        item.setId(2);
        Location location = new Location();
        location.setX(1);
        location.setY(1);
        item.setLocation(location);
        dView.addItem(item);

        item = new ViewItem();
        item.setId(3);
        location = new Location();
        location.setX(1);
        location.setY(1);
        item.setLocation(location);
        dView.addItem(item);

        ViewConnection connection = new ViewConnection();
        connection.setId(4);
        connection.setSourceId(2);
        connection.setTargetId(3);
        connection.setSourceProperty("source");
        connection.setTargetProperty("target");
        dView.addConnection(connection);

        testFile.addView(dView);
    }

    @Test
    public void testCreation() {
        String xml = testFile.toString();
        assertTrue(xml.length()>0);
    }

    @Test
    public void testEmpty() {
        CmxFile testFile = new CmxFile();

        String xml = testFile.toString();
        assertTrue(xml.length()>0);
    }
}
