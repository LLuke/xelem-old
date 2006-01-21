/*
 * Created on May 19, 2005
 *
 */
package nl.fountain.vtks.model;

/**
 *
 */
public interface Kop extends Comparable{
    
    Long getId();
    
    void setCaption(String naam);
    String getCaption();
    
    void setLeftMostColumn(int index);
    int getLeftMostColumn();
    void setRightMostColumn(int index);
    int getRightMostColumn();
    int getSpan();
    
    void setComment(String comment);
    String getComment();
    
    void setNote(String note);
    String getNote();

}
