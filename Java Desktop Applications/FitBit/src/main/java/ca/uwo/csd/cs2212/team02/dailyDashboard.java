/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uwo.csd.cs2212.team02;

import com.github.scribejava.core.exceptions.OAuthException;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.*;

/**
 * The daily dashboard that will display the data of the current day, or a given date
 * including: the health meta score, steps taken, number of floors climbed,
 * number of calories burned, number of active minutes, and the resting heart rate
 */
public class dailyDashboard extends JLayeredPane {
    private JButton healthMeta, steps, floors, distance, calories, activeMins,
            restingHR, refresh, edit, save, info;
    private JLabel logo, updateTimeStamp;
    private int panelW, panelH;
    private MainWindow parentWindow;
    private JLayeredPane main;
    private Date day;
    private DataType dataSteps, dataDistance, dataFloors, dataCalories, dataSedentaryMins, dataActiveMins;
    private HealthMetaScore dataHealthMeta;
    private HeartRate dataHeartRate;
    private ArrayList<String> items;
    private java.util.Date LastUpdate;

    /**
     * Constructor class
     *
     * @param mainwindow the parent mainwindow that will contain the JPanel
     */
    public dailyDashboard(MainWindow mainwindow) {
        this.parentWindow = mainwindow;
        initUI();
    }

    /**
     * Launch the UI
     */
    private void initUI() {
        this.panelW = new Integer(this.parentWindow.getSize().width - this.parentWindow.getSideWidth() - this.parentWindow.getMinMaxDiff());
        this.panelH = new Integer(this.parentWindow.getSize().height - this.parentWindow.getTopHeight());
        this.setPreferredSize(new Dimension(panelW, panelH));
        this.setLayout(new BorderLayout());

        this.day = this.parentWindow.getDataDate();

        if (new File("src/main/resources/dashboard.config").exists()) {
            try {
                loadSaved();
                LastUpdate = new java.util.Date();
                this.generateButtons();
                parentWindow.setConnected(true);
            } catch (Exception ex) {
                try {
                    loadDefault();
                    LastUpdate = new java.util.Date();
                    this.generateButtons();
                    parentWindow.setConnected(true);
                } catch (APIException ex1) {
                    switch (ex1.getErrType()) {
                        case 0:
                            parentWindow.setConnected(false);
                            new PopUpWindow("Please check network connection", "Cannot load your personal data");
                            break;
                        case 1:
                            parentWindow.setConnected(false);
                            new PopUpWindow("Please try again in an hour", "<html>You have exceeded the limit for refreshing your data</html>");
                            break;
                    }
                } catch (OAuthException ex2) {
                    parentWindow.setConnected(false);
                    new PopUpWindow("Please contact system administrator", "Cannot communicate with Fitbit");
                } catch (IOException e) {
                    parentWindow.setConnected(false);
                    new PopUpWindow("Please contact system administrator", "Invalid token files");
                }
            }
        } else
            try {
                loadDefault();
                LastUpdate = new java.util.Date();
                this.generateButtons();
                parentWindow.setConnected(true);

            } catch (APIException ex) {
                switch (ex.getErrType()) {
                    case 0:
                        parentWindow.setConnected(false);
                        new PopUpWindow("Please check network connection", "Cannot load your personal data");
                        break;
                    case 1:
                        parentWindow.setConnected(false);
                        new PopUpWindow("Please try again in an hour", "<html>You have exceeded the limit for refreshing your data</html>");
                        break;

                }
            } catch (OAuthException ex2) {
                parentWindow.setConnected(false);
                new PopUpWindow("Please contact system administrator", "Cannot communicate with fitbit");
            } catch (IOException e) {
                parentWindow.setConnected(false);
                new PopUpWindow("Please contact system administrator", "Invalid token files");
            }
        main = this;

    }

    /**
     * Causes the main window to refresh the data of the daily dashboard
     *
     * @param evt Action listener
     **/
    private void refreshActionPerformed(ActionEvent evt) {
        APIHandling.reinitializeHashTable();
        parentWindow.playButtonSound();
        parentWindow.recreateWorkingArea();
    }

