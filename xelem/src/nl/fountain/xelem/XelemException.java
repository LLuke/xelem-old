/*
 * Created on Oct 26, 2004
 *
 */
package nl.fountain.xelem;

/**
 * A wrapper class for several exceptions that may occur while working with the
 * {@link XFactory}.
 */
public class XelemException extends Exception {
    
    public XelemException() {
        super();
    }
    
    public XelemException(String message) {
        super(message);
    }
    
    public XelemException(Throwable cause) {
        super(cause);
    }

}
