/*
 * Created on Jun 3, 2005
 * 
 *
 */
package nl.fountain.vtks.model;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

/**
 *
 */
public class Kolomkop extends AbstractKop {
    
    static Logger logger = Logger.getLogger(Kolomkop.class.getName());
    
    public static final String TYPE_RIJ = "RIJ";
    public static final String TYPE_DATA = "DATA";
    public static final String TYPE_META = "META";
    public static final String TYPE_LEEG = "LEEG";
    
    private String type;
    private Model model;
    private Kolomkop parent;
    private Set<Kolomkop> children = new TreeSet<Kolomkop>();
    
    public Kolomkop() {}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null
                || "".equals(type)
                || TYPE_RIJ.equals(type)
                || TYPE_DATA.equals(type)
                || TYPE_META.equals(type)
                || TYPE_LEEG.equals(type)) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("'" + type + "'" 
                    + ", legal values: '" 
                    + TYPE_RIJ + "', '" 
                    + TYPE_DATA + "', '"
                    + TYPE_META + "', '"
                    + TYPE_LEEG + "'.");
        }
    }

    public Kolomkop getParent() {
        return parent;
    }
    
    public boolean hasParent() {
        return parent != null;
    }

    public Set<Kolomkop> getChildren() {
        return children;
    }
    
    // nodig voor Hibernate
    void setChildren(Set<Kolomkop> children) {
        this.children = children;
    }
    
    public void addChild(Kolomkop kid) {
        if (kid.hasParent() && !kid.getParent().equals(this)) {
            kid.getParent().removeChild(kid);
        }
        kid.setParent(this);
        children.add(kid);
    }
    
    void setParent(Kolomkop parent) {
        this.parent = parent;
    }
    
    public void removeChild(Kolomkop kid) {
        kid.setParent(null);
        children.remove(kid);
    }   

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(this.getClass().getName());
        
        sb.append(" [leftMostColumn=");
        sb.append(getLeftMostColumn());
        
        sb.append(" rightMostColumn=");
        sb.append(getRightMostColumn());
        
        sb.append(" caption=");
        sb.append(getCaption());
        
        sb.append(" type=");
        sb.append(getType());
        
        sb.append(" comment=");
        sb.append(getComment());
        
        sb.append(" note=");
        sb.append(getNote());
        
        sb.append("]");
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Kolomkop)) return false;
        if (!toString().equals(obj.toString())) return false;
        
        // is the offspring equal?
        Kolomkop other = (Kolomkop)obj;
        if (other.children.size() != children.size()) return false;
        
        Iterator<Kolomkop> iterThis = children.iterator();
        Iterator<Kolomkop> iterOther = other.children.iterator();
        
        for (int i = 0; i < children.size(); i++) {
            if (!iterThis.next().equals(iterOther.next())) return false; 
        }
        
        return true;
    }

}
