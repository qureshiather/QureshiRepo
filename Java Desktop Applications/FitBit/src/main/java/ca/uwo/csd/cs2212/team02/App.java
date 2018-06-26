
package ca.uwo.csd.cs2212.team02;


import javax.swing.SwingUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

    private static boolean testMode = false;

    static Logger logger = LogManager.getLogger(App.class.getName());

    /**
     * Main function
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-test")) {
                testMode = true;
            } else {
                System.err.println("Invalid Arguments");
                System.exit(1);
            }
        }
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                /*
                * Check if there are any previously saved user settings file saved as
                * "user.config", if so load these settings back when creating mainWindow\
                */
                try {
                    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            javax.swing.UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }
                } catch (ClassNotFoundException ex) {
                    java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                    java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }

                MainWindow window = new MainWindow();
                window.setVisible(true);
            }
        });
    }


    /**
     * Check whether or not the program is in test mode
     *
     * @return testMode     boolean that indicates the mode
     */
    public static boolean isTestMode() {

        return testMode;
    }
}