    /**
     * Opens up a pop up dialog allowing the user to change the configuration of the daily dashboard
     **/
    private void editActionPerformed(ActionEvent evt) {
        parentWindow.playButtonSound();
        DashboardConfigWindow config = new DashboardConfigWindow(this);
    }

    /**
     * @param value  Value to be rounded
     * @param places # of places after the decimal
     * @return rounded value
     */
    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /**
     * Generate buttons with the 6 types of personal data for the user
     */
    private void generateButtons() {
        this.removeAll();
        int containerWidth = this.panelW - 10;
        int containerHeight = this.panelH - 10;

        int xcenter = containerWidth / 2;
        int ycenter = containerHeight / 2;

        // compute radius of the circle

        int xradius = (containerWidth - 200) / 2;
        int yradius = (containerHeight - 200) / 2;
        int radius = Math.min(xradius, yradius);

        Iterator it = items.iterator();
        int i = 0;
        while (it.hasNext()) {
            String itemType = (String) it.next();
            double angle = 2 * Math.PI * i / items.size();

            // center point of component
            int x = xcenter + (int) (Math.cos(angle) * radius);
            int y = ycenter + (int) (Math.sin(angle) * radius);

            // move component so that its center is (x, y)
            // and its size is its preferred size
            GraphCircle gc = null;
            if ("Health Score".equals(itemType)) {
                gc = new GraphCircle((int) (dataHealthMeta.getScore() / 10), (int) dataHealthMeta.getScore(), itemType, 1000);
            } else if ("Steps".equals(itemType)) {
                gc = new GraphCircle((int) (dataSteps.getValue() / dataSteps.getGoal() * 100), (int) dataSteps.getValue(), itemType, (int) dataSteps.getGoal());
            } else if ("Floors".equals(itemType)) {
                gc = new GraphCircle((int) (dataFloors.getValue() / dataFloors.getGoal() * 100), (int) dataFloors.getValue(), itemType, (int) dataFloors.getGoal());
            } else if ("Kilometers".equals(itemType)) {
                gc = new GraphCircle((int) (dataDistance.getValue() / dataDistance.getGoal() * 100), (int) dataDistance.getValue(), itemType, (int) dataDistance.getGoal());
            } else if ("Calories".equals(itemType)) {
                gc = new GraphCircle((int) (dataCalories.getValue() / dataCalories.getGoal() * 100), (int) dataCalories.getValue(), itemType, (int) dataCalories.getGoal());
            } else if ("Mins Sedentary".equals(itemType)) {
                gc = new GraphCircle(100, (int) dataSedentaryMins.getValue(), itemType, -1);
            } else if ("Mins Active".equals(itemType)) {
                gc = new GraphCircle(100, (int) dataActiveMins.getValue(), itemType, -1);
            }
            BufferedImage a = null;
            try {
                a = gc.createImage();
            } catch (AWTException ex) {
                new PopUpWindow("Cannot create custom daily dashboard properly", "Something is wrong");
            }
            JLabel temp = new JLabel(new ImageIcon(a.getScaledInstance(this.panelH / 5, this.panelH / 5, java.awt.Image.SCALE_SMOOTH)));
            temp.setBounds(x - (this.panelH / 4 / 2), y - (this.panelH / 4 / 2), this.panelH / 4, this.panelH / 4);
            add(temp);
            i++;
        }

        this.refresh = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Refresh.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
        this.refresh.setContentAreaFilled(false);
        this.refresh.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/RefreshPressed.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
        this.refresh.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/RefreshRollover.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
        this.refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });
        this.add(this.refresh);
        this.refresh.setBounds(this.panelW - 10 - this.panelH / 15, (this.panelH / 15 * 1 / 2), this.panelH / 15, this.panelH / 15);

        this.edit = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Edit.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
        this.edit.setContentAreaFilled(false);
        this.edit.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/EditPressed.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
        this.edit.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/EditRollover.png")).getImage()).getScaledInstance(this.panelH / 15, this.panelH / 15, java.awt.Image.SCALE_SMOOTH)));
        this.edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editActionPerformed(evt);
            }
        });
        this.add(this.edit);
        this.edit.setBounds(this.panelW - 10 - this.panelH / 15, (this.panelH / 15 * 3 / 2) + 10, this.panelH / 15, this.panelH / 15);

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


        this.logo = new JLabel(new ImageIcon(((new ImageIcon("src/main/resources/images/Logo.png")).getImage()).getScaledInstance(240, 200, java.awt.Image.SCALE_SMOOTH)));
        this.add(this.logo);
        this.logo.setBounds(xcenter - 120, ycenter - 100, 240, 200);

        this.updateTimeStamp = new JLabel("Last Updated: " + this.LastUpdate.toString());
        this.updateTimeStamp.setForeground(new Color(37, 31, 25));
        this.add(this.updateTimeStamp);
        this.updateTimeStamp.setBounds(this.panelW - (this.panelW / 4), this.panelH - (this.panelH / 13), 500, 50);


        JLabel NONE = new JLabel();
        this.add(NONE);

        this.refresh.setToolTipText("Refresh Data");
        this.save.setToolTipText("Export as PDF");
        this.edit.setToolTipText("Configure Dashboard");
    }


    /**
     * @return Object representing data related to floors
     */
    public DataType getDataFloors() {
        return dataFloors;
    }

    /**
     * @return Object representing data related to steps
     */
    public DataType getDataSteps() {
        return dataSteps;
    }

    /**
     * @return Object representing data related to distance
     */
    public DataType getDataDistance() {
        return dataDistance;
    }

    /**
     * @return Object representing data related to calories
     */
    public DataType getDataCalories() {
        return dataCalories;
    }

    /**
     * @return Object representing data related to sedentary minutes
     */
    public DataType getDataSedentaryMins() {
        return dataSedentaryMins;
    }

    /**
     * @return Object representing data related to active minutes
     */
    public DataType getDataActiveMins() {
        return dataActiveMins;
    }

    /**
     * @return Object representing Health Meta Score and related data
     */
    public HealthMetaScore getDataHealthMeta() {
        return dataHealthMeta;
    }

    /**
     * @return main window
     */
    public MainWindow getParentWindow() {
        return this.parentWindow;
    }

    /**
     * Loads default settings (all metrics shown on screen)
     *
     * @throws APIException if communication with Fitbit cannot be established
     */
    private void loadDefault() throws APIException, IOException {
        items = new ArrayList<String>() {{
            add("Calories");
            add("Mins Sedentary");
            add("Mins Active");
            add("Health Score");
            add("Steps");
            add("Floors");
            add("Kilometers");
        }};

        this.dataSteps = new DataType(0, day);
        this.dataDistance = new DataType(1, day);
        this.dataFloors = new DataType(2, day);
        this.dataCalories = new DataType(3, day);
        this.dataSedentaryMins = new DataType(4, day);
        this.dataActiveMins = new DataType(5, day);
        this.dataHealthMeta = new HealthMetaScore(day, this);
        this.dataHeartRate = APIHandling.getHeartRate(this.parentWindow.getDataDate());
    }

    /**
     * Loads saved dashboard settings
     *
     * @throws Exception if file is corrupt
     */
    private void loadSaved() throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/dashboard.config"));
        DashboardConfigs p = (DashboardConfigs) in.readObject();
        in.close();
        boolean[] saved = p.getConfigs();
        items = new ArrayList<String>();


        this.dataSteps = new DataType(0, day);
        this.dataFloors = new DataType(2, day);
        this.dataDistance = new DataType(1, day);
        this.dataCalories = new DataType(3, day);
        this.dataSedentaryMins = new DataType(4, day);
        this.dataActiveMins = new DataType(5, day);
        this.dataHeartRate = APIHandling.getHeartRate(this.parentWindow.getDataDate());
        this.dataHealthMeta = new HealthMetaScore(day, this);

        if (saved[0]) {
            items.add("Calories");
        }
        if (saved[1]) {
            items.add("Mins Sedentary");
        }
        if (saved[2]) {
            items.add("Mins Active");
        }
        if (saved[3]) {
            items.add("Health Score");
        }
        if (saved[4]) {
            items.add("Steps");
        }
        if (saved[5]) {
            items.add("Floors");
        }
        if (saved[6]) {
            items.add("Kilometers");
        }
    }

    /**
     * Show user a legend for the daily dashboard
     */
    private void showInfo() {
        new Legend(this.parentWindow, new File("src/main/resources/images/DashboardLegend.png"));
    }

}
