package ca.uwo.csd.cs2212.team02;

import java.awt.*;
import javax.swing.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * UI page for accolades
 */

public class UIAccolades extends JLayeredPane {

    private MainWindow parentWindow;
    private Date userDate;
    private JButton save, view;
    private JLayeredPane main;
    private int panelW, panelH, xcenter, ycenter, containerWidth, containerHeight;
    private JButton[] buttonArray = new JButton[20];
    private JButton[] lockedArray = new JButton[20];
    private JButton[] unlockedArray = new JButton[20];
    private LinkedList<Accolade> fullList;


    private javax.swing.JLabel TitleText;

    /**
     * Constructor
     *
     * @param mainwindow main
     * @param userDate   user-selected date
     * @param accList    list of accolades
     */
    public UIAccolades(MainWindow mainwindow, Date userDate, AccoladeList accList) {
        this.userDate = userDate;
        this.parentWindow = mainwindow;
        this.panelW = new Integer(this.parentWindow.getSize().width - this.parentWindow.getSideWidth() - this.parentWindow.getMinMaxDiff());
        this.panelH = new Integer(this.parentWindow.getSize().height - this.parentWindow.getTopHeight());

        this.setPreferredSize(new Dimension(panelW, panelH));
        this.containerWidth = this.panelW - 10;
        this.containerHeight = this.panelH - 10;
        this.xcenter = containerWidth / 2;
        this.ycenter = containerHeight / 2;
        this.fullList = accList.getFullList();
        accList.checkAll();
        //overall theme options
        setLayout(null);

        TitleText = new JLabel("Accolades");
        TitleText.setFont(new java.awt.Font("Lucida Grande", 0, 48)); // NOI18N
        TitleText.setForeground(new Color(37, 31, 25));
        add(TitleText);
        TitleText.setBounds(this.parentWindow.getMinMaxDiff(), ycenter / 18, this.panelW / 9 * 2, 150);
        FontSize(TitleText);


        //code to place badges
        Iterator<Accolade> iter = fullList.iterator();
        int lockCount = 0;
        int unlockCount = 0;
        for (int i = 0; i <= 19; i++) {
            final Accolade tempAcc = iter.next();
            String temp = tempAcc.getName();
            if (!tempAcc.isLocked() && tempAcc.isVisible(this.parentWindow.getDataDate())) {
                buttonArray[i] = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/" + temp + ".png")).getImage()).getScaledInstance(this.panelH / 7, this.panelH / 7, java.awt.Image.SCALE_SMOOTH)));
                buttonArray[i].setRolloverIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/" + temp + "Rollover.png")).getImage()).getScaledInstance(this.panelH / 7, this.panelH / 7, java.awt.Image.SCALE_SMOOTH)));
                buttonArray[i].setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/" + temp + "Pressed.png")).getImage()).getScaledInstance(this.panelH / 7, this.panelH / 7, java.awt.Image.SCALE_SMOOTH)));

                buttonArray[i].setContentAreaFilled(false);
                //buttonArray[i].setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/DownloadPressed.png")).getImage()).getScaledInstance(this.panelH/15, this.panelH/15, java.awt.Image.SCALE_SMOOTH)));
                //buttonArray[i].setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/DownloadRollover.png")).getImage()).getScaledInstance(this.panelH/15, this.panelH/15, java.awt.Image.SCALE_SMOOTH)));
                buttonArray[i].addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        new AccoladePopUp(tempAcc.getDescription(), tempAcc.getName(), tempAcc.getName());
                    }
                });

                unlockedArray[unlockCount] = buttonArray[i];
                unlockCount++;
            } else {
                buttonArray[i] = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/lock.png")).getImage()).getScaledInstance(this.panelH / 7, this.panelH / 7, java.awt.Image.SCALE_SMOOTH)));
                buttonArray[i].setRolloverIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/lockRollover.png")).getImage()).getScaledInstance(this.panelH / 7, this.panelH / 7, java.awt.Image.SCALE_SMOOTH)));
                buttonArray[i].setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/lockPressed.png")).getImage()).getScaledInstance(this.panelH / 7, this.panelH / 7, java.awt.Image.SCALE_SMOOTH)));
                lockedArray[lockCount] = buttonArray[i];
                lockCount++;
            }
            buttonArray[i].setToolTipText(temp);
            add(buttonArray[i]);
        }


        int xstart = xcenter - 4 * xcenter / 7;
        int ystart = ycenter - (int) (ycenter / 1.5);
        int lock = 0;
        int unlock = 0;
        for (int i = 0; i <= 19; ) {
            for (int j = 0; j <= 4; j++) {
                if (i < unlockCount) {
                    unlockedArray[unlock].setContentAreaFilled(false);
                    unlockedArray[unlock].setBounds(xstart, ystart, 180, 180);
                    unlock++;
                } else {
                    lockedArray[lock].setContentAreaFilled(false);
                    lockedArray[lock].setBounds(xstart, ystart, 180, 180);
                    lock++;
                }
                xstart += 185;
                i++;
            }
            ystart += 190;
            xstart = xcenter - 4 * xcenter / 7;
        }


        //export PDF
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


        if (App.isTestMode()) {
            this.view = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/View All.png")).getImage()).getScaledInstance(121, 33, java.awt.Image.SCALE_SMOOTH)));
            this.view.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/View AllPressed.png")).getImage()).getScaledInstance(121, 33, java.awt.Image.SCALE_SMOOTH)));
            this.view.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/View AllRollover.png")).getImage()).getScaledInstance(121, 33, java.awt.Image.SCALE_SMOOTH)));
            this.view.setContentAreaFilled(false);
            this.add(this.view);
            this.view.setBounds(this.panelW - 121, this.panelH - 60, 121, 33);
            this.view.addActionListener(new java.awt.event.ActionListener() {
                                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                showAll();
                                            }
                                        }
            );
        }


    }

    /**
     * Have option to show all badges when in test mode
     */
    private void showAll() {
        Iterator<Accolade> iter = fullList.iterator();
        for (int i = 0; i <= 19; i++) {
            this.remove(buttonArray[i]);
            Accolade tempAcc = iter.next();
            String temp = tempAcc.getName();
            buttonArray[i] = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/" + temp + ".png")).getImage()).getScaledInstance(this.panelH / 7, this.panelH / 7, java.awt.Image.SCALE_SMOOTH)));
            buttonArray[i].setRolloverIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/" + temp + "Rollover.png")).getImage()).getScaledInstance(this.panelH / 7, this.panelH / 7, java.awt.Image.SCALE_SMOOTH)));
            buttonArray[i].setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/" + temp + "Pressed.png")).getImage()).getScaledInstance(this.panelH / 7, this.panelH / 7, java.awt.Image.SCALE_SMOOTH)));
            buttonArray[i].setToolTipText(temp);
            add(buttonArray[i]);
        }
        int xstart = xcenter - 4 * xcenter / 7;
        int ystart = ycenter - (int) (ycenter / 1.5);
        for (int i = 0; i <= 19; ) {
            for (int j = 0; j <= 3; j++) {
                buttonArray[i].setContentAreaFilled(false);
                buttonArray[i].setBounds(xstart, ystart, 180, 180);
                ystart += 185;
                i++;
            }
            xstart += 190;
            ystart = ycenter - (int) (ycenter / 1.5);
        }
    }

    /**
     * Resizes JLabel to match font size
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
}
