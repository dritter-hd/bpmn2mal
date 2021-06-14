package Crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class CveConfigReader {
  private static final Logger logger = LoggerFactory.getLogger(CveConfigReader.class);
  /**
   * Method reads scConfig file which inherits the Filteroptions for the NVD search
   *
   * @return ArrayList which inherits the not empty Parameters form the config file
   */
  public List<String> loadConfig() {
    try {
      final File file = new File("/Users/klasengberg/Documents/GitHub/kth-cooperation/LayeredArchitecture/DatabaseHandler/src/main/java/Config/ServerConfig.txt");
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
  public Properties loadProperties() throws FileNotFoundException {
    try {
      final File file =
          new File("/Users/klasengberg/Documents/GitHub/kth-cooperation/LayeredArchitecture/DatabaseHandler/src/main/java/Config/ServerConfig.txt");
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
}
