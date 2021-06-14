package CVE;


import com.google.gson.JsonArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CVEHandler {
  List<String> namesOfFiles;
  List<CVEDAO> cvedaoList;

  public CVEHandler() {
    namesOfFiles = new ArrayList<>();
    cvedaoList = new ArrayList<>();
  }

  /**
   * Checks if there are already json files available so that they do not need to be requested again
   */
  public void cvefromJSON() {

    File[] files = new File("resources/CVEJson").listFiles();

    for (File file : files) {
      if (file.isFile()) {
        namesOfFiles.add(file.getName());
        cvedaoList.add(new CVEDAO(file.getPath()));
      }
    }
    if (namesOfFiles.size() > 0) {
      System.out.println("Found the following files");
      printNamesOfFiles();
    }
  }

  public void printNamesOfFiles() {
    for (int i = 0; i < namesOfFiles.size(); i++) {
      System.out.println(namesOfFiles.get(i));
    }
  }

  public List<CVEDAO> getCvedaoList() {
    return cvedaoList;
  }

  public JsonArray getCve (String name){
    JsonArray jsonArray = new JsonArray();

    for (int i = 0; i < cvedaoList.size(); i++){
      if (cvedaoList.get(i).getServerName().equals(name))
        jsonArray = cvedaoList.get(i).getResults();
    }

    return jsonArray;
  }
}
