/*
 * Created on 8-okt-2004
 *
 */
package nl.fountain.xelem.trash;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import nl.fountain.xelem.UnsupportedStyleException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class StyleFactory {

    private static Map styles;
    public static final String DEFAULT = "Default";
    public static final String BOLD = "bold";
    public static final String COMMA_0 = "comma0";
    public static final String COMMA_1 = "comma1";
    public static final String COMMA_2 = "comma2";
    
    
    public static String get(String id) throws UnsupportedStyleException {
        String style = (String) getStyles().get(id);
        if (style == null) {
            style = readFromFile("styles", id);
        }
        return style;
    } 
    
    private static String readFromFile(String dir, String id) throws UnsupportedStyleException {
        String style;
        RandomAccessFile ra = null;
        StringBuffer sb = new StringBuffer();
        String seperator = System.getProperty("file.separator");
        try {
            ra = new RandomAccessFile(dir + seperator + id, "r");
            String s;
            while ((s = ra.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
        } catch (FileNotFoundException e) {
            throw new UnsupportedStyleException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ra != null) ra.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        style = sb.toString();
        styles.put(id, style);
        return style;
    }

    public static boolean supportsStyle(String styleID) {
        boolean found = false;
        try {
            get(styleID);
            found = true;
        } catch (UnsupportedStyleException e) {
            //
        }
        return found;
    }
    
    private static Map getStyles() {
        if (styles == null) {
            styles = new HashMap();
            getDefaultStyles(styles);
        }
        return styles;
    }
    
    protected static void getDefaultStyles(Map map) {
        String dir = "defaultstyles";
        String id = "blauw";
        getDefaultStylesFromFile(map, dir, id);
    }

    private static void getDefaultStylesFromFile(Map map, String dir, String id) {
        String seperator = System.getProperty("file.separator");
        InputStream in = 
            StyleFactory.class.getResourceAsStream(dir + seperator + id);
        StringBuffer sb = new StringBuffer();
        int c;
        try {
            while ((c = in.read()) != -1) {
                sb.append((char)c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put(id, sb.toString());
    }
    
    protected void loadStyles(Map map, InputStream in) {
        String ss = "urn:schemas-microsoft-com:office:spreadsheet";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(in);
            
            
            
            NodeList styles = doc.getElementsByTagNameNS(ss, "Style");
            for (int i = 0; i < styles.getLength(); i++) {
                Node style = styles.item(i);
                String id = style.getAttributes().getNamedItemNS(ss, "ID").getNodeValue();
                map.put(id, style);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
