package Startup;

import CVE.CVEHandler;
import CVEtoMALMapper.CVEtoMALMapper;
import Config.ConfigDAO;
import Config.ConfigHandler;
import Controller.Controller;
import Crawler.CveConfigReader;
import Crawler.CveCrawler;
import IntegrationFlowHandler.IntegrationFlowHandler;
import IntegrationFlowtoNodeMapper.IntegrationFlowtoNodeMapper;
import MAL.MALGraphHandler;
import View.View;
import java.io.File;
import java.io.IOException;
import org.mal_lang.compiler.lib.CompilerException;
import sCADBuilder.ScadFactory;
import sCADBuilder.output.ScadWriter;

public class Startup {

  private static File resourcesDirectory =
      new File("LayeredArchitecture/Model/src/resources/sCADBuilder");
  private static File mapping =
      new File(resourcesDirectory.getAbsolutePath() + File.separator + "mapping.xml");
  private static File coreLang =
      new File(resourcesDirectory.getAbsolutePath() + File.separator + "coreLang.mal");
  /**
   * This class starts upp all the necessary components to run the program.
   *
   * @param args Input currently not in use.
   */
  public static void main(String[] args) throws CompilerException, IOException {
    // TODO Create Database handlers
    ConfigHandler configHandler = new ConfigHandler();
    CVEHandler cveHandler = new CVEHandler();
    MALGraphHandler malGraphHandler = new MALGraphHandler();
    IntegrationFlowHandler integrationFlowHandler = new IntegrationFlowHandler();

    // TODO Model components need to take in database handlers

    // Crawler currently not run. Constructors need to be set up so that the earlier code uses the
    // new Layer pattern architecture
    CveConfigReader configReader = new CveConfigReader(); // needs configHandler
    CveCrawler cveCrawler = new CveCrawler(cveHandler); // needs cveHandler
    ConfigDAO configDAO = configHandler.getEndpointUrlFromProperties();
    cveCrawler.getNvdData(configDAO.getDAO(), configHandler.getConfigProperties());

    IntegrationFlowtoNodeMapper integrationFlowtoNodeMapper = new IntegrationFlowtoNodeMapper(integrationFlowHandler, configHandler);
    CVEtoMALMapper cveToMALMapper = new CVEtoMALMapper(integrationFlowtoNodeMapper.getMalGraph(), cveHandler);

    for (int i = 0; i < cveToMALMapper.getMalGraphList().size(); i++) {
      ScadFactory scadFactory = new ScadFactory(cveToMALMapper.getMalGraphList().get(i), mapping, coreLang);

      ScadWriter scadWriter = new ScadWriter();
      File scadFile = new File(resourcesDirectory.getAbsolutePath() + File.separator + "MALtest_" + i + ".sCAD");

      scadWriter.createScad(
              scadFile,
              "MAL" + i,
              scadFactory.getEomFile().toString(),
              scadFactory.getCmxFile().toString(),
              scadFactory.getMetaFile().toString());
    }

    // TODO Create Controller.Controller
    Controller controller = new Controller(configReader, cveCrawler, cveToMALMapper);

    // TODO Create View.View
    View view = new View(controller);
  }
}
