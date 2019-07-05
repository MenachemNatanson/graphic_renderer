package renderer;
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

/**
 * Load data from xml
 */
public class loadScene {
    /**
     * Load a render object from xml
     *
     * @param fileName the xml file name
     * @param ignoreResolution ignore the resolution
     * @return a render object with the data
     */
    public static Render loadFromXML(String fileName,Boolean ignoreResolution ) throws ParserConfigurationException, SAXException, IOException {

        //load the xml into the handler
        SAXParserFactory parserFactor = SAXParserFactory.newInstance();
        SAXParser parser = parserFactor.newSAXParser();
        SAXHandler handler = new SAXHandler();
        handler._ignoreResolution = ignoreResolution;
        String path = System.getProperty("user.dir") + "\\" + fileName ;
        File xmlFile = new File(path);
        InputStream fileReader = new FileInputStream(xmlFile);
        parser.parse(fileReader, handler);

        if(!handler.version.equals("1.0"))
            throw new IllegalArgumentException("Wrong xml version");
        return new Render(handler._imageWriter, handler._scene,handler._optimised,handler._beamRay);
    }
}
