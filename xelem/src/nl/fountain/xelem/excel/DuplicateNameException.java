/*
 * Created on 17-okt-2004
 *
 */
package nl.fountain.xelem.excel;

/**
 * Indicates that a certain name allready exists in an otherwise unique name-sequence.
 */
public class DuplicateNameException extends Exception {
    
    public DuplicateNameException() {}
    
    public DuplicateNameException(String message) {
        super(message);
    }

}
