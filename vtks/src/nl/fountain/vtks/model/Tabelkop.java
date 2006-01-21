/*
 * Created on 13-apr-2005
 *
 */
package nl.fountain.vtks.model;

import org.apache.log4j.Logger;


/**
 *
 */
public class Tabelkop extends AbstractKop {
    
    private String bestandsnaam;
    private String bladnaam;
    private int jaar;
    private Model model;
    
    private static Logger logger = Logger.getLogger(Tabelkop.class.getName());
    
    public Tabelkop() {}
    
    public void setBestandsnaam(String naam) {
        bestandsnaam = naam;
        logger.info(this + " bestandsnaam [" + bestandsnaam + "]");
    }
    
    public String getBestandsnaam() {
        return bestandsnaam;
    }
    
    public void setBladnaam(String naam) {
        bladnaam = naam;
    }
    
    public String getBladnaam() {
        return bladnaam;
    }
    
    public void setJaar(int jaar) {
        this.jaar = jaar;
    }
    
    public int getJaar() {
        return jaar;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
