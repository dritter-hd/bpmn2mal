package Crawler;

import CVE.CVEDAO;
import CVE.CVEHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CveCrawler {
  private static final Logger logger = LoggerFactory.getLogger(CveCrawler.class);
  private static Properties configProperties;

  private final String baseRequest = "https://services.nvd.nist.gov/rest/json/cves/1.0";

  private CVEHandler cveHandler;

  public CveCrawler(CVEHandler cveHandler){
    this.cveHandler = cveHandler;
  //  init();

  }

  private void init(){
    cveHandler.cvefromJSON();
  }

  /**
   * Builds the URL for the HTTP Get Method form the Data read by the Config Reader
   *
   * @param pSearchParameters List which contains the the Parameters for the Search given in the
   *     config file, which are not empty
   *
  public List<String> getEndpointUrlFromProperties(
          List<String> pSearchParameters, Properties pConfigProperties) {
    configProperties = pConfigProperties;
    final ArrayList<String> nvdSearchQueries = new ArrayList<String>();
    for (int i = 0; i < pSearchParameters.size(); i++) {
      if (pSearchParameters.get(i).toString().startsWith("servers")) {
        for (int j = 0;
             j < pSearchParameters.get(i).toString().split("=")[1].split(",").length;
             j++) {
          nvdSearchQueries.add(
                  "?keyword=" + pConfigProperties.get("servers").toString().split(",")[j]);
        }
      }
    }
    if (nvdSearchQueries.isEmpty()) {
      nvdSearchQueries.add("?");
      logger.debug("no server given");
    }
    for (int i = 0; i < pSearchParameters.size(); i++) {
      if (pSearchParameters.get(i).toString().startsWith("lastModifiedStart")) {
        for (int j = 0; j < nvdSearchQueries.size(); j++) {
          String urlStringTillNow = nvdSearchQueries.get(j);
          String lastModifiedDateStart =
                  "&" + "modStartDate=" + pConfigProperties.get("lastModifiedStart");
          String timeForDates = "T00:00:00:000%20UTC-05:00";
          StringBuffer urlString = new StringBuffer();
          urlString.append(urlStringTillNow).append(lastModifiedDateStart).append(timeForDates);
          nvdSearchQueries.set(j, urlString.toString());
        }
      }
      if (pSearchParameters.get(i).toString().startsWith("pubStartDate")) {
        for (int j = 0; j < nvdSearchQueries.size(); j++) {
          String urlStringTillNow = nvdSearchQueries.get(j);
          String pubStartDate = "&" + "pubStartDate=" + pConfigProperties.get("lastModifiedStart");
          String timeForDates = "T00:00:00:000%20UTC-05:00";
          StringBuffer urlString = new StringBuffer();
          urlString.append(urlStringTillNow).append(pubStartDate).append(timeForDates);
          nvdSearchQueries.set(j, urlString.toString());
        }
      }
      if (pSearchParameters.get(i).toString().startsWith("pubEndDate")) {
        for (int j = 0; j < nvdSearchQueries.size(); j++) {
          String urlStringTillNow = nvdSearchQueries.get(j);
          String pubEndDate = "&" + "pubEndDate=" + pConfigProperties.get("lastModifiedStart");
          String timeForDates = "T00:00:00:000%20UTC-05:00";
          StringBuffer urlString = new StringBuffer();
          urlString.append(urlStringTillNow).append(pubEndDate).append(timeForDates);
          nvdSearchQueries.set(j, urlString.toString());
        }
      }
      if (pSearchParameters.get(i).toString().startsWith("lastModifiedEnd")) {
        for (int j = 0; j < nvdSearchQueries.size(); j++) {
          String urlStringTillNow = nvdSearchQueries.get(j);
          String lastModifiedDateEnd =
                  "&" + "modEndDate=" + pConfigProperties.get("lastModifiedEnd");
          String timeForDates = "T00:00:00:000%20UTC-05:00";
          StringBuffer urlString = new StringBuffer();
          urlString.append(urlStringTillNow).append(lastModifiedDateEnd).append(timeForDates);
          nvdSearchQueries.set(j, urlString.toString());
        }
      }
      if (pSearchParameters.get(i).toString().startsWith("cvssV2Severity")) {
        for (int j = 0; j < nvdSearchQueries.size(); j++) {
          String urlStringTillNow = nvdSearchQueries.get(j);
          String cvssV2Severity = "&" + "cvssV2Severity=" + pConfigProperties.get("cvssV2Severity");
          StringBuffer urlString = new StringBuffer();
          urlString.append(urlStringTillNow).append(cvssV2Severity);
          nvdSearchQueries.set(j, urlString.toString());
        }
      }
      if (pSearchParameters.get(i).toString().startsWith("cvssV3Severity")) {
        for (int j = 0; j < nvdSearchQueries.size(); j++) {
          String urlStringTillNow = nvdSearchQueries.get(j);
          String cvssV3Severity = "&" + "cvssV3Severity=" + pConfigProperties.get("cvssV3Severity");
          StringBuffer urlString = new StringBuffer();
          urlString.append(urlStringTillNow).append(cvssV3Severity);
          nvdSearchQueries.set(j, urlString.toString());
        }
      }
    }
    return nvdSearchQueries;
  }

  */

  /**
   * The Method executes a HTTP Get Method to get the wished NVD Data
   *
   * @param pHttpParameters The method needs the filter Options for the NVD search
   */
  public void getNvdData(ArrayList<String> pHttpParameters, Properties configProperties) throws IllegalStateException {
    try {
      new File("./resources/CVEJson/").mkdirs();
      for (int i = 0; i < pHttpParameters.size(); i++) {
        String request = baseRequest + pHttpParameters.get(i);
        URL requestURL = new URL(request);
        HttpURLConnection con = (HttpURLConnection) requestURL.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("USER_AGENT", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
          BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
          String inputLine;
          StringBuilder response = new StringBuilder();

          while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
          }
          in.close();
          String directory = "./resources/CVEJson/";
          String fileName = configProperties.get("servers").toString().split(",")[i] + ".json";
          FileWriter result = new FileWriter(directory + fileName);
          result.write(response.toString());
          result.close();
        } else {
          System.out.println(responseCode);
          logger.error("GET request failed. The Program was terminated");
        }
        Thread.sleep(3000);
      }
    } catch (ProtocolException | MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
      logger.error("Something went wrong. The Program was terminated");
    }
  }
}
