/*
 * Created on 22-mrt-2005
 *
 */
package nl.fountain.xelem.ztest;



/**
 *
 */
public class Test {
    
    private static int COLUMN_BASE = 26;

    public static void main(String[] args) {
        
//        for (int i = 1; i < 5; i++) {
//            String column = Test.calculateColumn(i);
//            int c = Test.calculateColumn(column);
//            System.out.println(i + " " + column + " " + c + " " + (c==i));
//        }
       System.out.println(Integer.MAX_VALUE);
       System.out.println(Test.calculateColumn("FXSHRXW")); 
       System.out.println(Test.calculateColumn(Integer.MAX_VALUE));
       
    }
    
    public static int calculateColumn(String letters) {
        String s = letters.toUpperCase();
        int colnr = 0;
        int factor = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (Character.isLetter(ch)) {
                colnr += (ch - 64) * factor;
                factor *= COLUMN_BASE;
            }
        }
        return colnr;
    }
    
    public static String calculateColumn(int c) {
        int div = 1;
        int af = 0;
        StringBuffer sb = new StringBuffer();
        int r;
        while ((r = (c-af)/div) > 0) {
            sb.insert(0, getLSD(r));
            af += div;
            div *= COLUMN_BASE;            
        }
        return sb.toString();
    }
    
    private static char getLSD(int c) {
        int lsd = c % COLUMN_BASE;
        if (lsd == 0) lsd = COLUMN_BASE;
        return (char) (lsd + 64);
    }
}
