/*
 * Created on Sep 8, 2004
 *
 */
package nl.fountain.xelem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A utility class for xelem.
 */
public class XLUtil {
       
    private static DateFormat xldfD;
    private static DateFormat xldfT;
    
    // this class has only static methods.
    private XLUtil() {}
    
    /**
     * Converts rgb to a string. Like <code><i>#ff00ba</i></code>
     * 
     * @param 	r	the value of red (0 - 255)
     * @param 	g	the value of green (0 - 255)
     * @param 	b	the value of blue (0 - 255)
     * 
     * @return 	A String starting with <code>#</code>
     * 			+ the two-digit hex-code for 
     * 			<code>r</code>, <code>g</code> and <code>b</code>.
     */
    public static String convertToHex(int r, int g, int b) {
        StringBuffer sb = new StringBuffer("#");
        String red = Integer.toHexString(toByte(r));
        sb.append(red.length() < 2 ? "0" + red : red);
        String green = Integer.toHexString(toByte(g));
        sb.append(green.length() < 2 ? "0" + green : green);
        String blue = Integer.toHexString(toByte(b));
        sb.append(blue.length() < 2 ? "0" + blue : blue);
        return sb.toString();
    }
    
    private static int toByte(int i) {
        if (i < 0) i = 0;
        if (i > 255) i = 255;
        return i;
    }
    
    /**
     * Formats a Date into a Date-Time value used by SpreadsheetML.
     * 
     * @param 	date 	The date to be formatted.
     * 
     * @return A string of format <code>yyyy-MM-ddTHH:mm:ss.SSS</code>.
     */
    public static String format(Date date) {
        StringBuffer sb = new StringBuffer(getDateFormat().format(date));
        sb.append("T");
        sb.append(getTimeFormat().format(date));
        return sb.toString();
    }
    
    private static DateFormat getDateFormat() {
        if (xldfD == null) {
            xldfD = new SimpleDateFormat("yyyy-MM-dd");
        }
        return xldfD;
    }
    
    private static DateFormat getTimeFormat() {
        if (xldfT == null) {
           xldfT = new SimpleDateFormat("HH:mm:ss.SSS"); 
        }
        return xldfT;
    }

}
