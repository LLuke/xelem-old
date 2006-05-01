package nl.fountain.xelem.ztest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import nl.fountain.xelem.GIO;
import nl.fountain.xelem.XFactory;
import nl.fountain.xelem.XelemException;
import nl.fountain.xelem.excel.ss.XLWorkbook;

public class StyleFactoryWorkbook extends XLWorkbook {
	
	protected XFactory xFactory;
	
	
	public StyleFactoryWorkbook(String name) {
		super(name);
	}
	
	public Document createDocument() throws ParserConfigurationException {
        GIO gio = new GIO();
        Document doc = getDoc();
        Element root = doc.getDocumentElement();
        assemble(root, gio);
        return doc;
    }


	public XFactory getFactory() {
		System.out.println("called");
		if (xFactory == null) {
            try {
                xFactory = XFactory.newInstance();
            } catch (XelemException e) {
                // return an empty factory
                xFactory = XFactory.emptyFactory();
            } 
        }
        return xFactory;
	}
	
	private Document getDoc() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation domImpl = builder.getDOMImplementation();
        return domImpl.createDocument(XMLNS, getTagName(), null);
    }

}
