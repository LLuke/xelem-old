/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.fountain.xelem.Area;
import nl.fountain.xelem.excel.Worksheet;



/**
 *
 */
public class BuilderFactory {
    
    private XLWorkbookBuilder xlworkbookbuilder;
    private SSWorksheetBuilder ssworksheetbuilder;
    private SSRowBuilder ssrowbuilder;
    private SSCellBuilder sscellbuilder;
    private List anonymousBuilders;
    private Area readArea;
    
    public void setReadArea(Area area) {
        readArea = area;
    }
    
    public Area getReadArea() {
        if (readArea == null) {
            readArea = new Area(Worksheet.firstRow, Worksheet.firstColumn,
                    Worksheet.lastRow, Worksheet.lastColumn);
        }
        return readArea;
    }
    
    public XLWorkbookBuilder getXLWorkbookBuilder() {
        if (xlworkbookbuilder == null) {
            xlworkbookbuilder = new XLWorkbookBuilder();
        }
        return xlworkbookbuilder;
    }
    
    public SSWorksheetBuilder getSSWorksheetBuilder() {
        if (ssworksheetbuilder == null) {
            ssworksheetbuilder = new SSWorksheetBuilder();
        }
        return ssworksheetbuilder;
    }
    
    public SSRowBuilder getSSRowBuilder() {
        if (ssrowbuilder == null) {
            ssrowbuilder = new SSRowBuilder();
        }
        return ssrowbuilder;
    }
    
    public SSCellBuilder getSSCellBuilder() {
        if (sscellbuilder == null) {
            sscellbuilder = new SSCellBuilder();
        }
        return sscellbuilder; 
    }
    
    public AnonymousBuilder getAnonymousBuilder() {
        AnonymousBuilder aBuilder = null;
        for (Iterator iter = getBuilders().iterator(); iter.hasNext();) {
            AnonymousBuilder builder = (AnonymousBuilder) iter.next();
            if (!builder.isOccupied()) {
                aBuilder = builder;
                break;
            }
        }
        if (aBuilder == null) {
            aBuilder = new AnonymousBuilder();
            getBuilders().add(aBuilder);
        }
        aBuilder.setOccupied(true);
        return aBuilder;
    }
    
    private List getBuilders() {
        if (anonymousBuilders == null) {
            anonymousBuilders = new ArrayList();
        }
        return anonymousBuilders;
    }

}
