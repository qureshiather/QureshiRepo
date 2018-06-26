package ca.uwo.csd.cs2212.team02;

import com.github.scribejava.core.exceptions.OAuthException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

/**
 * A JPanel class used to display the data for the user's lifetime totals for:
 * Distance, Floors, and Steps.
 */
public class UILifeTime extends JLayeredPane {


    MainWindow parentWindow;
    Date userDate;

    private javax.swing.JLabel DistanceField;
    private javax.swing.JLabel StepField;
    private javax.swing.JLabel FloorField;
    private javax.swing.JLabel TitleText;
    private javax.swing.JLabel DistancePic;
    private javax.swing.JLabel FloorsPic;
    private javax.swing.JLabel StepsPic;
    private JButton save;
    private JLayeredPane main;
    private int panelW, panelH, xcenter, ycenter, containerWidth, containerHeight;

    /**
     * Constructor class
     *
     * @param mainwindow the parent mainwindow that will contain the JPanel
     */
    public UILifeTime(MainWindow mainwindow, Date userDate) {
        this.userDate = userDate;
        this.parentWindow = mainwindow;
        this.panelW = new Integer(this.parentWindow.getSize().width - this.parentWindow.getSideWidth() - this.parentWindow.getMinMaxDiff());
        this.panelH = new Integer(this.parentWindow.getSize().height - this.parentWindow.getTopHeight());
        this.setPreferredSize(new Dimension(panelW, panelH));
        this.containerWidth = this.panelW - 10;
        this.containerHeight = this.panelH - 10;
        this.xcenter = containerWidth / 2;
        this.ycenter = containerHeight / 2;

        DistanceField = new javax.swing.JLabel();
        StepField = new javax.swing.JLabel();
        FloorField = new javax.swing.JLabel();
        TitleText = new javax.swing.JLabel();

        //SET TITLE TEXT
        TitleText.setFont(new java.awt.Font("Lucida Grande", 0, 60)); // NOI18N
        TitleText.setText("Lifetime Totals");
        TitleText.setForeground(new Color(37, 31, 25));
        add(TitleText);
        TitleText.setBounds(xcenter - this.parentWindow.getMinMaxDiff() * 3 / 2, ycenter / 18, this.panelW / 3, 150);

        if(mainwindow.isConnected()) {
            //get data from Api classes
            int floorLT = (int) Math.round(mainwindow.getDashboard().getDataFloors().getLifeTime());
            int stepsLT = (int) Math.round(mainwindow.getDashboard().getDataSteps().getLifeTime());
            double distanceLT = this.parentWindow.getDashboard().round(mainwindow.getDashboard().getDataDistance().getLifeTime(), 2);


            //DISTANCE FIELD + PICTURE
            DistanceField.setText("Distance:   " + distanceLT + "km");
            DistanceField.setFont(new Font("monospaced", Font.PLAIN, 40));
            DistanceField.setForeground(Color.black);
            add(DistanceField);
            DistanceField.setBounds(xcenter - xcenter / 5, ycenter - ycenter / 3, 800, 200);

            DistancePic = new JLabel(new ImageIcon(((new ImageIcon("src/main/resources/images/DistanceIcon.png")).getImage()).getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)));
            add(DistancePic);
            DistancePic.setBounds(xcenter - xcenter / 4 - xcenter / 3, ycenter - ycenter / 3, 200, 200);

            //FLOORS FIELD + PICTURE
            FloorField.setText("Floors:     " + floorLT + " floors");
            FloorField.setFont(new Font("monospaced", Font.PLAIN, 40));
            FloorField.setForeground(Color.black);
            add(FloorField);
            FloorField.setBounds(xcenter - xcenter / 5, ycenter, 800, 200);

            FloorsPic = new JLabel(new ImageIcon(((new ImageIcon("src/main/resources/images/FloorIcon.png")).getImage()).getScaledInstance(130, 130, java.awt.Image.SCALE_SMOOTH)));
            add(FloorsPic);
            FloorsPic.setBounds(xcenter - xcenter / 4 - xcenter / 3 + 30, ycenter + 50, 130, 130);


            //STEPS FIELD + PICTURE
            StepField.setText("Steps:      " + stepsLT + " steps");
            StepField.setFont(new Font("monospaced", Font.PLAIN, 40));
            StepField.setForeground(Color.black);
            add(StepField);
            StepField.setBounds(xcenter - xcenter / 5, ycenter + ycenter / 3, 800, 200);

            StepsPic = new JLabel(new ImageIcon(((new ImageIcon("src/main/resources/images/stepsicon.png")).getImage()).getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)));
            add(StepsPic);
            StepsPic.setBounds(xcenter - xcenter / 4 - xcenter / 3 + 30, ycenter + ycenter / 3 + 40, 150, 150);
            //exportPDF information
            main = this;
            this.save = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Download.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
            this.save.setContentAreaFilled(false);
            this.save.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/DownloadPressed.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
            this.save.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/DownloadRollover.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
            this.save.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    parentWindow.createPDF(main);
                }
            });
            this.add(this.save);
            this.save.setBounds(this.panelW - 10 - (this.panelH / 15), (this.panelH / 15 * 5 / 2) + 20, this.panelH / 15, this.panelH / 15);
            this.save.setToolTipText("Export Data as PDF");
        }
        else{
            new PopUpWindow("Please check your internet connection", "Cannot load lifetime data");
        }


    }// </editor-fold>





}
