/*
 * Created on 10-sep-2004
 *
 */
package nl.fountain.xelem.trash;



public class SimpleExcelWriter  {
    
//    private Writer out;
//    private XMLProperties xp;
//    private boolean closeFile;
//
//    public SimpleExcelWriter(Writer out) {
//        this.out = out;
//        xp = XMLProperties.getInstance();
//    }
//    
//    public SimpleExcelWriter(OutputStream out) {
//        this(new BufferedWriter(new OutputStreamWriter(out)));
//    }
//    
//    public SimpleExcelWriter(String filename) throws FileNotFoundException {
//       this(new File(filename), true); 
//    }
//    
//    public SimpleExcelWriter(File file, boolean close) throws FileNotFoundException {
//        this(new BufferedOutputStream(new FileOutputStream(file)));
//        closeFile = close;
//    }
//    
////    public void write(Workbook workbook) throws IOException, UnsupportedStyleException {
////        writeIntro();
////        writeDocumentProperties(workbook);
////        writeExcelworkbook(workbook);
////        writeStyles(workbook);
////        writeSheets(workbook);
////        
////        out.write("</Workbook>");
////        out.flush();
////        
////        if (closeFile) out.close();
////    }
//
//    private void writeIntro() throws IOException {
//        out.write(xp.getProperty("version"));
//        out.write("\n");
//        out.write(xp.getProperty("progid"));
//        out.write("\n");
//        out.write(xp.getProperty("info"));
//        out.write("<Workbook xmlns=");
//        out.write(xp.getProperty("xmlns"));
//        out.write("\n");
//        out.write(" xmlns:o=");
//        out.write(xp.getProperty("xmlns_o"));
//        out.write("\n");
//        out.write(" xmlns:x=");
//        out.write(xp.getProperty("xmlns_x"));
//        out.write("\n");
//        out.write(" xmlns:ss=");
//        out.write(xp.getProperty("xmlns_ss"));
//        out.write("\n");
//        out.write(" xmlns:html=");
//        out.write(xp.getProperty("xmlns_html"));
//        out.write(">");
//        out.write("\n");
//    }
//    
////    private void writeDocumentProperties(Workbook workbook) throws IOException {
////        out.write(" <o:DocumentProperties>");
////        out.write("\n");
////        
////        out.write(" </o:DocumentProperties>");
////        out.write("\n");
////    }
////    
////    private void writeExcelworkbook(Workbook workbook) throws IOException {
////        out.write(" <x:ExcelWorkbook>");
////        out.write("\n");
////        
////        out.write(" </x:ExcelWorkbook>");
////        out.write("\n");
////    }
////    
////    private void writeStyles(Workbook workbook) throws IOException, UnsupportedStyleException {
//////        out.write(" <Styles>");
//////        out.write("\n");
//////        out.write(StyleFactory.get("Default"));
//////        for (Iterator iter = workbook.getStyleIDs().iterator(); iter.hasNext();) {
//////            out.write(StyleFactory.get((String) iter.next()));           
//////        }        
//////        out.write(" </Styles>");
//////        out.write("\n");
////    }
////    
////    private void writeSheets(Workbook workbook) throws IOException {
////        for (Iterator iter = workbook.getWorksheets().iterator(); iter.hasNext();) {
////            Worksheet sheet = (Worksheet) iter.next();
////            out.write(" <ss:Worksheet");
////            writeSheetAttributes(sheet);
////            out.write(">");
////            out.write("\n");
////            writeWorksheetOptions(sheet);
////            writeTable(sheet);
////            
////            out.write(" </ss:Worksheet>");
////            out.write("\n");
////        }
////    }
////    
////    private void writeSheetAttributes(Worksheet sheet) throws IOException {
////        for (Iterator iter = sheet.getAttributes().iterator(); iter.hasNext();) {
////            out.write((String)iter.next());           
////        }
////    }
////    
////    private void writeWorksheetOptions(Worksheet sheet) throws IOException {
//////        if (sheet.hasWorksheetOptions()) {
//////	        out.write("  <x:WorksheetOptions>");
//////	        out.write("\n");
//////	        for (Iterator iter = sheet.getWorksheetOptions().getOptions().iterator(); iter.hasNext();) {
//////                out.write("   ");
//////	            out.write((String)iter.next());
//////	            out.write("\n");
//////            }
//////	        out.write("  </x:WorksheetOptions>");
//////	        out.write("\n");
//////        }
////    }
////
////    private void writeTable(Worksheet sheet) throws IOException {
////        Table table = sheet.getTable();
////        out.write("  <ss:Table>");
////        out.write("\n");
////        writeRows(table);
////        
////        out.write("  </ss:Table>");
////        out.write("\n");
////    }
////    
////    private void writeRows(Table table) throws IOException {
////        Iterator iter = table.rowIterator();
////        while (iter.hasNext()) {
////            Row row = (Row) iter.next();
////            out.write("   <ss:Row"); 
////            writeRowAttributes(row);
////            out.write(">");
////            out.write("\n");
////            writeCells(row);
////            
////            out.write("   </ss:Row>");
////            out.write("\n");
////        }
////    }
////    
////    private void writeRowAttributes(Row row) throws IOException {
////        for (Iterator iter = row.getAttributes().iterator(); iter.hasNext();) {
////            out.write((String)iter.next());
////        }
////    }
////
////    private void writeCells(Row row) throws IOException {
////        Iterator iter = row.cellIterator();
////        while (iter.hasNext()) {
////            Cell cell = (Cell) iter.next();
////            out.write("    <ss:Cell");
////            writeCellAttributes(cell);
////            out.write(">");
////            out.write("\n");
////            out.write("     <ss:Data ss:Type=\"");
////            out.write(cell.getXlDataType());
////            out.write("\">");
////            out.write(cell.getData$());
////            out.write("</ss:Data>");
////            out.write("\n");
////            
////            out.write("    </ss:Cell>");
////            out.write("\n");
////        }
////    }
////
////    private void writeCellAttributes(Cell cell) throws IOException {
//////        for (Iterator iter = cell.getAttributes().iterator(); iter.hasNext();) {
//////            out.write((String)iter.next());            
//////        }
////    }

}
