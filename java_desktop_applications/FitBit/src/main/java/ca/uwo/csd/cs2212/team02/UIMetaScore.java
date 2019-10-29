package ca.uwo.csd.cs2212.team02;

import com.github.scribejava.core.exceptions.OAuthException;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;


/**
 * Class to display health meta score information
 * Includes score, graphs, and suggestion
 */
public class UIMetaScore extends JLayeredPane {


    MainWindow parentWindow;
    Date userDate;

    private javax.swing.JLabel DistanceField;
    private javax.swing.JLabel StepField;
    private javax.swing.JLabel FloorField;
    private javax.swing.JLabel CalorieField;
    private javax.swing.JLabel suggestionField;
    private javax.swing.JLabel TitleText;
    private javax.swing.JLabel suggestionText;
    private JButton save;
    private JLayeredPane main;
    private int panelW, panelH, xcenter, ycenter, containerWidth, containerHeight;

    /**
     * Constructor class
     *
     * @param mainwindow the parent mainwindow that will contain the JPanel
     */

    public UIMetaScore(MainWindow mainwindow, Date userDate) {
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

    }// </editor-fold>

    /**
     * Create the UI screen
     */
    public void initUI() {

        DistanceField = new javax.swing.JLabel();
        StepField = new javax.swing.JLabel();
        FloorField = new javax.swing.JLabel();
        suggestionField = new javax.swing.JLabel();
        suggestionText = new javax.swing.JLabel();
        TitleText = new javax.swing.JLabel();
        CalorieField = new javax.swing.JLabel();


        setLayout(null);

        //SET TITLE TEXT

        TitleText.setFont(new java.awt.Font("Lucida Grande", 0, 48)); // NOI18N
        TitleText.setText("Health Meta Score");
        TitleText.setForeground(new Color(37, 31, 25));
        add(TitleText);
        TitleText.setBounds(this.parentWindow.getMinMaxDiff(), ycenter / 18, this.panelW / 5 * 2, 150);
        FontSize(TitleText);

        //get data from Api classes
        if (parentWindow.isConnected()) {
            int floorLT = (int) Math.round(parentWindow.getDashboard().getDataFloors().getLifeTime());
            int stepsLT = (int) Math.round(parentWindow.getDashboard().getDataSteps().getLifeTime());

            double distanceLT = parentWindow.getDashboard().getDataDistance().getLifeTime();

            HealthMetaScore healthMeta = parentWindow.getDashboard().getDataHealthMeta();
            int metaScore = healthMeta.getScore();
            GraphCircle gc = new GraphCircle((int) (metaScore / 10), metaScore, "Skip", -1);

            try {
                BufferedImage a = gc.createImage();
                JLabel temp = new JLabel(new ImageIcon(a.getScaledInstance(this.panelW / 4, this.panelW / 4, java.awt.Image.SCALE_SMOOTH)));
                temp.setBounds(xcenter + 100, 0, this.panelW / 4, this.panelW / 4);
                add(temp);
            } catch (Exception e) {
            }

            suggestionField.setText("<html>" + healthMeta.suggestion() + "</html>");
            suggestionField.setFont(new Font("monospaced", Font.PLAIN, this.panelW / 50));
            suggestionField.setForeground(new Color(37, 31, 25));
            add(suggestionField);
            suggestionField.setBounds(this.parentWindow.getMinMaxDiff(), ycenter / 3, this.panelW / 2, 200);

            //STEPS FIELD + PICTURE

            StepField.setText("Steps: ");
            StepField.setFont(new Font("monospaced", Font.PLAIN, 10));
            StepField.setForeground(new Color(37, 31, 25));
            add(StepField);
            StepField.setBounds(this.panelW / 4, (int) (ycenter * 1.5) - 115, (int) this.panelW / 3, 45);
            FontSize(StepField);

            //DISTANCE FIELD + PICTURE
            DistanceField.setText("Distance:   ");
            DistanceField.setFont(new Font("monospaced", Font.PLAIN, 10));
            DistanceField.setForeground(new Color(37, 31, 25));
            add(DistanceField);
            DistanceField.setBounds(this.panelW / 4, (int) ((ycenter * 1.5) - 105 + (this.panelH / (4 * 3))), (int) this.panelW / 3, 45);
            FontSize(DistanceField);

            //FLOORS FIELD + PICTURE

            FloorField.setText("Floors:");
            FloorField.setFont(new Font("monospaced", Font.PLAIN, 10));
            FloorField.setForeground(new Color(37, 31, 25));
            add(FloorField);
            FloorField.setBounds(this.panelW / 4, (int) ((ycenter * 1.5) - 95 + (this.panelH / (4 * 3)) * 2), (int) this.panelW / 3, 45);
            FontSize(FloorField);

            CalorieField.setText("Calories:");
            CalorieField.setFont(new Font("monospaced", Font.PLAIN, 10));
            CalorieField.setForeground(new Color(37, 31, 25));
            add(CalorieField);
            CalorieField.setBounds(this.panelW / 4, (int) ((ycenter * 1.5) - 85 + (this.panelH / (4 * 3)) * 3), (int) this.panelW / 3, 45);
            FontSize(CalorieField);


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
            if (parentWindow.isConnected()) {
                JComponent jc = new HMSBarChartComponent(this.panelW, this.panelH, this.parentWindow);
                add(jc);
                jc.setBounds(xcenter - (this.panelW / 10), (int) this.panelH / 9 * 5, this.panelW, this.panelH);
            }
        }
        else{
            new PopUpWindow("Please check your internet connection", "Cannot load Health Meta Score");
        }
    }

    /**
     * Set the font size to match the size of the label
     *
     * @param label label in question
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

}