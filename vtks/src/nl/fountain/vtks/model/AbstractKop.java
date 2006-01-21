/*
 * Created on May 19, 2005
 *
 */
package nl.fountain.vtks.model;


/**
 *
 */
public abstract class AbstractKop implements Kop {
    
    private Long kop_id;
    private String caption;
    private int leftMostColumn;
    private int rightMostColumn;
    private String comment;
    private String note;
    
    //private static Logger logger = Logger.getLogger(AbstractKop.class.getName());
    
    AbstractKop(){}

    @SuppressWarnings("unused")
	private void setId(Long id) {
        kop_id = id;
    }

    public Long getId() {
        return kop_id;
    }

    public void setCaption(String naam) {
        caption = naam;
    }

    public String getCaption() {
        return caption;
    }

    public void setLeftMostColumn(int index) {
        leftMostColumn = index;
    }

    public int getLeftMostColumn() {
        return leftMostColumn;
    }

    public void setRightMostColumn(int index) {
        rightMostColumn = index;
    }

    public int getRightMostColumn() {
        return rightMostColumn;
    }

    public int getSpan() {
        return rightMostColumn - leftMostColumn;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }
    
    public int compareTo(Object o) {
        return toString().compareTo(o.toString());
    }

}
