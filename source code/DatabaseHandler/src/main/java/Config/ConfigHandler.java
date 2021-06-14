package Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigHandler {
    private static final Logger logger = LoggerFactory.getLogger(ConfigHandler.class);
    private static Properties configProperties;
    private static List<String> pSearchParameters;

    public ConfigHandler() throws FileNotFoundException {
        this.configProperties = loadProperties();
        this.pSearchParameters = loadConfig();
    }


    /**
     * Builds the URL for the HTTP Get Method form the Data read by the Config Reader
     *
     */
    public ConfigDAO getEndpointUrlFromProperties() {
        final ConfigDAO configDAO = new ConfigDAO();
        for (int i = 0; i < this.pSearchParameters.size(); i++) {
            if (this.pSearchParameters.get(i).toString().startsWith("servers")) {
                for (int j = 0;
                     j < this.pSearchParameters.get(i).toString().split("=")[1].split(",").length;
                     j++) {
                    configDAO.addToDAOList(
                            "?keyword=" + this.configProperties.get("servers").toString().split(",")[j]);
                }
            }
        }
        if (configDAO.daoEmpty()) {
            configDAO.addToDAOList("?");
            logger.debug("no server given");
        }
        for (int i = 0; i < this.pSearchParameters.size(); i++) {
            if (this.pSearchParameters.get(i).toString().startsWith("lastModifiedStart")) {
                for (int j = 0; j < configDAO.daoSize(); j++) {
                    String urlStringTillNow = configDAO.getFromDAO(j);
                    String lastModifiedDateStart =
                            "&" + "modStartDate=" + this.configProperties.get("lastModifiedStart");
                    String timeForDates = "T00:00:00:000%20UTC-05:00";
                    StringBuffer urlString = new StringBuffer();
                    urlString.append(urlStringTillNow).append(lastModifiedDateStart).append(timeForDates);
                    configDAO.setNvdSearchQueries(j, urlString.toString());
                }
            }
            if (this.pSearchParameters.get(i).toString().startsWith("pubStartDate")) {
                for (int j = 0; j < configDAO.daoSize(); j++) {
                    String urlStringTillNow = configDAO.getFromDAO(j);
                    String pubStartDate = "&" + "pubStartDate=" + this.configProperties.get("lastModifiedStart");
                    String timeForDates = "T00:00:00:000%20UTC-05:00";
                    StringBuffer urlString = new StringBuffer();
                    urlString.append(urlStringTillNow).append(pubStartDate).append(timeForDates);
                    configDAO.setNvdSearchQueries(j, urlString.toString());
                }
            }
            if (this.pSearchParameters.get(i).toString().startsWith("pubEndDate")) {
                for (int j = 0; j < configDAO.daoSize(); j++) {
                    String urlStringTillNow = configDAO.getFromDAO(j);
                    String pubEndDate = "&" + "pubEndDate=" + this.configProperties.get("lastModifiedStart");
                    String timeForDates = "T00:00:00:000%20UTC-05:00";
                    StringBuffer urlString = new StringBuffer();
                    urlString.append(urlStringTillNow).append(pubEndDate).append(timeForDates);
                    configDAO.setNvdSearchQueries(j, urlString.toString());
                }
            }
            if (pSearchParameters.get(i).toString().startsWith("lastModifiedEnd")) {
                for (int j = 0; j < configDAO.daoSize(); j++) {
                    String urlStringTillNow = configDAO.getFromDAO(j);
                    String lastModifiedDateEnd =
                            "&" + "modEndDate=" + this.configProperties.get("lastModifiedEnd");
                    String timeForDates = "T00:00:00:000%20UTC-05:00";
                    StringBuffer urlString = new StringBuffer();
                    urlString.append(urlStringTillNow).append(lastModifiedDateEnd).append(timeForDates);
                    configDAO.setNvdSearchQueries(j, urlString.toString());
                }
            }
            if (this.pSearchParameters.get(i).toString().startsWith("cvssV2Severity")) {
                for (int j = 0; j < configDAO.daoSize(); j++) {
                    String urlStringTillNow = configDAO.getFromDAO(j);
                    String cvssV2Severity = "&" + "cvssV2Severity=" + this.configProperties.get("cvssV2Severity");
                    StringBuffer urlString = new StringBuffer();
                    urlString.append(urlStringTillNow).append(cvssV2Severity);
                    configDAO.setNvdSearchQueries(j, urlString.toString());
                }
            }
            if (this.pSearchParameters.get(i).toString().startsWith("cvssV3Severity")) {
                for (int j = 0; j < configDAO.daoSize(); j++) {
                    String urlStringTillNow = configDAO.getFromDAO(j);
                    String cvssV3Severity = "&" + "cvssV3Severity=" + this.configProperties.get("cvssV3Severity");
                    StringBuffer urlString = new StringBuffer();
                    urlString.append(urlStringTillNow).append(cvssV3Severity);
                    configDAO.setNvdSearchQueries(j, urlString.toString());
                }
            }
        }
        return configDAO;
    }

    private List<String> loadConfig() {
        try {
            final File file = new File("LayeredArchitecture/DatabaseHandler/src/main/java/Config/ServerConfig.txt");
            final Scanner sc = new Scanner(file);
            final List<String> parameters = new ArrayList<>();

            sc.useDelimiter("\n");
            while (sc.hasNext()) {
                String temp = sc.next();
                if (temp.split("=")[1].length() > 1) {
                    parameters.add(temp);
                }
            }
            sc.close();
            return parameters;
        } catch (final Exception FileNotFoundException) {
            logger.error(
                    "The CveSearchConfig.txt File was not found in the directory: (crawler\\src\\main\\resources\\).");
            throw new IllegalStateException(
                    "The CveSearchConfig.txt File was not found in the directory:"
                            + " (crawler\\src\\main\\resources\\).",
                    FileNotFoundException);
        }
    }

    /**
     * loads the Properties form the config file
     *
     * @return Properties from the config file
     */
    private Properties loadProperties() throws FileNotFoundException {
        try {
            final File file =
                    new File("LayeredArchitecture/DatabaseHandler/src/main/java/Config/ServerConfig.txt");
            final InputStream fis = new FileInputStream(file);
            final Properties config = new Properties();
            config.load(fis);
            return config;
        } catch (final Exception FileNotFoundException) {
            logger.error(
                    "The CveSearchConfig.properties File was not found in the directory: (crawler\\src\\main\\resources).");
            throw new IllegalStateException(
                    "The CveSearchConfig.properties File was not found in the directory:"
                            + " (crawler\\src\\main\\resources).",
                    FileNotFoundException);
        }
    }

    public Properties getConfigProperties(){
        return configProperties;
    }

}
