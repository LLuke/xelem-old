/*
 * Created on 30-nov-2004
 *
 */
package nl.fountain.xelem;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import nl.fountain.xelem.excel.Worksheet;

/**
 * A reference to the intersection of a row and a column. Besides that Address can
 * be used to get R1C1-reference strings, which can be used in formulas and 
 * NamedRanges.
 */
public class Address implements Comparable {
    
     /**
      * The row index of this address.
      */
    protected int r;
    
    /**
     * The column index of this address.
     */
    protected int c;
    
    /**
     * This constructor is protected.
     */
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
     * Gets the index of the row of this address.
     */
    public int getRowIndex() {
        return r;
    }

    /**
     * Gets the index of the column of this address.
     */
    public int getColumnIndex() {
        return c;
    }

    /**
     * Specifies whether this address is within the bounds of the
     * spreadsheet.
     */
    public boolean isWithinSheet() {
        return c >= Worksheet.firstColumn && c <= Worksheet.lastColumn 
        	&& r >= Worksheet.firstRow && r <= Worksheet.lastRow;
    }

    /**
     * Translates the position of this address into an 
     * absolute R1C1-reference string.
     * 
     * @return The position of this address as an absolute R1C1-reference string.
     */
    public String getAbsoluteAddress() {
        StringBuffer sb = new StringBuffer("R");
        sb.append(r);
        sb.append("C");
        sb.append(c);
        return sb.toString();
    }
    
    /**
     * Gets the absolute range-address of a rectangular range 
     * in R1C1-reference style. The rectangle is
     * delimited by this address and <code>otherAddress</code>. 
     * This address can be in
     * any of the four corners of the rectangle, as long as 
     * <code>otherAddress</code> is in the opposite corner.
     * <P>
     * <img src="doc-files/getAbsoluteRange.gif">
     * <P>
     * @param otherAddress The address in the opposite corner.
     * @return A string in R1C1-reference style.
     */
    public String getAbsoluteRange(Address otherAddress) {
        return getAbsoluteRange(otherAddress.r, otherAddress.c);
    }
    
    /**
     * Gets the absolute range-address of a rectangular range 
     * in R1C1-reference style. The rectangle is
     * delimited by this address and the cell at the intersection of
     * <code>row</code> and <code>column</code>.
     * This address can be in
     * any of the four corners of the rectangle, as long as 
     * the intersection of
     * <code>row</code> and <code>column</code> is in the opposite corner.
     * <P>
     * <img src="doc-files/getAbsoluteRange_int_int.gif">
     * <P>
     * @param row		The row of the cell in the opposite corner.
     * @param column	The column of the cell in the opposite corner.
     * @return A string in R1C1-reference style.
     */
    public String getAbsoluteRange(int row, int column) {
        StringBuffer sb = new StringBuffer("R");
        int minR = r;
        int maxR = row;
        if (minR > maxR) {
            minR = row;
            maxR = r;
        }
        sb.append(minR);
        sb.append("C");
        int minC = c;
        int maxC = column;
        if (minC > maxC) {
            minC = column;
            maxC = c;
        }
        sb.append(minC);
        if (minR != maxR || minC != maxC) {
            sb.append(":R");
            sb.append(maxR);
            sb.append("C");
            sb.append(maxC);
        }
        return sb.toString();
    }
    
