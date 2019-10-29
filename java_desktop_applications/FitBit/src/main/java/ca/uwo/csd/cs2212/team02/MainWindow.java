/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uwo.csd.cs2212.team02;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.FileOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//SOUND IMPORTS
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * The main view class and container that will display and generate other
 * JPanels within this JFrame
 */
public class MainWindow extends JFrame {

    private Box.Filler filler1, filler2;
    private JButton home, hrZones, sidePanelSizeToggle, lifetime,
            settings, bestDays, calendar, HMS, accolades;
    private JLayeredPane sidePanel, sidePanelMain, sidePanelToggle, topPanel,
            workingArea, centralArea;
    private Dimension screenSize;
    private int sidePanelWidth, topPanelWidth, topPanelHeight;
    private int MIN_MAX_DIFF = 222 - 118;
    private boolean sideMinimized, soundOn, tipsOn;
    private int pageMarker;
    private Calendar cal;
    private java.util.Date today;
    private java.util.Date _dataDate_;
    private Date dataDate;
    private Timestamp lastUpdated;
    private SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd yyyy");
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    private SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    private SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    private JLabel dateLabel;
    private JButton fitbit;
    private dailyDashboard dashboard;
    private JButton exit;
    private DatePick datePicker;
    private String userConfigs;
    private AccoladeList accList;
    private LinkedList<Accolade> newlyUnlocked;
    private boolean isConnected; //checks whether connected with API
    private BackgroundPanel panel;
    // Default Constructor: no saved user settings found

    /**
     * Default constructor method
     */
    public MainWindow() {
        this.displayBackground();
        this.initUI();
        this.userConfigs = null;
        loadSettings();
        if (this.tipsOn)
            new DailyTips();
        //this.getContentPane().setBackground(new Color(0,255,0));
    }

    /**
     * Getter for today's date  (will be for user-selected date in future)
     *
     * @return current date
     */
    public Date getDataDate() {
        return dataDate;
    }

    /**
     * Initializes the UI of the entire mainWindow calls upon private helper
     * methods: createSidePanel() and createTopPanel() to do so
     */
    private void initUI() {

        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(this.screenSize.width - 200, this.screenSize.height - 100));
        this.setSize(new Dimension(this.screenSize.width - 200, this.screenSize.height - 100));
        this.setResizable(false);
        this.setUndecorated(true); // removes the menu bar
        this.today = Calendar.getInstance().getTime();
        this._dataDate_ = this.today;

        this.dataDate = new Date(Integer.parseInt(yearFormat.format(this.today)),
                Integer.parseInt(monthFormat.format(this.today)),
                Integer.parseInt(dayFormat.format(this.today)));//Year Month Day

        this.sidePanel = new JLayeredPane();
        this.centralArea = new JLayeredPane();
        this.topPanel = new JLayeredPane();
        this.workingArea = new JLayeredPane();

        this.topPanel.setMinimumSize(new Dimension(100, 50));
        this.topPanel.setPreferredSize(new Dimension(670, 50));
        this.sidePanelMain = new JLayeredPane();
        this.sidePanelToggle = new JLayeredPane();
        this.sidePanelSizeToggle = new JButton();
        this.sidePanel.setLayout(new java.awt.BorderLayout());

        this.sidePanel.add(this.sidePanelMain, BorderLayout.LINE_START);
        this.sidePanel.add(this.sidePanelToggle, BorderLayout.LINE_END);
        this.panel.add(sidePanel, BorderLayout.LINE_START);
        pack();


        lastUpdated = new Timestamp(new java.util.Date().getTime());

        this.panel.add(centralArea, BorderLayout.CENTER);

        this.workingArea.setBackground(new Color(255, 255, 255, 255));
        this.workingArea.setOpaque(false);

        this.topPanel.setBackground(new Color(0, 0, 0, 0));
        this.topPanel.setOpaque(false);
        this.centralArea.setOpaque(false);
        this.sidePanelMain.setBackground(new Color(0, 0, 0, 0));
        this.sidePanelMain.setOpaque(false);
        this.sidePanelToggle.setBackground(new Color(0, 0, 0, 0));
        this.sidePanelToggle.setOpaque(false);
        this.sidePanel.setBackground(new Color(0, 0, 0, 0));
        this.sidePanel.setOpaque(false);


