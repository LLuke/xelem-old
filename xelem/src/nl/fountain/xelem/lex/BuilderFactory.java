/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 *
 */
public class BuilderFactory {
    
    private XLWorkbookBuilder xlworkbookbuilder;
    private SSWorksheetBuilder ssworksheetbuilder;
    private SSCellBuilder sscellbuilder;
    private List anonymousBuilders;
    
    public Builder getXLWorkbookBuilder() {
        if (xlworkbookbuilder == null) {
            xlworkbookbuilder = new XLWorkbookBuilder();
        }
        return xlworkbookbuilder;
    }
    
    public Builder getSSWorksheetBuilder() {
        if (ssworksheetbuilder == null) {
            ssworksheetbuilder = new SSWorksheetBuilder();
        }
        return ssworksheetbuilder;
    }
    
    public Builder getSSCellBuilder() {
        if (sscellbuilder == null) {
            sscellbuilder = new SSCellBuilder();
        }
        return sscellbuilder; 
    }
    
    public Builder getAnonymousBuilder() {
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
