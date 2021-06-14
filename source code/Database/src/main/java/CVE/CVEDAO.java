package CVE;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CVEDAO {
  private static final Logger logger = LoggerFactory.getLogger(CVEDAO.class);
  JsonArray results;
  String serverName;

  public CVEDAO(String filePath) {
    try {
      results = new JsonArray();
      final File currFile = new File(filePath.toString());
      String name[] = currFile.getName().split("\\.");
      this.serverName = name[0];
      final Scanner sc = new Scanner(currFile);
      sc.useDelimiter("\n");
      String temp = "";
      while (sc.hasNext()) {
        temp = sc.next();
      }
      Gson gson = new Gson();
      JsonElement element = gson.fromJson(temp, JsonElement.class);
      JsonObject jsonObj = element.getAsJsonObject();

      // Example: how to navigate in the cve-data

      // logger.debug(jsonObj.entrySet().toString());
      // logger.debug(jsonObj.keySet().toString());
      // logger.debug(jsonObj.get("result").toString());
      // logger.debug(jsonObj.get("result").getAsJsonObject().get("CVE_Items").toString());
      logger.debug(
          jsonObj
              .get("result")
              .getAsJsonObject()
              .get("CVE_Items")
              .getAsJsonArray()
              .get(0)
              .getAsJsonObject()
              .get("impact")
              .toString());
      // logger.debug(jsonObj.get("result").getAsJsonObject().get("CVE_Items").getAsJsonArray().get(0).getAsJsonObject().get("cve").getAsJsonObject().get("description").toString());

      results =
          jsonObj.get("result").getAsJsonObject().get("CVE_Items").getAsJsonArray().deepCopy();
      /*for (int i = 0; i < results.size(); i++) {
        results.get(i).getAsJsonObject().addProperty("serverName", name[0]);
      }*/
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public JsonArray getResults() {
    return results;
  }

  public void getCveData() {}

  public String getServerName() {
    return serverName;
  }
}