        centralArea.setLayout(new BorderLayout());
        centralArea.add(topPanel, java.awt.BorderLayout.PAGE_START);
        centralArea.add(workingArea, BorderLayout.CENTER);
        this.sidePanelWidth = 200;
        this.topPanelHeight = 50;
        pack();
        createSidePanel(0); // 0 for minimized, 1 for maximized
        createTopPanel();
        this.workingArea.setLayout(null);
        dashboard = new dailyDashboard(this);
        this.workingArea.add(dashboard);
        dashboard.setBounds(MIN_MAX_DIFF / 2, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                this.getSize().height - this.getTopHeight());

        this.pageMarker = 0;
        this.setLocationRelativeTo(null); // centres the application, requires pack() before call
        this.sideMinimized = true;
        this.accList = new AccoladeList(this);
        this.accList.checkAll();
        this.newlyUnlocked = accList.getNewlyUnlocked();
        if (!newlyUnlocked.isEmpty()) {
            new AccoladeAchievements(newlyUnlocked);
        }
    }

    /**
     * Method to play sound when side panel buttons are clicked
     */
    public void playButtonSound() {
        if (this.soundOn) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/main/resources/BUTTONCLICK.wav").getAbsoluteFile());
                Clip buttonClip = AudioSystem.getClip();
                buttonClip.open(audioInputStream);
                buttonClip.start();
            } catch (LineUnavailableException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * @return the width of the side panel on the left of the mainwindow
     */
    public int getSideWidth() {
        return this.sidePanelWidth;
    }

    /**
     * @return the height of the top panel of the mainwindow
     */
    public int getTopHeight() {
        return this.topPanelHeight;
    }

    /**
     * @return the different in width between the minimized and maximized side
     * panel
     */
    public int getMinMaxDiff() {
        return this.MIN_MAX_DIFF;
    }

    /**
     * @return a boolean value of whether or not the side panel is minimized
     */
    public boolean isSideMinimized() {
        return this.sideMinimized;
    }

    /**
     * Activated when the home button is pressed. Returns the user back to the
     * daily dashboard.
     */
    private void homeActionPerformed(java.awt.event.ActionEvent evt) {
        playButtonSound();
        this.workingArea.removeAll();
        this.workingArea.add(dashboard);
        if (this.sideMinimized) {
            dashboard.setBounds(MIN_MAX_DIFF / 2, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        } else {
            dashboard.setBounds((MIN_MAX_DIFF / 2) - this.MIN_MAX_DIFF, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        }
        updateWindow();
        this.pageMarker = 0;
    }

    /**
     * Activated when the heart rate zones button is pressed. Takes the user to
     * a page displaying the time spent in each heart rate zone.
     */
    private void hrZonesActionPerformed(java.awt.event.ActionEvent evt) {
        playButtonSound();
        this.workingArea.removeAll();
        this.workingArea.add(new UIHRZ(this, dataDate));
        Component c = this.workingArea.getComponent(0);
        if (this.sideMinimized) {
            c.setBounds(MIN_MAX_DIFF / 2, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        } else {
            c.setBounds((MIN_MAX_DIFF / 2) - this.MIN_MAX_DIFF, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        }
        updateWindow();
        this.pageMarker = 3;
    }

    /**
     * Activated when the best days button is pressed. Takes the user to a page
     * displaying the user's best days for their data.
     */
    private void bestDaysActionPerformed(java.awt.event.ActionEvent evt) {
        playButtonSound();
        this.workingArea.removeAll();
        this.workingArea.add(new UIBestDays(this, dataDate));
        Component c = this.workingArea.getComponent(0);
        if (this.sideMinimized) {
            c.setBounds(MIN_MAX_DIFF / 2, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        } else {
            c.setBounds((MIN_MAX_DIFF / 2) - this.MIN_MAX_DIFF, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        }
        updateWindow();
        this.pageMarker = 4;
    }

    /**
     * Activated when the lifetime totals button is pressed. Takes the user to a
     * page displaying the user's lifetime totals.
     */
    private void lifetimeActionPerformed(java.awt.event.ActionEvent evt) {
        playButtonSound();
        this.workingArea.removeAll();
        this.workingArea.add(new UILifeTime(this, dataDate));
        Component c = this.workingArea.getComponent(0);
        if (this.sideMinimized) {
            c.setBounds(MIN_MAX_DIFF / 2, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        } else {
            c.setBounds((MIN_MAX_DIFF / 2) - this.MIN_MAX_DIFF, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        }
        updateWindow();
        this.pageMarker = 5;
    }


    private void accoladesActionPerformed(java.awt.event.ActionEvent evt) {
        playButtonSound();
        this.workingArea.removeAll();
        this.workingArea.add(new UIAccolades(this, dataDate, this.accList));
        Component c = this.workingArea.getComponent(0);
        if (this.sideMinimized) {
            c.setBounds(MIN_MAX_DIFF / 2, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        } else {
            c.setBounds((MIN_MAX_DIFF / 2) - this.MIN_MAX_DIFF, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        }
        updateWindow();
        this.pageMarker = 2;
    }

    /**
     * Activated when the minimize/maximize side panel button is pressed. This
     * will redraw the side panel, the top panel, and shift the working area to
     * compensate for the length of the side panel. Uses the functions
     * createSidePanel(), updateWindow(), and updateTopPanel() to do so.
     */
    private void sidePanelSizeToggleActionPerformed(java.awt.event.ActionEvent evt) {
        playButtonSound();
        if (this.sideMinimized == true) {
            createSidePanel(1);
            this.sideMinimized = false;
        } else {
            createSidePanel(0);
            this.sideMinimized = true;
        }
        Component c;
        if (this.workingArea.getComponentCount() > 0) {
            c = this.workingArea.getComponent(0);
            this.updateTopPanel();
            if (this.sideMinimized) {
                c.setLocation(c.getBounds().x + this.MIN_MAX_DIFF, c.getBounds().y);
            } else {
                c.setLocation(c.getBounds().x - this.MIN_MAX_DIFF, c.getBounds().y);
            }
        }
        this.updateWindow();

    }

    /**
     * Activated when the settings button is pressed. Brings up a new JFrame
     * popup where the user can change his/her settings.
     */
    private void settingsActionPerformed(java.awt.event.ActionEvent evt) {
        playButtonSound();
        new UserSettingsWindow(this);
    }

    /**
     * Create small calendar for the user to select a new date
     * for which to see their data
     *
     * @param evt
     */
    private void calendarActionPerformed(java.awt.event.ActionEvent evt) {
        playButtonSound();
        datePicker = new DatePick(this, this._dataDate_, this.today);
    }

    /**
     * Links user to the Fitbit website to access their account
     * @param evt
     */
    private void fitbitActionPerformed(java.awt.event.ActionEvent evt) {
        playButtonSound();
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://www.fitbit.com/login"));
        } catch (IOException e) {
        }
    }

    /**
     * Create health meta score page
     *
     * @param evt
     */
    private void HMSActionPerformed(java.awt.event.ActionEvent evt) {
        playButtonSound();
        this.workingArea.removeAll();
        this.workingArea.add(new UIMetaScore(this, dataDate));
        Component c = this.workingArea.getComponent(0);
        if (this.sideMinimized) {
            c.setBounds(MIN_MAX_DIFF / 2, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        } else {
            c.setBounds((MIN_MAX_DIFF / 2) - this.MIN_MAX_DIFF, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                    this.getSize().height - this.getTopHeight());
        }
        updateWindow();
        this.pageMarker = 1;
    }

    /**
     * Change the date to the date the user selected
     *
     * @param d User's selected date
     * @throws AWTException
     */
    public void setDataDate(java.util.Date d) throws AWTException {
        this._dataDate_ = d;
        this.dataDate.setYear(Integer.parseInt(yearFormat.format(this._dataDate_)));
        this.dataDate.setMonth(Integer.parseInt(monthFormat.format(this._dataDate_)));
        this.dataDate.setDay(Integer.parseInt(dayFormat.format(this._dataDate_)));//Year Month Day
        this.dateLabel.setText(formatter.format(this._dataDate_));
        recreateWorkingArea();
    }

    /**
     * Creates side panel containing the buttons to navigate to different screens
     *
     * @param max checks if side panel should be expanded
     */
    private void createSidePanel(int max) {
        this.filler1 = new Box.Filler(new java.awt.Dimension(0, 25), new java.awt.Dimension(0, 25), new java.awt.Dimension(32767, 25));
        this.filler2 = new Box.Filler(new java.awt.Dimension(0, 25), new java.awt.Dimension(0, 25), new java.awt.Dimension(32767, 25));

        this.sidePanel.removeAll();
        this.sidePanel.revalidate();
        this.sidePanel.repaint();

        this.sidePanelMain = new JLayeredPane();
        this.sidePanelToggle = new JLayeredPane();
        this.sidePanelSizeToggle = new JButton();
        this.sidePanel.setLayout(new java.awt.BorderLayout());

        this.sidePanelMain.setLayout(new java.awt.GridLayout(8, 1));

        this.home = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Home.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.home.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/HomePressed.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.home.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/HomeRollover.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.home.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.home.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        this.home.setContentAreaFilled(false);
        this.home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeActionPerformed(evt);
            }
        });
        this.sidePanelMain.add(this.home);

        this.HMS = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Score.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.HMS.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/ScorePressed.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.HMS.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/ScoreRollover.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.HMS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.HMS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        this.HMS.setContentAreaFilled(false);
        this.HMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HMSActionPerformed(evt);
            }
        });
        this.sidePanelMain.add(this.HMS);

        this.accolades = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Trophy.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.accolades.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/TrophyPressed.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.accolades.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/TrophyRollover.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.accolades.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.accolades.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        this.accolades.setContentAreaFilled(false);
        this.accolades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accoladesActionPerformed(evt);
            }
        });
        this.sidePanelMain.add(this.accolades);

        this.hrZones = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/HeartRate.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.hrZones.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/HeartRatePressed.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.hrZones.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/HeartRateRollover.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.hrZones.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.hrZones.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        this.hrZones.setContentAreaFilled(false);
        this.hrZones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hrZonesActionPerformed(evt);
            }
        });
        this.sidePanelMain.add(this.hrZones);


        this.bestDays = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/BestDays.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.bestDays.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/BestDaysPressed.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.bestDays.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/BestDaysRollover.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.bestDays.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.bestDays.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        this.bestDays.setContentAreaFilled(false);
        this.bestDays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bestDaysActionPerformed(evt);
            }
        });
        this.sidePanelMain.add(bestDays);

        this.lifetime = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Lifetime.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.lifetime.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/LifetimePressed.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.lifetime.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/LifetimeRollover.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.lifetime.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.lifetime.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        this.lifetime.setContentAreaFilled(false);
        this.lifetime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lifetimeActionPerformed(evt);
            }
        });
        this.sidePanelMain.add(this.lifetime);


        this.sidePanelMain.add(filler1);

        this.settings = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Settings.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.settings.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/SettingsPressed.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.settings.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/SettingsRollover.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 9, (this.screenSize.height - 100) / 9, java.awt.Image.SCALE_SMOOTH)));
        this.settings.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.settings.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        this.settings.setContentAreaFilled(false);
        this.settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsActionPerformed(evt);
            }
        });
        this.sidePanelMain.add(this.settings);
        this.sidePanel.add(this.sidePanelMain, BorderLayout.LINE_START);

        this.sidePanelToggle.setLayout(new BorderLayout());
        this.sidePanelSizeToggle.setFont(new Font("sansserif", 0, 10)); // NOI18N
        this.sidePanelSizeToggle.setText(".");
        this.sidePanelSizeToggle.setIconTextGap(0);
        this.sidePanelSizeToggle.setMaximumSize(new Dimension(15, 20));
        this.sidePanelSizeToggle.setMinimumSize(new Dimension(15, 20));
        this.sidePanelSizeToggle.setPreferredSize(new Dimension(15, 20));
        this.sidePanelSizeToggle.setContentAreaFilled(true);
        this.sidePanelSizeToggle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sidePanelSizeToggleActionPerformed(evt);
            }
        });
        this.sidePanelToggle.add(this.sidePanelSizeToggle, BorderLayout.CENTER);

        this.sidePanel.add(this.sidePanelToggle, BorderLayout.LINE_END);

        if (max == 1) {
            this.home.setText("Home Dashboard");
            this.hrZones.setText("HR Zones");
            this.bestDays.setText("Best Days");
            this.lifetime.setText("Lifetime Totals");
            this.settings.setText("Settings");
            this.sidePanelSizeToggle.setToolTipText("Minimize Side Bar");
            this.HMS.setText("Health Meta Score");
            this.accolades.setText("Accolades");
        }
        if (max == 0) {
            this.home.setToolTipText("Home Dashboard");
            this.hrZones.setToolTipText("HR Zones");
            this.bestDays.setToolTipText("Best Days");
            this.lifetime.setToolTipText("Lifetime");
            this.settings.setToolTipText("Settings");
            this.sidePanelSizeToggle.setToolTipText("Expand Side Bar");
            this.HMS.setToolTipText("Health Meta Score");
            this.accolades.setToolTipText("Accolades");
        }

        this.sidePanelMain.setBackground(new Color(0, 0, 0, 0));
        this.sidePanelToggle.setBackground(new Color(0, 0, 0, 0));
        this.sidePanel.setBackground(new Color(0, 255, 0));
    }


    /**
     * Generates the top panel of the mainwindow and its buttons
     */
    private void createTopPanel() {
        this.topPanelWidth = this.getSize().width - this.sidePanelWidth;
        this.topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.topPanel.setLayout(null);

        this.exit = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Exit.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 25, (this.screenSize.height - 100) / 25, java.awt.Image.SCALE_SMOOTH)));
        this.exit.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/ExitPressed.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 25, (this.screenSize.height - 100) / 25, java.awt.Image.SCALE_SMOOTH)));
        this.exit.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/ExitRollover.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 25, (this.screenSize.height - 100) / 25, java.awt.Image.SCALE_SMOOTH)));
        this.exit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        this.exit.setContentAreaFilled(false);
        this.exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonSound();
                System.exit(0);
            }
        });
        this.topPanel.add(this.exit);

        this.exit.setBounds(this.topPanelWidth - (this.screenSize.height - 100) / 25, 0, 55, 55);
        this.calendar = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/Calendar.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 25, (this.screenSize.height - 100) / 25, java.awt.Image.SCALE_SMOOTH)));
        this.calendar.setPressedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/CalendarPressed.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 25, (this.screenSize.height - 100) / 25, java.awt.Image.SCALE_SMOOTH)));
        this.calendar.setSelectedIcon(new ImageIcon(((new ImageIcon("src/main/resources/images/CalendarRollover.png")).getImage()).getScaledInstance((this.screenSize.height - 100) / 25, (this.screenSize.height - 100) / 25, java.awt.Image.SCALE_SMOOTH)));
        this.calendar.setContentAreaFilled(false);
        this.calendar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calendarActionPerformed(evt);
            }
        });
        this.topPanel.add(this.calendar);
        this.calendar.setBounds((this.topPanelWidth / 2) - 275, 0, 55, 55);

        this.dateLabel = new JLabel(formatter.format(this._dataDate_));
        this.dateLabel.setFont(new Font("Lucida Grande", Font.BOLD, 24));
        this.topPanel.add(this.dateLabel);
        this.dateLabel.setBounds((this.topPanelWidth / 2) - 125, 5, 250, 50);

        this.fitbit = new JButton(new ImageIcon(((new ImageIcon("src/main/resources/images/fitbit_logo.png")).getImage()).getScaledInstance(100, 25, java.awt.Image.SCALE_SMOOTH)));
        this.fitbit.setContentAreaFilled(false);
        this.topPanel.add(this.fitbit);
        this.fitbit.setBounds(this.topPanelWidth - ((this.screenSize.height - 100) / 25) - 200, 15, 100, 25);
        this.fitbit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fitbitActionPerformed(evt);
            }
        });

    }

    /**
     * Load user's preferences from stored file
     */
    private void loadSettings() {
        if (new File("src/main/resources/app.config").exists()) {
            ObjectInputStream in;
            try {
                in = new ObjectInputStream(new FileInputStream("src/main/resources/app.config"));
                UserConfigs p = (UserConfigs) in.readObject();
                in.close();
                this.tipsOn = p.getConfigs()[0];
                this.soundOn = p.getConfigs()[1];
            } catch (IOException ex) {
                this.tipsOn = true;
                this.soundOn = true;
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                this.tipsOn = true;
                this.soundOn = true;
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            this.tipsOn = true;
            this.soundOn = true;
        }
    }

    /**
     * This will update the top panel accordingly depending on whether the side
     * panel is minimized
     */
    private void updateTopPanel() {
        if (this.sideMinimized == true) {
            this.calendar.setBounds((this.topPanelWidth / 2) - 275, 0, 55, 55);
            this.exit.setBounds(this.topPanelWidth - (this.screenSize.height - 100) / 25, 0, 55, 55);
            this.dateLabel.setBounds((this.topPanelWidth / 2) - 125, 5, 250, 50);
            this.fitbit.setBounds(this.topPanelWidth - ((this.screenSize.height - 100) / 25) - 200, 15, 100, 25);
        } else {
            this.calendar.setBounds((this.topPanelWidth / 2) - 275 - MIN_MAX_DIFF, 0, 55, 55);
            this.exit.setBounds(this.topPanelWidth - (this.screenSize.height - 100) / 25 - MIN_MAX_DIFF, 0, 55, 55);
            this.dateLabel.setBounds((this.topPanelWidth / 2) - 125 - MIN_MAX_DIFF, 5, 250, 50);
            this.fitbit.setBounds(this.topPanelWidth - ((this.screenSize.height - 100) / 25) - MIN_MAX_DIFF - 200, 15, 100, 25);
        }
        updateWindow();
    }

    /**
     * This will update the main working area accordingly to whether or not the
     * side panel is minimized
     */
    public void updateWindow() {
        this.revalidate();
        this.repaint();
    }


    /**
     * @return Daily dashboard
     */
    public dailyDashboard getDashboard() {
        return dashboard;
    }

    /**
     * Once date is changed, reload the current page and update the daily dash
     */
    public void recreateWorkingArea() {
            switch (this.pageMarker) {
                case (0):
                    this.dashboard = new dailyDashboard(this);
                    if(this.isConnected()) { //ensure refresh worked before removing and reloading page
                        this.workingArea.removeAll();
                        this.workingArea.add(this.dashboard);
                    }
                    break;
                case (1):
                    this.dashboard = new dailyDashboard(this);
                    if(this.isConnected()) {
                        this.workingArea.removeAll();
                        this.workingArea.add(new UIMetaScore(this, this.dataDate));
                    }
                    break;
                case (2):
                    this.dashboard = new dailyDashboard(this);
                    if(this.isConnected()) {
                        this.workingArea.removeAll();
                        this.workingArea.add(new UIAccolades(this, this.dataDate, accList));
                    }
                    break;
                case (3):
                    this.dashboard = new dailyDashboard(this);
                    if(this.isConnected()) {
                        this.workingArea.removeAll();
                        this.workingArea.add(new UIHRZ(this, this.dataDate));
                    }
                    break;
                case (4):
                    this.dashboard = new dailyDashboard(this);
                    if(this.isConnected()) {
                        this.workingArea.removeAll();
                        this.workingArea.add(new UIBestDays(this, dataDate));
                    }
                    break;
                case (5):
                    this.dashboard = new dailyDashboard(this);
                    if(this.isConnected()) {
                        this.workingArea.removeAll();
                        this.workingArea.add(new UILifeTime(this, dataDate));
                    }
                    break;
            }

            Component c = this.workingArea.getComponent(0);
            if (this.sideMinimized) {
                c.setBounds(MIN_MAX_DIFF / 2, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                        this.getSize().height - this.getTopHeight());
            } else {
                c.setBounds((MIN_MAX_DIFF / 2) - this.MIN_MAX_DIFF, 5, this.getSize().width - this.getSideWidth() - this.getMinMaxDiff(),
                        this.getSize().height - this.getTopHeight());
            }
            updateWindow();
    }

    /**
     * Export current page to PDF
     *
     * @param jpanel Panel to be exported to PDF
     */
    public void createPDF(JLayeredPane jpanel) {
        playButtonSound();
        com.itextpdf.text.Rectangle r = new com.itextpdf.text.Rectangle(0, 0, jpanel.getWidth(), jpanel.getHeight());
        Document doc = new Document(r);
        String filename = fileChooser();
        if (filename != "INVALID") {
            try {
                PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(filename));
                doc.open();
                PdfContentByte cb = writer.getDirectContent();
                PdfTemplate tp = cb.createTemplate(PageSize.A4.getHeight() * 2, PageSize.A4.getWidth() * 2);
                Graphics2D g2;
                g2 = tp.createGraphics(jpanel.getWidth(), jpanel.getHeight(), new DefaultFontMapper());
                jpanel.print(g2);
                g2.dispose();
                cb.addTemplate(tp, 0, 0);
                PopUpWindow saveSuccess = new PopUpWindow(filename, "Save Successful!", 8);
            } catch (Exception e) {
                new PopUpWindow("Please try again.", "Error saving file");
            }
        }
        doc.close();
    }

    /**
     * Frame in which the user will select the location where the PDF is to be created
     *
     * @return file location
     */
    private String fileChooser() {
        JFrame frame = new JFrame();
        JFileChooser saveLoc = new JFileChooser() {
            // Overwrite approval, in the event that file already exists
            @Override
            public void approveSelection() {
                File f = new File(getSelectedFile().toString() + ".pdf");
                if (f.exists() && getDialogType() == SAVE_DIALOG) {
                    int result = JOptionPane.showConfirmDialog(this, "The file exists, overwrite?", "Existing file", JOptionPane.YES_NO_CANCEL_OPTION);
                    switch (result) {
                        case JOptionPane.YES_OPTION:
                            super.approveSelection();
                            return;
                        case JOptionPane.NO_OPTION:
                            return;
                        case JOptionPane.CLOSED_OPTION:
                            return;
                        case JOptionPane.CANCEL_OPTION:
                            cancelSelection();
                            return;
                    }
                }
                super.approveSelection();
            }
        };
        String[] pages = {"Dashboard", "Health Meta Score", "Accolades", "Heart Rate Zones", "Best Days", "Lifetime Totals"};
        saveLoc.setSelectedFile(new File(Integer.toString(dataDate.getDay()) + "-"
                + Integer.toString(dataDate.getMonth()) + "-" + Integer.toString(dataDate.getYear())
                + "-" + pages[this.pageMarker]));
        saveLoc.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        saveLoc.setFileFilter(new FileNameExtensionFilter("PDF Documents", "pdf"));
        int userSelection = saveLoc.showSaveDialog(frame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = saveLoc.getSelectedFile();
            if (fileToSave.toString().endsWith(".pdf") || fileToSave.toString().endsWith(".PDF"))
                return fileToSave.getAbsolutePath();
            else
                return (fileToSave.getAbsolutePath() + ".pdf");
        }
        return "INVALID";
    }

    /**
     * Button sounds on/off
     *
     * @param option on or off
     */
    public void setSoundOn(boolean option) {
        this.soundOn = option;
    }

    /**
     * Daily tips on/off
     *
     * @param option on or off
     */
    public void setTipsOn(boolean option) {
        this.tipsOn = option;
    }

    /**
     * Checks if communication with Fitbit is established
     *
     * @return whether connected or not
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Sets connection status
     *
     * @param connected whether connected or not
     */
    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * Displays the background of the app
     */
    private void displayBackground() {
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("src/main/resources/images/background.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        panel = new BackgroundPanel(myImage, BackgroundPanel.SCALED, 0.0f, 0.0f);
        this.getContentPane().add(panel);

    }
}
