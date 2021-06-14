package IntegrationFlow;

import com.sap.anagraph.graph.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegrationFlowDAO {
  private static final Logger logger = LoggerFactory.getLogger(IntegrationFlowDAO.class);
  private MultiGraph graph;
  private AnaGraph dataGraph;
  private List<Node> nodes;

  public IntegrationFlowDAO(String filePath) throws IOException {


    final File file = new File(filePath);

    FileSource fileSource = FileSourceFactory.sourceFor(filePath);
    String graphName = filePath.substring(filePath.lastIndexOf("/"), filePath.lastIndexOf("."));
    graph = new MultiGraph(graphName);
    fileSource.addSink(graph);
    fileSource.readAll(file.getParent() + File.separator + file.getName());
    fileSource.removeSink(graph);
    logger.debug("Graph loaded from file " + filePath);

    //dataGraph = GraphLoader.create().loadMultiGraphComplete(file.getParent() + File.separator, file.getName());
    nodes = new ArrayList<Node>();
    for (int i = 0; i < graph.getNodeCount(); i++) {
      nodes.add(graph.getNode(i));
    }
    String stylesheet =
        graph.getAttribute("ui.stylesheet").toString();
    graph.setAttribute(
            "ui.stylesheet",
            stylesheet
                + " node.Attackpoint { shape:box; fill-color: blue; }"
                + " node.HighSeverity { shape:box; fill-color: red; }"
                + " node.MediumSeverity { shape:box; fill-color: yellow; }"
                + " node.LowSeverity { shape:box; fill-color: green; }");
  }

  public List<Node> getNodes() {
    return nodes;
  }
}
