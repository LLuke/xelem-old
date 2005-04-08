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
 * Supervises the process of reading Excel workbooks.
 * 
 * @since xelem.2.0
 */
public class Director {
    
    private XLWorkbookBuilder xlworkbookbuilder;
    private SSWorksheetBuilder ssworksheetbuilder;
    private SSRowBuilder ssrowbuilder;
    private SSCellBuilder sscellbuilder;
    private List anonymousBuilders;
    private Area buildArea;
    private List listeners;
    private boolean listeningOnly;
    
    private String currentSheetName;
    private int currentRowIndex;
    
    public void setBuildArea(Area area) {
        buildArea = area;
    }
    
    public Area getBuildArea() {
        if (buildArea == null) {
            buildArea = new Area(Worksheet.firstRow, Worksheet.firstColumn,
                    Worksheet.lastRow, Worksheet.lastColumn);
        }
        return buildArea;
    }
    
    public boolean hasBuildArea() {
        return buildArea != null;
    }
    
    public List getListeners() {
        if (listeners == null) {
            listeners = new ArrayList();
        }
        return listeners;
    }
    
    public void addExcelReaderListener(ExcelReaderListener l) {
        if (!getListeners().contains(l)) {
            getListeners().add(l);
        }
    }
    
    public boolean removeExcelReaderListener(ExcelReaderListener l) {
        return getListeners().remove(l);
    }
    
    public void clearExcelReaderListeners() {
        getListeners().clear();
    }
    
    public void setListenOnly(boolean listen) {
        listeningOnly = listen;
    }
    
    public boolean isListeningOnly() {
        return listeningOnly;
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
    
    protected void setCurrentRowInfo(String sheetName, int rowIndex) {
        currentSheetName = sheetName;
        currentRowIndex = rowIndex;
    }
    
    protected String getCurrentSheetName() {
        return currentSheetName;
    }
    
    protected int getCurrentRowIndex() {
        return currentRowIndex;
    }
    
    private List getBuilders() {
        if (anonymousBuilders == null) {
            anonymousBuilders = new ArrayList();
        }
        return anonymousBuilders;
    }
    
    

}
