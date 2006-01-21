/*
 * Created on Jun 7, 2005
 * 
 *
 */
package nl.fountain.vtks.trash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 *
 */
public class TestOpenExcel {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        runExcel();
        doRunDir();
        doProcess();
    }

    private static void runExcel() throws IOException {
        Runtime.getRuntime().exec("open -a 'C:/Program Files/Microsoft Office 2003/OFFICE11/EXCEL.EXE'");
    }
    
    private static void doRunDir() throws Exception {
        String[] args = { "cmd /c dir" };
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(args);
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        System.out.printf("Output of running %s is:", 
            Arrays.toString(args));
        while ((line = br.readLine()) != null) {
          System.out.println(line);
        }

    }
    
    private static void doProcess() throws IOException {
        String[] args = { "cmd /c dir" };
        Process process = new ProcessBuilder(args).start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        System.out.printf("Output of running %s is:", 
           Arrays.toString(args));
        while ((line = br.readLine()) != null) {
          System.out.println(line);
        }
    }
    

}
