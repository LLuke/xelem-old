/*
 * Created on 30-nov-2004
 *
 */
package nl.fountain.xelem;

/**
 * A reference to the intersection of a row and column.
 */
public class Address {

    /**
     * The left-most limit of the worksheets columns.
     */
    public static int firstColumn = 1;
    /**
     * The top row of the worksheet.
     */
    public static int firstRow = 1;
    /**
     * The right-most limit of the worksheets columns.
     */
    public static int lastColumn = 256;
    /**
     * The bottom row of the worksheet.
     */
    public static int lastRow = 65536;
    
    protected int r;
    protected int c;
    
    protected Address() {}
    
    /**
     * Constructs a new Address.
     * @param rowIndex		The row index of this Address.
     * @param columnIndex	The column index of this Address.
     */
    public Address(int rowIndex, int columnIndex) {
        r = rowIndex;
        c = columnIndex;
    }

    /**
     * Gets the index of the row of this Address.
     */
    public int getRowIndex() {
        return r;
    }

    /**
     * Gets the index of the column of this Address.
     */
    public int getColumnIndex() {
        return c;
    }

    /**
     * Specifies whether this Address is within the bounds of the
     * spreadsheet.
     */
    public boolean isWithinSheet() {
        return c >= firstColumn && c <= lastColumn 
        	&& r >= firstRow && r <= lastRow;
    }

    /**
     * Translates the position of this Address into an 
     * absolute R1C1-reference string.
     * 
     * @return The position of this Address as an absolute R1C1-reference string.
     */
    public String getAbsoluteAddress() {
        StringBuffer sb = new StringBuffer("R");
        sb.append(r);
        sb.append("C");
        sb.append(c);
        return sb.toString();
    }

    /**
     * Gets the relative location of this Address seen from a cell
     * at the intersection of the given <code>row</code> and <code>column</code>.
     * Or, to say it in another way,
     * calculates the offset of this Address rowIndex and ColumnIndex
     * from <code>row</code> and 
     * <code>column</code> and returns this as a relative R1C1-reference string.
     * <P>
     * Given a Cell cf which is at row 15 and column 12. The address is at
     * row 10, column 10.
     * <PRE>
     *          cf.setFormula("=" + adress.getRelativeAddress(15, 12);
     * </PRE>
     * leads to the formula <code>=R[-5]C[-2]</code>
     * and leaves the formula pointing to the cell at the position 
     * of this Address.
     * 
     * @return The offset of this Address as a relative R1C1-reference string.
     */
    public String getRelativeAddress(int row, int column) {
        StringBuffer sb = new StringBuffer("R");
        int rD = r - row;
        if (rD != 0) {
            sb.append("[");
            sb.append(rD);
            sb.append("]");
        }
        sb.append("C");
        int cD = c - column;
        if (cD != 0) {
            sb.append("[");
            sb.append(cD);
            sb.append("]");
        }
        return sb.toString();
    }
    
    /**
     * Gets the relative location of this address seen from the location of the
     * other address.
     * <P>
     * Given another address adr who's rowIndex is 15 and it's columnIndex is 12. 
     * This address is at row 10, column 10.
     * <PRE>
     *          address.getRelativeAddress(adr);
     * </PRE>
     * produces the string <code>R[-5]C[-2]</code>.
     * 
     * @return The offset of this Address as a relative R1C1-reference string.
     */
    public String getRelativeAddress(Address fromAddress) {
        return getRelativeAddress(fromAddress.r, fromAddress.c);
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer(this.getClass().getName());
        sb.append("[row=");
        sb.append(r);
        sb.append(",column=");
        sb.append(c);
        sb.append("]");
        return  sb.toString();
    }
    
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

}
