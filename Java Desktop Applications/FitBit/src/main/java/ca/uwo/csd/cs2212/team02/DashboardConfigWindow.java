/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uwo.csd.cs2212.team02;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * UI for custom dashboard
 */

public class DashboardConfigWindow extends JDialog {

    private JCheckBox calories, sedentary, active, HMS, steps, floors, distance;
    private ArrayList<JCheckBox> objectArray;
    private boolean[] SavedArray;
    private boolean[] NewArray;
    private dailyDashboard parentDashboard;
    private MainWindow mainwindow;

    /**
     * Constructor
     *
     * @param dash
     */
    public DashboardConfigWindow(dailyDashboard dash) {
        parentDashboard = dash;
        mainwindow = parentDashboard.getParentWindow();
        if (new File("src/main/resources/dashboard.config").exists()) {
            try {
                loadSettings();
            } catch (Exception ex) {
                new PopUpWindow("Loading default settings instead.", "Unable to load users dashboard settings");
                SavedArray = new boolean[]{true, true, true, true, true, true, true};
                Logger.getLogger(DashboardConfigWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
            SavedArray = new boolean[]{true, true, true, true, true, true, true};
        initDialog();
    }

    /**
     * Draw edit window
     */
    private void initDialog() {
        NewArray = SavedArray.clone();
        this.setSize(500, 425);
        this.setResizable(false);
        this.setTitle("FFFFFF - Daily Dashboard Configurations");
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.calories = new JCheckBox("Calories Burned");
        this.sedentary = new JCheckBox("Sedentary Minutes");
        this.active = new JCheckBox("Active Minutes");
        this.HMS = new JCheckBox("Health Meta Score");
        this.steps = new JCheckBox("Steps Taken");
        this.floors = new JCheckBox("Floors Climbed");
        this.distance = new JCheckBox("Distance Travelled");

        objectArray = new ArrayList<JCheckBox>() {{
            add(calories);
            add(sedentary);
            add(active);
            add(HMS);
            add(steps);
            add(floors);
            add(distance);
        }};
        c.insets = new Insets(20, 20, 20, 20);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Select The Items You Wish To See On The Daily Dashboard");
        title.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        this.add(title, c);

        c.gridwidth = 1; // reset width of item
        int row = 1;
        int col = 0;

        for (int index = 0; index < objectArray.size(); index++) {

            if (SavedArray[index]) {
                objectArray.get(index).setSelected(true);
            }
            switch (index) {
                case 0:
                    objectArray.get(index).addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            NewArray[0] = calories.isSelected();
                        }
                    });
                    break;
                case 1:
                    objectArray.get(index).addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            NewArray[1] = sedentary.isSelected();
                        }
                    });
                    break;
                case 2:
                    objectArray.get(index).addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            NewArray[2] = active.isSelected();
                        }
                    });
                    break;
                case 3:
                    objectArray.get(index).addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            NewArray[3] = HMS.isSelected();
                        }
                    });
                    break;
                case 4:
                    objectArray.get(index).addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            NewArray[4] = steps.isSelected();
                        }
                    });
                    break;
                case 5:
                    objectArray.get(index).addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            NewArray[5] = floors.isSelected();
                        }
                    });
                    break;
                case 6:
                    objectArray.get(index).addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            NewArray[6] = distance.isSelected();
                        }
                    });
                    break;
            }

            c.gridx = col;
            c.gridy = row;
            this.add(objectArray.get(index), c);
            if ((index + 1) % 2 == 0) { // if next index is even
                row++;
                col--;
            } else {
                col++;

            }
        }
        c.insets = new Insets(0, 0, 5, 5);
        c.gridy = row++;
        JButton apply = new JButton("Apply");
        apply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    applyConfigs();
                } catch (Exception ex) {
                    new PopUpWindow("Make sure config file is not in use", "Unable to save dashboard settings");
                    Logger.getLogger(DashboardConfigWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        c.gridy = row;
        c.gridx = 1;
        this.add(apply, c);
        JButton save = new JButton("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    applyConfigs();
                } catch (Exception ex) {
                    new PopUpWindow("Make sure config file is not in use", "Unable to save dashboard settings");
                    Logger.getLogger(DashboardConfigWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            }
        });
        c.gridy = row++;
        c.gridx = 2;
        this.add(save, c);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    revertConfigs();
                } catch (Exception ex) {
                    new PopUpWindow("Make sure config file is not in use", "Unable to save dashboard settings");
                    Logger.getLogger(DashboardConfigWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            }
        });
        c.gridy = row;
        this.add(cancel, c);
        this.setModal(true);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    /**
     * Load saved settings
     *
     * @throws Exception if file is corrupt
     */
    private void loadSettings() throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/dashboard.config"));
        DashboardConfigs p = (DashboardConfigs) in.readObject();
        in.close();
        this.SavedArray = p.getConfigs();
    }

    /**
     * Apply user's changes
     *
     * @throws Exception
     */
    private void applyConfigs() throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/resources/dashboard.config"));
        DashboardConfigs DC = new DashboardConfigs(NewArray);
        out.writeObject(DC);
        mainwindow.recreateWorkingArea();
    }

    /**
     * Undo user's changes if they click cancel
     *
     * @throws Exception
     */
    private void revertConfigs() throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/resources/dashboard.config"));
        DashboardConfigs DC = new DashboardConfigs(SavedArray);
        out.writeObject(DC);
        mainwindow.recreateWorkingArea();
    }
}
