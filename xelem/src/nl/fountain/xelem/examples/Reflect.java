/*
 * Created on 12-nov-2004
 *
 */
package nl.fountain.xelem.examples;

import java.io.File;

import nl.fountain.xelem.excel.Cell;
import nl.fountain.xelem.excel.ss.SSCell;





public class Reflect {

    public static void main(String[] args) {
        Cell cell = new SSCell();
        new Reflect().reflectUpon(cell);
    }
    
    public void reflectUpon(Object o) {
        String pName = o.getClass().getPackage().getName();
        System.out.println(pName);
        File pack = new File(pName);
        System.out.println(pack.getAbsolutePath());

    }

}
