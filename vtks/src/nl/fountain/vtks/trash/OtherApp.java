/*
 * Created on Jun 7, 2005
 * 
 *
 */
package nl.fountain.vtks.trash;

/**
 *
 */

    import java.awt.BorderLayout;
    import java.awt.event.* ;
    import java.io.* ;
    import javax.swing.* ;
     
    import sun.awt.shell.ShellFolder;
     
    /**
     * @author Little Dany
     */
    public class OtherApp
            extends JFrame {
        
        private JFileChooser fc ;
        
        private OtherApp() {
            super() ;
            setTitle( "Running Other Application" ) ;
            setDefaultCloseOperation( EXIT_ON_CLOSE ) ;
            init() ;
            setSize( 400 , 300 ) ;
            setVisible( true ) ;
        }
        
        private void init() {
            fc = new JFileChooser() ;
            fc.setApproveButtonText( "Execute" ) ;
            getContentPane().add( new JButton( new ButAct() ) , BorderLayout.SOUTH ) ;
        }
        
        public class ButAct extends AbstractAction {
            public ButAct() {
                super( "Button" ) ;
            }
            public void actionPerformed ( ActionEvent actEvt ) {
                int r = fc.showOpenDialog( OtherApp.this ) ;
                if( r != JFileChooser.APPROVE_OPTION )
                    return ;
                openFile( fc.getSelectedFile() ) ;
            }
            private void openFile( File file ) {
                Runtime r = Runtime.getRuntime() ;
                String cmd = getExecutable( file ) ;
                if( cmd == null )
                    cmd = file.getPath() ;
                else
                    cmd = cmd + " \"" + file.getPath() + "\"" ;
                try {
                    r.exec( cmd ) ;
                } catch ( IOException ioe ) {
                    ioe.printStackTrace() ;
                }
            }
            private String getExecutable( File file ) {
                try {
                    ShellFolder sf = ShellFolder.getShellFolder( file ) ;
                    String s = sf.getExecutableType() ;
                    if( s != null )
                        return s ;
                } catch ( FileNotFoundException fnfe ) {
                    fnfe.printStackTrace() ;
                }
                String ext = getFileExtention( file ) ;
                if( ext == null || ext.equals( "txt" ) )
                    return "notepad" ;
                else if( ext.equals( "doc" ) || ext.equals( ".rtf" ) )
                    return "winword" ;
                else if( ext.equals( ".ppt" ) || ext.equals( ".pps" ) )
                    return "powerpnt" ;
                // Add more else if for handle more files like pdf
                // return value is the path of the executable
                // ex. return "C:\\program files\\notepad.exe" ;
                // ex. return "notepad" ; // if notepad.exe in the path variable.
                return null ;
            }
            private String getFileExtention( File file ) {
                String name = file.getName() ;
                int id = name.lastIndexOf( '.' ) ;
                if( id == -1 || id == name.length() - 1 )
                    return null ;
                return name.substring( id + 1 ) ;
            }
        }
        
        public static void main ( String[] args ) {
            SwingUtilities.invokeLater( new Runnable() {
                public void run () {
                    new OtherApp() ;
                }
            } ) ;
        }


}
