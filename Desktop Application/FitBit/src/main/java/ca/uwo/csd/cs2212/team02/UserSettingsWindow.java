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
 * UI for settings window UI
 */
public class UserSettingsWindow extends JDialog {

    private JCheckBox tips, sounds;
    private ArrayList<JCheckBox> objectArray;
    private boolean[] SavedArray;
    private boolean[] NewArray;
    private MainWindow parentWindow;

    /**
     * @param main
     */
    public UserSettingsWindow(MainWindow main) {
        this.parentWindow = main;
        if (new File("src/main/resources/app.config").exists()) {
            try {
                loadSettings();
            } catch (Exception ex) {
                new PopUpWindow("Loading default settings instead.", "Unable to load users application settings");
                SavedArray = new boolean[]{true, true};
                Logger.getLogger(DashboardConfigWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
            SavedArray = new boolean[]{true, true};
        initDialog();
    }

    /**
     * Launch UI
     */
    private void initDialog() {
        NewArray = SavedArray.clone();
        this.setSize(375, 300);
        this.setTitle("FFFFFF - Application Settings");
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        this.tips = new JCheckBox("Show Daily Tips");
        this.sounds = new JCheckBox("Enable Application Button Sounds");

        objectArray = new ArrayList<JCheckBox>() {{
            add(tips);
            add(sounds);
        }};
        c.insets = new Insets(20, 20, 20, 20);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Personalize Application Settings");
        title.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        this.add(title, c);

        c.gridwidth = 2; // reset width of item
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
                            NewArray[0] = tips.isSelected();
                        }
                    });
                    break;
                case 1:
                    objectArray.get(index).addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent e) {
                            NewArray[1] = sounds.isSelected();
                        }
                    });
                    break;
            }

            c.gridx = col;
            c.gridy = row;
            this.add(objectArray.get(index), c);
            row++;
        }

        c.insets = new Insets(0, 0, 5, 5);
        JButton save = new JButton("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    applyConfigs();
                } catch (Exception ex) {
                    new PopUpWindow("Make sure config file is not in use", "Unable to save application settings");
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
                    new PopUpWindow("Make sure config file is not in use", "Unable to save application settings");
                    Logger.getLogger(DashboardConfigWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                dispose();
            }
        });
        c.gridy = row;
        this.add(cancel, c);
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setModal(true);
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.setVisible(true);
    }

    /**
     * Load saved settings
     *
     * @throws Exception
     */
    private void loadSettings() throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/app.config"));
        UserConfigs p = (UserConfigs) in.readObject();
        in.close();
        this.SavedArray = p.getConfigs();
    }

    /**
     * Save user's settings
     *
     * @throws Exception
     */
    private void applyConfigs() throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/resources/app.config"));
        UserConfigs DC = new UserConfigs(NewArray);
        out.writeObject(DC);
        this.parentWindow.setTipsOn(NewArray[0]);
        this.parentWindow.setSoundOn(NewArray[1]);
    }

    /**
     * Revert old settings if user cancels
     *
     * @throws Exception
     */
    private void revertConfigs() throws Exception {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/main/resources/app.config"));
        UserConfigs DC = new UserConfigs(SavedArray);
        out.writeObject(DC);
        this.parentWindow.setTipsOn(SavedArray[0]);
        this.parentWindow.setSoundOn(SavedArray[1]);
    }
}
