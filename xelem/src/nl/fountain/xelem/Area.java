/*
 * Created on 5-apr-2005
 *
 */
package nl.fountain.xelem;

/**
 *
 */
public class Area {
    
    protected int r1;
    protected int r2;
    protected int c1;
    protected int c2;
    
    public Area(int row1, int column1, int row2, int column2) {
        setDimensions(row1, column1, row2, column2);
    }

    public Area(Address address1, Address address2) {
        setDimensions(address1.r, address1.c, address2.r, address2.c);
    }
    
    public Area(String a1_ref) {
        String[] ar = a1_ref.split(":");
        if (ar.length < 2) {
            throw new IllegalArgumentException("use format 'A1:A1'.");
        }
        setDimensions(Address.calculateRow(ar[0]), Address.calculateColumn(ar[0]),
                Address.calculateRow(ar[1]), Address.calculateColumn(ar[1]));
    }
    
    public int getFirstRow() {
        return r1;
    }
    
    public int getLastRow() {
        return r2;
    }
    
    public int getFirstColumn() {
        return c1;
    }
    
    public int getLastColumn() {
        return c2;
    }
    
    public String getA1Reference() {
        StringBuffer sb = new StringBuffer();
        sb.append(Address.calculateColumn(c1));
        sb.append(r1);
        sb.append(":");
        sb.append(Address.calculateColumn(c2));
        sb.append(r2);
        return sb.toString();
    }
    
    public String getAbsoluteRange() {
        StringBuffer sb = new StringBuffer("R");
        sb.append(r1);
        sb.append("C");
        sb.append(c1);
        sb.append(":R");
        sb.append(r2);
        sb.append("C");
        sb.append(c2);
        return sb.toString();
    }
    
    public boolean isWithinArea(int rowIndex, int columnIndex) {
        return rowIndex >= r1 && rowIndex <= r2 
        	&& columnIndex >= c1 && columnIndex <= c2;
    }
    
    public boolean isWithinArea(Address address) {
        return isWithinArea(address.r, address.c);
    }
    
    public boolean isRowPartOfArea(int rowIndex) {
        return rowIndex >= r1 && rowIndex <= r2;
    }
    
    public boolean isColumnPartOfArea(int columnIndex) {
        return columnIndex >= c1 && columnIndex <= c2;
    }
    

    private void setDimensions(int row1, int column1, int row2, int column2) {
        if (row2 > row1) {
            r1 = row1;
            r2 = row2;
        } else {
            r1 = row2;
            r2 = row1;
        }
        if (column2 > column1) {
            c1 = column1;
            c2 = column2;
        } else {
            c1 = column2;
            c2 = column1;
        }
    }
    

}
