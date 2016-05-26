package ca.uwo.csd.cs2212.team02;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.*;

/**
 * A JPanel Class used to display the health meta score
 */


public class UIHRZ extends JLayeredPane {


    MainWindow parentWindow;
    Date userDate;

    private javax.swing.JLabel FatBurnField;
    private javax.swing.JLabel OutRangeField;
    private javax.swing.JLabel CardioField;
    private javax.swing.JLabel PeakField;
    private javax.swing.JLabel suggestionField;
    private javax.swing.JLabel TitleText;
    private javax.swing.JLabel suggestionText;
    private javax.swing.JLabel DistancePic;
    private javax.swing.JLabel FloorsPic;
    private javax.swing.JLabel StepsPic;
    private javax.swing.JLabel Descriptions;
    private JButton save, info;
    private JLayeredPane main;
    private int panelW, panelH, xcenter, ycenter, containerWidth, containerHeight;

    /**
     * Constructor class
     *
     * @param mainwindow the parent mainwindow that will contain the JPanel
     */

    public UIHRZ(MainWindow mainwindow, Date userDate) {
        this.userDate = userDate;
        this.parentWindow = mainwindow;
        this.panelW = new Integer(this.parentWindow.getSize().width - this.parentWindow.getSideWidth() - this.parentWindow.getMinMaxDiff());
        this.panelH = new Integer(this.parentWindow.getSize().height - this.parentWindow.getTopHeight());
        this.setPreferredSize(new Dimension(panelW, panelH));
        this.containerWidth = this.panelW - 10;
        this.containerHeight = this.panelH - 10;
        this.xcenter = containerWidth / 2;
        this.ycenter = containerHeight / 2;

        initUI();
    }

    /**
     * Launch the UI
     */
    private void initUI() {
        FatBurnField = new javax.swing.JLabel();
        OutRangeField = new javax.swing.JLabel();
        CardioField = new javax.swing.JLabel();
        suggestionField = new javax.swing.JLabel();
        suggestionText = new javax.swing.JLabel();
        TitleText = new javax.swing.JLabel();
        PeakField = new javax.swing.JLabel();
        Descriptions = new javax.swing.JLabel();


        setLayout(null);
        //SET TITLE TEXT

        TitleText.setFont(new java.awt.Font("Lucida Grande", 0, 48)); // NOI18N
        TitleText.setText("Heart Rate");
        TitleText.setForeground(new Color(37, 31, 25));
        add(TitleText);
        TitleText.setBounds(this.parentWindow.getMinMaxDiff(), ycenter / 18, this.panelW / 4, 150);
        FontSize(TitleText);

        if(parentWindow.isConnected()) {
            if (HeartRate.getResting() > 0) {
                int hrValues[] = HeartRate.getHRMins();
                String hrNames[] = HeartRate.getNames();
                GraphCircle gc = new GraphCircle(100, (int) HeartRate.getResting(), "BPM", -1);
                try {
                    BufferedImage a = gc.createImage();
                    JLabel temp = new JLabel(new ImageIcon(a.getScaledInstance(this.panelW / 4, this.panelW / 4, java.awt.Image.SCALE_SMOOTH)));
                    temp.setBounds(xcenter + 100, 0, this.panelW / 4, this.panelW / 4);
                    add(temp);
                } catch (Exception e) {
                }



                //set descriptions
                Descriptions.setFont(new java.awt.Font("monospaced", 0, 14)); // NOI18N
                Descriptions.setText("<html><br>Heart rate zones can help you optimize your workout by targeting different training intensities.<br><br>"
                        + "The default zones are calculated using your estimated maximum heart rate. <br>");
                add(Descriptions);
                Descriptions.setBounds(this.parentWindow.getMinMaxDiff(), ycenter / 4, this.panelW * 2, 300); // set the initial label to resize font
                FontSize(Descriptions); // resize font
                Descriptions.setBounds(this.parentWindow.getMinMaxDiff(), ycenter / 5, this.panelW / 2, 400); // resize label accordingly
                //DISTANCE FIELD + PICTUREStepField.setText(hrNames[0]);
                FontSize(suggestionField);
                OutRangeField.setText(hrNames[0]);
                OutRangeField.setFont(new Font("monospaced", Font.PLAIN, 10));
                OutRangeField.setForeground(new Color(37, 31, 25));
                add(OutRangeField);
                OutRangeField.setBounds(this.panelW / 4, (int) (ycenter * 1.5) - 125, (int) this.panelW / 3, 45);
                FontSize(OutRangeField);

                //DISTANCE FIELD
                FatBurnField.setText(hrNames[1]);
                FatBurnField.setFont(new Font("monospaced", Font.PLAIN, 10));
                FatBurnField.setForeground(new Color(37, 31, 25));
                add(FatBurnField);
                FatBurnField.setBounds(this.panelW / 4, (int) ((ycenter * 1.5) - 115 + (this.panelH / (4 * 3))), (int) this.panelW / 5, 45);
                FontSize(FatBurnField);

                //FLOORS FIELD + PICTURE

                CardioField.setText(hrNames[2]);
                CardioField.setFont(new Font("monospaced", Font.PLAIN, 10));
                CardioField.setForeground(new Color(37, 31, 25));
                add(CardioField);
                CardioField.setBounds(this.panelW / 4, (int) ((ycenter * 1.5) - 105 + (this.panelH / (4 * 3)) * 2), (int) this.panelW / 7, 45);
                FontSize(CardioField);

                PeakField.setText(hrNames[3]);
                PeakField.setFont(new Font("monospaced", Font.PLAIN, 10));
                PeakField.setForeground(Color.black);
                add(PeakField);
                PeakField.setBounds(this.panelW / 4, (int) ((ycenter * 1.5) - 95 + (this.panelH / (4 * 3)) * 3), (int) this.panelW / 10, 45);
                FontSize(PeakField);

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

                this.info = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Info.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
                this.info.setContentAreaFilled(false);
                this.info.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/InfoPressed.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
                this.info.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/InfoRollover.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
                this.info.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        showInfo();
                    }
                });
                this.add(this.info);
                this.info.setBounds(this.panelW - 10 - (this.panelH / 15), (this.panelH / 15 * 7 / 2) + 30, this.panelH / 15, this.panelH / 15);

                JComponent jc = new HRZBarChartComponent(this.panelW, this.panelH);
                add(jc);
                jc.setBounds(xcenter - (this.panelW / 20), (int) this.panelH / 9 * 5 - 10, this.panelW, this.panelH);
            } else {
                new PopUpWindow("Try again later on in the day", "Heart data not yet available");
            }
        }
        else{
            new PopUpWindow("Please check your internet connection", "Cannot load heart rate data");
        }

    }

    /**
     * Resizes label to match font
     *
     * @param label to be resized
     */
    public void FontSize(JLabel label) {
        Font labelFont = label.getFont();
        String labelText = label.getText();

        int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
        int componentWidth = label.getWidth();

        // Find out how much the font can grow in width.
        double widthRatio = (double) componentWidth / (double) stringWidth;

        int newFontSize = (int) (labelFont.getSize() * widthRatio);
        int componentHeight = label.getHeight();

        // Pick a new font size so it will not be larger than the height of label.
        int fontSizeToUse = Math.min(newFontSize, componentHeight);


        // Set the label's font size to the newly determined size.
        label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
    }


    private void showInfo() {
        new Legend(this.parentWindow, new File("src/main/resources/images/HRInfo.png"));
    }
}
