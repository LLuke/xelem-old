/*
 * Created on 23-nov-2005
 * 
 *
 */
package nl.fountain.vtks.model;

import java.util.Comparator;

/**
 *
 */
public class KopComparator implements Comparator {

    // @see java.util.Comparator#compare(T, T)
    public int compare(Object o1, Object o2) {
        return o1.toString().compareTo(o2.toString());
    }

}
