/*
 * Created on 28-nov-2004
 *
 */
package nl.fountain.xelem;


/**
 * Keeps track of the position of cells being added to the
 * {@link nl.fountain.xelem.excel.Worksheet}.
 * <P>
 * [The position of the cellpointer is <em>not</em> displayed in the
 * actual Excel worksheet. If you wish to set the active cell in the
 * actual Excel worksheet on a position other then row 1, column 1, you
 * should use
 * {@link nl.fountain.xelem.excel.WorksheetOptions#setActiveCell(int, int)}.]
 * <P>
 * The position of the cellpointer is reflected in it's
 * {@link #getRowIndex()}- and {@link #getColumnIndex()}-values.
 * The cellpointer moves to a new position relative to its old
 * position at a call to {@link #move}. In what direction it moves and over
 * how many cells depends on it's settings. The default setting is to step
 * 1 column to the right.
 */
public class CellPointer {
    
    /**
     * A constant for the method {@link #setMovement(int)}.
     */
    public static final int MOVE_RIGHT = 0;
    
    /**
     * A constant for the method {@link #setMovement(int)}.
     */
    public static final int MOVE_LEFT = 1;
    
    /**
     * A constant for the method {@link #setMovement(int)}.
     */
    public static final int MOVE_DOWN = 2;
    
    /**
     * A constant for the method {@link #setMovement(int)}.
     */
    public static final int MOVE_UP = 3;
    
    /**
     * The left-most limit of the worksheets columns.
     */
    public int firstColumn = 1;
    
    /**
     * The top row of the worksheet.
     */
    public int firstRow = 1;
    
    /**
     * The right-most limit of the worksheets columns.
     */
    public int lastColumn = 256;
    
    /**
     * The bottom row of the worksheet.
     */
    public int lastRow = 65536; 
    
    private int r;
    private int c;
    private int hStep;
    private int vStep;
    private int hMove;
    private int vMove;
    
    /**
     * Constructs a new CellPointer. The position of the pointer will be at
     * row 1, column 1. The initial movement of the pointer is MOVE_RIGHT.
     * The initial step distance of the pointer is 1. So the default behavior 
     * of this pointer at a call to {@link #move()} is to move one
     * column to the right.
     * 
     * @see nl.fountain.xelem.excel.Worksheet#getCellPointer()
     */
    public CellPointer() {
        r = firstRow;
        c = firstColumn;
        hStep = 1;
        vStep = 1;
        hMove = 1;
    }
    
    /**
     * Gets the number of the row where this cellpointer is pointing at.
     */
    public int getRowIndex() {
        return r;
    }
    
    /**
     * Gets the number of the column where this cellpointer is pointing at.
     */
    public int getColumnIndex() {
        return c;
    }
    
    /**
     * Sets the number of cells this cellpointer will move 
     * in the horizontal axis after a call to {@link #move()}.
     * The default is 1.
     * <P>
     * If the movement of this cellpointer is set to
     * MOVE_DOWN or MOVE_UP the value of horizontalStepDistance
     * has no influence on this pointers move-behavior.
     * <P>
     * If the horizontalStepDistance is set to a negative value
     * the pointer will move to the left when movement is set to
     * MOVE_RIGHT and to the right when movement is set to MOVE_LEFT.
     * <P>
     * If the horizontalStepDistance is set to <code>0</code>,
     * the pointer will not move when movement is set to 
     * MOVE_RIGHT or MOVE_LEFT.
     * 
     * @param distance	The number of cells to move in the horizontal
     * 					axis.
     * @see #setMovement(int)
     */
    public void setHorizontalStepDistance(int distance) {
        hStep = distance;
    }
    
    /**
     * Gets the horizontalStepDistance.
     */
    public int getHorizontalStepDistance() {
        return hStep;
    }
    
    /**
     * Sets the number of cells this cellpointer will move 
     * in the vertical axis after a call to {@link #move()}.
     * The default is 1.
     * <P>
     * If the movement of this cellpointer is set to
     * MOVE_RIGHT or MOVE_LEFT the value of verticalStepDistance
     * has no influence on this pointers move-behavior.
     * <P>
     * If the verticalStepDistance is set to a negative value
     * the pointer will move up when movement is set to
     * MOVE_DOWN and down when movement is set to MOVE_UP.
     * <P>
     * If the verticalStepDistance is set to <code>0</code>,
     * the pointer will not move when movement is set to 
     * MOVE_DOWN or MOVE_UP.
     * 
     * @param distance	The number of cells to move in the vertical
     * 					axis.
     * @see #setMovement(int)
     */
    public void setVerticalStepDistance(int distance) {
        vStep = distance;
    }
    
