
package ca.uwo.csd.cs2212.team02;

import com.toedter.calendar.JCalendar;

import java.awt.AWTException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * UI for changing the date of the program
 */
public class DatePick extends JDialog {
    JCalendar Cal;

    /**
     * Constructor
     *
     * @param parent   main window
     * @param dataDate currently selected date
     * @param today    today's date
     */
    public DatePick(final MainWindow parent, java.util.Date dataDate, java.util.Date today) {
        this.setResizable(false);
        this.setLayout(new GridBagLayout());
        final java.util.Date TODAY = today;
        GridBagConstraints c = new GridBagConstraints();
        Cal = new JCalendar();
        Cal.setDate(dataDate);
        Cal.setMaxSelectableDate(today);
        c.fill = c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        this.add(Cal, c);

        JButton ok = new JButton("OK");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    if (Cal.getDate().compareTo(TODAY) == 1) {
                        new PopUpWindow("Please select another date!", "Cannot select a date in the future");
                    } else {
                        parent.setDataDate(Cal.getDate());
                    }
                } catch (AWTException ex) {
                    Logger.getLogger(DatePick.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.gridx = 3;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 1;       //third row
        this.add(ok, c);

        JButton cancel = new JButton("CANCEL");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.gridx = 3;
        c.gridwidth = 1;
        c.gridy = 2;
        this.add(cancel, c);
        this.pack();
        this.setSize(500, 350);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setAlwaysOnTop(true);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setVisible(true);

    }

    /**
     * @return selected date
     */
    public java.util.Date getDate() {
        return Cal.getDate();
    }

}
