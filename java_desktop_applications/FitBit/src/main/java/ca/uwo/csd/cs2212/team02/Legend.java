/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uwo.csd.cs2212.team02;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * UI pop up used to give more information about the configuration of daily dashboar
 */
public class Legend<K> extends JFrame {

    private MainWindow mainwindow;

    /**
     * Constructor
     *
     * @param mainwindow mainWindow object
     * @param f          file storing the image of the legend
     */
    public Legend(MainWindow mainwindow, File f) {
        this.mainwindow = mainwindow;
        initUI(f);

        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    /**
     * Draws the legend window
     */
    private void initUI(File f) {
        this.setSize(595, 842);
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(f);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        BackgroundPanel panel = new BackgroundPanel(myImage, BackgroundPanel.SCALED, 0.0f, 0.0f);

        this.setContentPane(panel);
        this.getContentPane().setLayout(null);
        JButton exit = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Exit.png")).getImage()).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
        exit.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/ExitPressed.png")).getImage()).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
        exit.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/ExitRollover.png")).getImage()).getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
        exit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        exit.setContentAreaFilled(false);
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playSound();
                dispose();
            }
        });
        this.add(exit);
        exit.setBounds(525, 10, 55, 55);
    }

    /**
     * Play sound when closing window
     */
    private void playSound() {
        this.mainwindow.playButtonSound();
    }
}
