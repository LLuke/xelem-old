/*
 * Created on 10-mrt-2005
 *
 */
package nl.fountain.xelem.ztest;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.XLElementTest;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DocumentTest extends XLElementTest {

    public static void main(String[] args) {
        DocumentTest doctest = new DocumentTest();
        doctest.read();
    }
    
    public void read() {
        try {
            String fileName = "testsuitefiles/ReaderTest/reader.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            File configFile = new File(fileName);
            Document doc = builder.parse(configFile);
            
            NodeList sheets = 
                doc.getElementsByTagNameNS(XLElement.XMLNS_SS, "Worksheet");
            for (int i = 0; i < sheets.getLength(); i++) {
                String name = sheets.item(i).getAttributes()
                	.getNamedItemNS(XLElement.XMLNS_SS, "Name").getNodeValue();
                System.out.println(name);
                NodeList tables = sheets.item(i).getChildNodes();
                for (int k = 0; k < tables.getLength(); k++) {
                    String xml = xmlToString(tables.item(k));
                    System.out.println("====" + k + " " + tables.item(k));
                }
            }
            
        } catch (FactoryConfigurationError e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
    }
}
