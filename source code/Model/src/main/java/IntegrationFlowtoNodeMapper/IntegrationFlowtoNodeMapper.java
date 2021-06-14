package IntegrationFlowtoNodeMapper;

import Config.ConfigHandler;
import IntegrationFlowHandler.IntegrationFlowHandler;
import com.sap.anagraph.graph.AnaNode;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IntegrationFlowtoNodeMapper {
  private IntegrationFlowHandler integrationFlowHandler;
  private ConfigHandler configHandler;
  private List<List<Node>> malPaths = new ArrayList<>();
  private Graph malGraph = new DefaultGraph("MALGraph");

  public IntegrationFlowtoNodeMapper(
          IntegrationFlowHandler integrationFlowHandler, ConfigHandler configHandler) {
    this.integrationFlowHandler = integrationFlowHandler;
    this.configHandler = configHandler;


    createMalGraph(integrationFlowHandler.getIntegrationFlowDAO().getNodes());
    prepareNodes(malGraph);
  }

  /** Extends the Endpointnodes */
  public void prepareNodes(Graph graph) {



    }

  /**
   * Creates a graph containing endpoint nodes from @param nodes
   * @param nodes A list of nodes from a graphstream graph
   */
  public void createMalGraph(List<Node> nodes){
    int edgeCounter = 0;
    int idCounter = 0;
    for (int i = 0; i < nodes.size(); i++) {
      if (((String)nodes.get(i).getAttribute("type")).contains("Endpoint")) {
        if (malGraph.getNode(nodes.get(i).getId()) == null) {
          nodes.get(i).setAttribute("ui.class", "Attackpoint");
          nodes.get(i).setAttribute("server", "TBD");
          nodes.get(i).setAttribute("severity", "TBD");
          nodes.get(i).setAttribute("ttc", Integer.MAX_VALUE);
          String label = nodes.get(i).getAttribute("ui.label") + " {Server:" + nodes.get(i).getAttribute("server") + "}";
          nodes.get(i).setAttribute("ui.label", label);

          malGraph.addNode(nodes.get(i).getId());
          malGraph.getNode(nodes.get(i).getId()).setAttribute("id", idCounter);
          malGraph.getNode(nodes.get(i).getId()).setAttribute("server", "Process");
          malGraph.getNode(nodes.get(i).getId()).setAttribute("name", nodes.get(i).getId());
          malGraph.getNode(nodes.get(i).getId()).setAttribute("serverName", "jetty"); // add in automatic name handling

        }
        Iterator<Node> ite = nodes.get(i).getDepthFirstIterator(true);
        while (ite.hasNext()) {
          Node node = ite.next();
          if (((String)node.getAttribute("type")).contains("Endpoint") && node.getId() != nodes.get(i).getId()) {
            malGraph.addNode(node.getId());
            malGraph.getNode(node.getId()).setAttribute("id", malGraph.getNodeCount()-1);
            malGraph.getNode(node.getId()).setAttribute("server", "Process"); //placeholder
            malGraph.getNode(node.getId()).setAttribute("name", node.getId());
            malGraph.getNode(nodes.get(i).getId()).setAttribute("serverName", "jetty"); // add in automatic name handling
            malGraph.addEdge(String.valueOf(edgeCounter), malGraph.getNode(nodes.get(i).getId()), malGraph.getNode(node.getId()));
            malGraph.getEdge(String.valueOf(edgeCounter)).setAttribute("id", edgeCounter);
            edgeCounter++;
            idCounter++;
          }
        }
      }
    }
  }

  public Graph getMalGraph() {
    return malGraph;
  }
}
