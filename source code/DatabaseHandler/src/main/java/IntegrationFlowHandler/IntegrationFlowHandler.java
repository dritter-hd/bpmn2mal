package IntegrationFlowHandler;

import IntegrationFlow.IntegrationFlowDAO;
import java.io.IOException;

public class IntegrationFlowHandler {
  private IntegrationFlowDAO integrationFlowDAO;

  public IntegrationFlowHandler() throws IOException {
    String filePath = "processes/src/main/resources/process_graphs/com_sap_GS_Italy_Invoice.gml";
    //String filePath = "resources/process_graphs/com_sap_GS_Italy_Invoice.gml";
    integrationFlowDAO = new IntegrationFlowDAO(filePath);
  }

  public IntegrationFlowDAO getIntegrationFlowDAO() {
    return integrationFlowDAO;
  }
}
