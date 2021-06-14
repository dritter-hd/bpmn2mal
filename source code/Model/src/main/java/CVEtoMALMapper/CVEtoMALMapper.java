package CVEtoMALMapper;

import CVE.CVEHandler;
import com.google.gson.JsonArray;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.Graphs;

import java.util.ArrayList;
import java.util.List;

public class CVEtoMALMapper {
  CVEHandler cveHandler;
  Graph malGraph;
  List<Graph> malGraphList = new ArrayList<>();

  public CVEtoMALMapper(Graph graph, CVEHandler cveHandler) {
    this.cveHandler = cveHandler;
    this.malGraph = graph;
    mapCVEtoMAL();

    for (int i = 0; i < malGraph.getNodeCount(); i++){
      if (malGraph.getNode(i).getAttribute("server").equals("Process")
      || malGraph.getNode(i).getAttribute("server").equals("DataObject")){
        malGraphList.add(addAttackers(malGraph, i));
      }
    }


  }

  private void mapCVEtoMAL(){
    JsonArray jsonArray = new JsonArray();
    int vulnNodeCounter = 0;
    int vulnEdgeCounter = 0;
    int nodeCount = malGraph.getNodeCount();
    for (int i = 0; i < nodeCount; i++){
      jsonArray = cveHandler.getCve((String)malGraph.getNode(i).getAttribute("serverName"));
      for (int j = 0; j < jsonArray.size(); j++) {
        String vulnNodeID = malGraph.getNode(i).getId() + "_v" + vulnNodeCounter;
        malGraph.addNode(vulnNodeID);
        malGraph.addEdge("V_" + String.valueOf(vulnEdgeCounter), malGraph.getNode(i), malGraph.getNode(vulnNodeID));
        malGraph.getEdge("V_" + String.valueOf(vulnEdgeCounter)).setAttribute("id", malGraph.getEdgeCount()-1);
        malGraph.getNode(vulnNodeID).setAttribute("server", "Vuln_" + jsonArray.get(j).getAsJsonObject().get("impact").getAsJsonObject().get("baseMetricV3").getAsJsonObject().get("cvssV3").getAsJsonObject().get("baseSeverity").toString().replaceAll("^\"|\"$", ""));
        malGraph.getNode(vulnNodeID).setAttribute("name", vulnNodeID);
        malGraph.getNode(vulnNodeID).setAttribute("id", malGraph.getNodeCount()-1);
        vulnNodeCounter++;
        vulnEdgeCounter++;
      }
      vulnNodeCounter = 0;
    }
  }

  public Graph addAttackers(Graph graph, int index){
    Graph malGraphWithAttacker = Graphs.clone(graph);
    malGraphWithAttacker.addNode("Attacker");
    malGraphWithAttacker.getNode("Attacker").setAttribute(String.valueOf(index), "fullAccess");
    return malGraphWithAttacker;
  }

  public Graph getMalGraph() {
    return malGraph;
  }

  public List<Graph> getMalGraphList() {
    return malGraphList;
  }
}
