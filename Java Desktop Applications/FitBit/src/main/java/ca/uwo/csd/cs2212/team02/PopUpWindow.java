package ca.uwo.csd.cs2212.team02;

import javax.swing.*;
import java.awt.*;


/**
 * Popup window class to display all error messages
 */
public class PopUpWindow {

    /**
     * Constructor to create the popup
     *
     * @param message the details of the error
     * @param header  Title of the error
     */
    public PopUpWindow(String message, String header) {

        final JDialog frame = new JDialog();
        frame.setSize(475, 375);
        frame.setLayout(new GridBagLayout());
        //frame.setUndecorated(true);
        frame.setTitle("ERROR - "+header);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        JLabel warningIcon = new JLabel(new ImageIcon(((new ImageIcon("src/main/resources/images/warning.png")).getImage()).getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
        frame.add(warningIcon, constraints);
        
        constraints.gridx++;
        constraints.weightx = 2;
        JLabel headingLabel = new JLabel(header);
        Font font = headingLabel.getFont();
        // same font but bold
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        headingLabel.setFont(boldFont);
        headingLabel.setOpaque(false);
        frame.add(headingLabel, constraints);
        
        
        constraints.gridx = 1;
        constraints.gridy++;
        constraints.weightx = 3;
        //constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        JLabel messageLabel = new JLabel(message);
        frame.add(messageLabel, constraints);
        
        
        constraints.gridx = 2;
        constraints.gridy++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        JButton closeButton = new JButton("Close");

        closeButton.setMargin(new Insets(1, 4, 1, 4));
        closeButton.setFocusable(false);
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frame.dispose();
            }
        });

        frame.add(closeButton, constraints);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setModal(true);
        frame.setAlwaysOnTop(true);
        frame.setModalityType (Dialog.ModalityType.APPLICATION_MODAL);
        frame.setVisible(true);
    }

    /**
     * Constructor for non-error pop ups
     * @param message Details
     * @param header Title of the message
     * @param n Flag
     */
    public PopUpWindow(String message, String header, int n) {

        final JDialog frame = new JDialog();
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());
        frame.setTitle(header);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx++;
        constraints.weightx = 2;
        JLabel headingLabel = new JLabel(header);
        Font font = headingLabel.getFont();
        // same font but bold
        Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
        headingLabel.setFont(boldFont);
        headingLabel.setOpaque(false);
        frame.add(headingLabel, constraints);


        constraints.gridx = 1;
        constraints.gridy++;
        constraints.weightx = 3;
        //constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        JLabel messageLabel = new JLabel(message);
        frame.add(messageLabel, constraints);


        constraints.gridx = 2;
        constraints.gridy++;
        constraints.weightx = 0f;
        constraints.weighty = 0f;
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.NORTH;
        JButton closeButton = new JButton("Close");

        closeButton.setMargin(new Insets(1, 4, 1, 4));
        closeButton.setFocusable(false);
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frame.dispose();
            }
        });

        frame.add(closeButton, constraints);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setModal(true);
        frame.setAlwaysOnTop(true);
        frame.setModalityType (Dialog.ModalityType.APPLICATION_MODAL);
        frame.setVisible(true);
    }
}
