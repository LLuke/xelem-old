/*
 * Created on 15-mrt-2005
 *
 */
package nl.fountain.xelem.lex;

import nl.fountain.xelem.excel.o.ODocumentPropertiesBuilder;
import nl.fountain.xelem.excel.ss.XLWorkbookBuilder;

/**
 *
 */
public class BuilderFactory {
    
    private XLWorkbookBuilder xlworkbookbuilder;
    private ODocumentPropertiesBuilder odocumentpropertiesbuilder;
    
    public Builder getXLWorkbookBuilder() {
        if (xlworkbookbuilder == null) {
            xlworkbookbuilder = new XLWorkbookBuilder();
        }
        return xlworkbookbuilder;
    }
    
    public Builder getODocumentPropertiesBuilder() {
        if (odocumentpropertiesbuilder == null) {
            odocumentpropertiesbuilder = new ODocumentPropertiesBuilder();
        }
        return odocumentpropertiesbuilder;
    }
    
    public Builder getDefaultBuilder() {
        return new DefaultBuilder();
    }

}
