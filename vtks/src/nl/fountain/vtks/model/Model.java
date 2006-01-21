/*
 * Created on Jun 7, 2005
 * 
 *
 */
package nl.fountain.vtks.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class Model {
    
    //private static Logger logger = Logger.getLogger(Model.class.getName());
    
    private Long model_id;
    private Set<Kolomkop> kolomkoppen = new HashSet<Kolomkop>();
    
    public Model() {}
    
    public Long getId() {
        return model_id;
    }

    @SuppressWarnings("unused")
	private void setId(Long id) {
        model_id = id;
    }

    public Set<Kolomkop> getKolomkoppen() {
        return kolomkoppen;
    }
    
    // nodig voor hibernate
    void setKolomkoppen(Set<Kolomkop> kolomkoppen) {
        this.kolomkoppen = kolomkoppen;
    }
    
    public void addKolomkop(Kolomkop kop) {
        kop.setModel(this);
        kolomkoppen.add(kop);
    }
    
    public void removeKolomkop(Kolomkop kop) {
        kop.setModel(null);
        kolomkoppen.remove(kop);
    }


    
    
    

}