    /**
     * Gets the absolute range-address of a collection of addresses
     * in R1C1-reference style.
     * <P>
     * <img src="doc-files/getAbsoluteRange_Set.gif">
     * <P>
     * @param addresses A collection of addresses.
     * @return A string in R1C1-reference style or <code>null</code> if
     * 			the list is empty.
     * @throws ClassCastException If the addresses provided are not of equal class.
     */
    public static String getAbsoluteRange(Collection addresses) {
        if (addresses.size() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        Set adrs = new TreeSet(addresses);
        for (Iterator iter = adrs.iterator(); iter.hasNext();) {
            Address adr = (Address) iter.next();
            if (sb.length() > 0) sb.append(","); 
            sb.append(adr.getAbsoluteAddress());
        }
        return sb.toString();
    }
    
    /**
     * Gets a relative reference from this address to another address 
     * in R1C1-reference style.
     * <P>
     * <img src="doc-files/getRefTo.gif">
     * <P>
     * @param otherAddress The referenced address.
     * @return A string in R1C1-reference style.
     */
    public String getRefTo(Address otherAddress) {
        return getRefTo(otherAddress.r, otherAddress.c);
    }
    
    /**
     * Gets a relative reference from this address to a cell at the 
     * intersection of row and column in R1C1-reference style.
     * <P>
     * <img src="doc-files/getRefTo_int_int.gif">
     * <P>
     * @param row 		The row to be referenced.
     * @param column	The column to be referenced.
     * @return A string in R1C1-reference style.
     */
    public String getRefTo(int row, int column) {
        StringBuffer sb = new StringBuffer("R");
        int rO = row - r;
        if (rO != 0) {
            sb.append("[");
            sb.append(rO);
            sb.append("]");
        }
        sb.append("C");
        int cO = column - c;
        if (cO != 0) {
            sb.append("[");
            sb.append(cO);
            sb.append("]");
        }
        return sb.toString();
    }
    
    /**
     * Gets a relative reference from this address to a rectanglular range
     * in R1C1-reference style.
     * The rectangle is delimited by address1 and address2.
     * Address1 can be in any of the four corners of the rectangle, as long as
     * address2 is in the opposite corner.
     * <P>
     * <img src="doc-files/getRefTo_AdrAdr.gif">
     * <P>
     * @param address1 	The address in one corner of the range to be referenced.
     * @param address2	The address in the opposite corner of the range
     * 					to be referenced.
     * @return A string in R1C1-reference style.
     */
    public String getRefTo(Address address1, Address address2) {
        return getRefTo(address1.r, address1.c, address2.r, address2.c);
    }
    
    /**
     * Gets a relative reference from this address to a rectanglular range
     * in R1C1-reference style.
     * The rectangle is delimited by cells at the
     * intersection of r1, c1 and r2, c2 respectively.
     * The intersection of r1, c1 can be in any of the four corners of the 
     * rectangle, as long as the intersection of r2, c2
     * is in the opposite corner.
     * <P>
     * <img src="doc-files/getRefTo_4int.gif">
     * <P>
     * @param r1 	The row at intersection 1.
     * @param c1	The column at intersection 1.
     * @param r2 	The row at intersection 2.
     * @param c2	The column at intersection 2.
     * @return A string in R1C1-reference style.
     */
    public String getRefTo(int r1, int c1, int r2, int c2) {
        StringBuffer sb = new StringBuffer();
        String ref1 = getRefTo(r1, c1);
        sb.append(ref1);
        String ref2 = getRefTo(r2, c2);
        if (!ref1.equals(ref2)) {
            sb.append(":");
            sb.append(ref2);
        }
        return sb.toString();
    }
    
    /**
     * Gets a relative reference from this address to a collection of
     * addresses in R1C1-reference style.
     * <P>
     * <img src="doc-files/getRefTo_Set.gif">
     * <P>
     * @param addresses	A collection of addresses.
     * @return A string in R1C1-reference style or <code>null</code> if
     * 			the list is empty.
     * @throws ClassCastException If the addresses provided are not of equal class.
     */
    public String getRefTo(Collection addresses) {
        if (addresses.size() == 0) {
            return null;
        }   
        StringBuffer sb = new StringBuffer();
        Set adrs = new TreeSet(addresses);
        for (Iterator iter = adrs.iterator(); iter.hasNext();) {
            Address adr = (Address) iter.next();
            if (sb.length() > 0) sb.append(","); 
            sb.append(getRefTo(adr));
        }
        return sb.toString();
    }
    
    /**
     * Returns a string representation of this address.
     * Composed as:
     * <PRE>
     *          this.getClass().getName() + "[row=x,column=y]"
     * </PRE>
     * where x and y stand for row- and column index of this address.
     * 
     * @return A string representation of this address.
     */
    public String toString() {
        StringBuffer sb = new StringBuffer(this.getClass().getName());
        sb.append("[row=");
        sb.append(r);
        sb.append(",column=");
        sb.append(c);
        sb.append("]");
        return  sb.toString();
    }
    
    /**
     * Specifies whether the object in the parameter is equal to this address.
     * Another object is equal to this address if
     * <UL>
     * <LI>their classes are equal and
     * <LI>their row indexes are equal and
     * <LI>their column indexes are equal.
     * </UL>
     * In fact this method invokes the toString-method on both the object and
     * this address and performs the equals-test on these strings.
     * 
     * @param obj An object.
     * @return <code>true</code> if this address equals <code>obj</code>,
     * 			<code>false</code> otherwise.
     */
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return this.toString().equals(obj.toString());
    }
    
    /**
     * Compare this addres with the specified object for order. 
     * The specified object is cast to an address. Returns
     * <UL>
     * <LI>Negative if the row index of this address is less then the row index
     * 	of <code>o</code> or, if both have the same row index, if the column
     *  index of this address is less then the column index of of <code>o</code>;
     * <LI>Zero if both row and column index of this address and <code>o</code>
     *  are the same;
     * <LI>Positive if the row index of this address is greater then the row index
     * 	of <code>o</code> or, if both have the same row index, if the column
     *  index of this address is greater then the column index of of <code>o</code>
     * </UL>
     * 
     * @param o the object to be compared
     * @return A negative integer, zero, or a positive integer as this address is
     * 			less then, equal to or greater then the specified object.
     * @throws ClassCastException If the class of the specified object is not
     * 			equal to the class of this object.
     */
    public int compareTo(Object o) {
        if (!o.getClass().equals(this.getClass())) {
            throw new ClassCastException();
        }
        Address a = (Address) o;
        //return Worksheet.lastColumn * (r - a.r) + (c - a.c);
        if (r == a.r) {
            return c - a.c;
        }
        return r - a.r;
    }
    

}
