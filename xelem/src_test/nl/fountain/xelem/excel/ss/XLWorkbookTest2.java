package nl.fountain.xelem.excel.ss;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;
import nl.fountain.xelem.excel.Workbook;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XLWorkbookTest2 extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(XLWorkbookTest2.class);
	}
	
	public void testDoc() throws ParserConfigurationException {
		Workbook wb = new XLWorkbook();
		Document doc = wb.createDocument();
		Element style = doc.createElement("Style");
		
	}

}
