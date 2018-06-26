package ca.uwo.csd.cs2212.team02;

import java.awt.*;
import java.text.ParseException;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A JPanel class used to display the data for the user's best days for:
 * Distance, Floors, and Steps.
 */
public class UIBestDays extends JLayeredPane {

    private MainWindow parentWindow;
    private Date userDate;
    private JButton save;
    private javax.swing.JLabel distanceDayField;
    private javax.swing.JLabel stepDayField;
    private javax.swing.JLabel floorDayField;
    private javax.swing.JLabel TitleText;
    private javax.swing.JLabel DistancePic;
    private javax.swing.JLabel FloorsPic;
    private javax.swing.JLabel StepsPic;
    private JLayeredPane main;
    private int panelW, panelH, xcenter, ycenter, containerWidth, containerHeight;
    private SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");

    /**
     * Constructor class
     *
     * @param mainwindow the parent mainwindow that will contain the JPanel
     */
    public UIBestDays(MainWindow mainwindow, Date userDate) {
        this.userDate = userDate;
        this.parentWindow = mainwindow;
        this.panelW = new Integer(this.parentWindow.getSize().width - this.parentWindow.getSideWidth() - this.parentWindow.getMinMaxDiff());
        this.panelH = new Integer(this.parentWindow.getSize().height - this.parentWindow.getTopHeight());
        this.setPreferredSize(new Dimension(panelW, panelH));
        this.containerWidth = this.panelW - 10;
        this.containerHeight = this.panelH - 10;
        this.xcenter = containerWidth / 2;
        this.ycenter = containerHeight / 2;

        TitleText = new javax.swing.JLabel();
        distanceDayField = new javax.swing.JLabel();
        stepDayField = new javax.swing.JLabel();
        floorDayField = new javax.swing.JLabel();

        TitleText.setFont(new java.awt.Font("Lucida Grande", 0, 60)); // NOI18N
        TitleText.setText("Best Days");
        TitleText.setForeground(new Color(37, 31, 25));
        add(TitleText);
        TitleText.setBounds(xcenter - this.parentWindow.getMinMaxDiff() * 3 / 2, ycenter / 18, this.panelW / 4, 150);

        if(mainwindow.isConnected()) {
            BestDay bestFloor = mainwindow.getDashboard().getDataFloors().getBestDay();
            String floorDay = dateParser(bestFloor.getDay());
            int floorVal = (int) Math.round(bestFloor.getValue());

            DataType steps = mainwindow.getDashboard().getDataSteps();
            BestDay bestSteps = steps.getBestDay();
            String stepsDay = dateParser(bestSteps.getDay());
            int stepsVal = (int) Math.round(bestSteps.getValue());

            DataType distance = mainwindow.getDashboard().getDataDistance();
            BestDay bestDistance = distance.getBestDay();

            String distanceDay = dateParser(bestDistance.getDay());
            double distanceVal = this.parentWindow.getDashboard().round(bestDistance.getValue(), 2);




            //DISTANCE + ICON
            distanceDayField.setText(distanceDay + ": " + distanceVal + " Km");
            distanceDayField.setFont(new Font("monospaced", Font.PLAIN, 40));
            distanceDayField.setForeground(Color.black);
            add(distanceDayField);
            distanceDayField.setBounds(xcenter - xcenter / 5, ycenter - ycenter / 3, 700, 200);

            DistancePic = new JLabel(new ImageIcon(((new ImageIcon("src/main/resources/images/DistanceIcon.png")).getImage()).getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)));
            add(DistancePic);
            DistancePic.setBounds(xcenter - xcenter / 4 - xcenter / 3, ycenter - ycenter / 3, 200, 200);

            //FLOORS + ICON
            floorDayField.setText(floorDay + ": " + floorVal + " Floors");
            floorDayField.setFont(new Font("monospaced", Font.PLAIN, 40));
            floorDayField.setForeground(Color.black);
            add(floorDayField);
            floorDayField.setBounds(xcenter - xcenter / 5, ycenter, 700, 200);

            FloorsPic = new JLabel(new ImageIcon(((new ImageIcon("src/main/resources/images/FloorIcon.png")).getImage()).getScaledInstance(130, 130, java.awt.Image.SCALE_SMOOTH)));
            add(FloorsPic);
            FloorsPic.setBounds(xcenter - xcenter / 4 - xcenter / 3 + 30, ycenter + 50, 130, 130);

            //STEPS + ICON
            stepDayField.setText(stepsDay + ": " + stepsVal + " Steps");
            stepDayField.setFont(new Font("monospaced", Font.PLAIN, 40));
            stepDayField.setForeground(Color.black);
            add(stepDayField);
            stepDayField.setBounds(xcenter - xcenter / 5, ycenter + ycenter / 3, 700, 200);

            StepsPic = new JLabel(new ImageIcon(((new ImageIcon("src/main/resources/images/stepsicon.png")).getImage()).getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH)));
            add(StepsPic);
            StepsPic.setBounds(xcenter - xcenter / 4 - xcenter / 3 + 30, ycenter + ycenter / 3 + 40, 150, 150);

            //export pdf
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
            new PopUpWindow("Please check your internet connection", "Cannot load best day data");
        }


    }

    /**
     * Takes a String in the format YYYY-MM-DD, and returns a string like Weekday - Month - Day - Year
     *
     * @param badDate date to be changed
     * @return properly formatted date
     */
    private String dateParser(String badDate) {
        SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat toFormat = new SimpleDateFormat("MMM dd, yyyy");
        String goodDate = null;
        try {
            java.util.Date date = fromFormat.parse(badDate);
            goodDate = toFormat.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(UIBestDays.class.getName()).log(Level.SEVERE, null, ex);
        }
        return goodDate;
    }

}