    /**
     * Gets the verticalStepDistance.
     */
    public int getVerticalStepDistance() {
        return vStep;
    }
    
    /**
     * Sets the direction this cellpointer will move after a call to
     * {@link #move()}. The default is MOVE_RIGHT.
     * 
     * @param moveConst	One of CellPointer's MOVE_RIGHT, MOVE_LEFT, MOVE_DOWN
     * 					or MOVE_UP values.
     * @throws IllegalArgumentException at values less than 0 or greater then 3.
     */
    public void setMovement(int moveConst) {
        switch (moveConst) {
        	case MOVE_RIGHT: hMove = 1; vMove = 0; break;
        	case MOVE_LEFT: hMove = -1; vMove = 0; break;
        	case MOVE_DOWN: hMove = 0; vMove = 1; break;
        	case MOVE_UP: hMove = 0; vMove = -1; break;
        	default: throw new IllegalArgumentException(
        	        moveConst + ". Legal values are 0, 1, 2 and 3.");
        }
    }
    
    /**
     * Gets the direction into which this cellpointer will move. The returned int will
     * be equal to one of CellPointer's MOVE_RIGHT, MOVE_LEFT, MOVE_DOWN
     * or MOVE_UP values.
     */
    public int getMovement() {
        if (hMove == 1) return MOVE_RIGHT;
        if (hMove == -1) return MOVE_LEFT;
        if (vMove == 1) return MOVE_DOWN;
        return MOVE_UP;
    }
    
    /**
     * Moves this cellpointer. The direction of the movement depends on the 
     * setting of {@link #setMovement(int)}. How many cells the pointer will
     * move depends on the setting of the step distance.
     * 
     * @see #setMovement(int)
     * @see #setHorizontalStepDistance(int)
     * @see #setVerticalStepDistance(int)
     */
    public void move() {
        c += (hStep * hMove);
        r += (vStep * vMove);
    }
    
    /**
     * Moves this cellpointer to a new position relative to it's old position.
     * If this cellpointer was previously at oldR, oldC, the new position will 
     * be at oldR + rows, oldC + columns.
     */
    public void move(int rows, int columns) {
        r += rows;
        c += columns;
    }
    
    /**
     * Moves this cellpointer to the specified row and column number.
     */
    public void moveTo(int row, int column) {
        r = row;
        c = column;
    }
    
    /**
     * Specifies whether this cellpointer is within the bounds of the
     * spreadsheet.
     */
    public boolean isWithinSheet() {
        return c >= firstColumn && c <= lastColumn 
        	&& r >= firstRow && r <= lastRow;
    }
    
    /**
     * Translates the position of this CellPointer into an 
     * absolute R1C1-reference string.
     * 
     * @return The position of this CellPointer as an absolute R1C1-reference string.
     */
    public String getAbsoluteAddress() {
        StringBuffer sb = new StringBuffer("R");
        sb.append(r);
        sb.append("C");
        sb.append(c);
        return sb.toString();
    }
    
    /**
     * Gets the relative location of this CellPointer seen from a cell
     * at the intersection of the given <code>row</code> and <code>column</code>.
     * Or, to say it in another way,
     * calculates the offset of this CellPointers rowIndex and ColumnIndex
     * from <code>row</code> and 
     * <code>column</code> and returns this as a relative R1C1-reference string.
     * <P>
     * Given a Cell cf which is at row 15 and column 12. The cellPointer is at
     * row 10, column 10.
     * <PRE>
     *          cf.setFormula("=" + cellPointer.getRelativeAddress(15, 12);
     * </PRE>
     * leads to the formula <code>=R[-5]C[-2]</code>
     * and leaves the formula pointing to the cell at the position 
     * of the CellPointer.
     * 
     * @return The offset of this CellPointer as a relative R1C1-reference string.
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

}
