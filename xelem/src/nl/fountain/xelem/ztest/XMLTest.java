/*
 * Created on 11-feb-2005
 *
 */
package nl.fountain.xelem.ztest;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nl.fountain.xelem.XSerializer;
import nl.fountain.xelem.XelemException;
import nl.fountain.xelem.excel.Workbook;
import nl.fountain.xelem.excel.XLElement;
import nl.fountain.xelem.excel.ss.XLWorkbook;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 */
public class XMLTest {

    public static void main(String[] args) throws Exception {
        XMLTest docTest = new XMLTest();
        docTest.testAppendElement();
        //docTest.createWB();
    }
    
    public void testAppendElement() throws Exception {
        String tagName = "Cell";
        String nameSpace = "urn:schemas-microsoft-com:office:spreadsheet";
        String prefix = "ss";
        Document doc = createDoc();
        doc.createEntityReference(prefix);
        Element element = doc.createElementNS(nameSpace, tagName);
        element.setPrefix(prefix);
        Element root = doc.getDocumentElement();
        root.appendChild(element);
        System.out.println();
        System.out.println(xmlToString(doc));
    }
    
    private Document createDoc() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation domImpl = builder.getDOMImplementation();
        Document doc = domImpl.createDocument(XLElement.XMLNS, "Test", null);
        Element root = doc.getDocumentElement();
        System.out.println("java.version: " + System.getProperty("java.version"));
        System.out.println("DocumentImpl: " + doc.getClass());
        //if (doc.getClass().getName().equals("org.apache.crimson.tree.XmlDocument")) {
            root.setAttribute("xmlns", XLElement.XMLNS);
        //}
        root.setAttribute("xmlns:o", XLElement.XMLNS_O);
        root.setAttribute("xmlns:x", XLElement.XMLNS_X);
        root.setAttribute("xmlns:ss", XLElement.XMLNS_SS);
        root.setAttribute("xmlns:html", XLElement.XMLNS_HTML);
        return doc;
    }
    
    private void transform(Document doc, Result result) throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {       
        TransformerFactory tFactory = TransformerFactory.newInstance();
        Transformer xformer = tFactory.newTransformer();
        xformer.setOutputProperty(OutputKeys.METHOD, "xml");
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source source = new DOMSource(doc);
        xformer.transform(source, result);
    }
    
    private String xmlToString(Document doc) throws TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);
        transform(doc, result);
        return sw.toString();
    }
    
    private void createWB() {
        Workbook wb = new XLWorkbook("bla");
        wb.addSheet().addCell("Hello bkla");
        XSerializer xs = new XSerializer();
        try {
            xs.serialize(wb);
        } catch (XelemException e) {
            e.printStackTrace();
        }
    }
    
}
