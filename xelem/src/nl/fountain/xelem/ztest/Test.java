/*
 * Created on 22-mrt-2005
 *
 */
package nl.fountain.xelem.ztest;

import java.math.BigDecimal;

/**
 *
 */
public class Test {

    public static void main(String[] args) {
        Number n = new BigDecimal(123456.789);
        System.out.println(n.toString().substring(0, 31));
        System.out.println(Double.MAX_VALUE -1);
        BigDecimal d = new BigDecimal(1123);
        d.toString();
    }
}
