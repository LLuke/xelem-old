/*
 * Created on Dec 24, 2004
 *
 */
package nl.fountain.xelem.ztest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import nl.fountain.xelem.XSerializer;
import nl.fountain.xelem.excel.Row;
import nl.fountain.xelem.excel.ss.SSRow;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 */
public class Tester {

    public static void main(String[] args) throws Exception {     
        Tester tester = new Tester();
        tester.appendRow();
        //tester.changeCellData();
    }
    
    public void changeCellData() throws Exception {
        Document doc = getDocument("D:/eclipse_file/Aviso.xml");
        NodeList nodelist = doc.getElementsByTagName("Row");
        Node row2 = nodelist.item(1);
        nodelist = row2.getChildNodes();
        //System.out.println(nodelist.getLength());
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node cell2 = nodelist.item(i);
            Node data = cell2.getChildNodes().item(0);
            if (data != null) {
                Node datavalue = data.getChildNodes().item(0);
                if (datavalue != null) {
                    if ("adm_code".equals(datavalue.getNodeValue()))
                        //System.out.println(datavalue);
                    	datavalue.setNodeValue("niwi_05");
                }
            }
        }
        
        serialize(doc, "D:/eclipse_file/Aviso3.xml");
    }
    
    public void appendRow() throws Exception {
        Document doc = getDocument("D:/eclipse_file/Aviso.xml");

        Row row = new SSRow();
        row.addCell("419900");
        row.addCell("wk 43, piet 45,8 u.");
        row.addCell("002020");
        row.addCell(-12345.67);
//        row.setStyleID("s34");
//        row.setHeight(55.62);
        
        
        Element rowElement = row.createElement(doc);
        
        NodeList nodelist = doc.getElementsByTagName("Table");
        Node table = nodelist.item(0);
        
        table.appendChild(rowElement);
        
        
        serialize(doc, "D:/eclipse_file/Aviso2.xml");
    }
    
    public void serialize(Document doc, String fileName) throws Exception {
        XSerializer xs = new XSerializer(XSerializer.US_ASCII);
        OutputStream out = new FileOutputStream(fileName);
        xs.serialize(doc, out);
        out.close();
    }

    public Document getDocument(String fileName) {
        Document doc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream is = new FileInputStream(fileName);
            doc = builder.parse(is);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (FactoryConfigurationError e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }
}
