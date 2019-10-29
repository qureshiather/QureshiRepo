/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uwo.csd.cs2212.team02;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author Henry
 */
public class DailyTips extends JFrame {
    private int NumTips;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private int tipIndex;
    private GridBagConstraints c;

    /**
     * Constructor to generate first tip
     */
    public DailyTips() {
        initUI();
        generateTip();

        //this.getContentPane().setBackground(new Color(255,255,255));
        //this.set

        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        //this.setOpacity((float) 0.93);


    }

    /**
     * Draw UI
     */
    private void initUI() {
        this.setSize(450, 350);
        BufferedImage myImage = null;
        try {
            myImage = ImageIO.read(new File("src/main/resources/images/TipCloud.png"));
        } catch (IOException ex) {
            Logger.getLogger(DailyTips.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setContentPane(new ImagePanel(getScaledImage(myImage, this.getWidth(), this.getHeight())));
        this.getContentPane().setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 5, 5);
        JButton prev = new JButton("Prev");
        prev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decreaseIndex();
            }
        });
        prev.setSize(new Dimension(50, 50));
        JButton next = new JButton("Next");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                increaseIndex();
            }
        });
        JButton exit = new JButton("Close");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });
        JLabel title = new JLabel("Tip of the Day");
        title.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        this.add(prev, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 1;
        this.add(next, c);
        c.gridx = 2;
        this.add(title, c);
        c.gridx = 3;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        this.add(exit, c);

        FileInputStream file = null;
        try {
            file = new FileInputStream(new File("src/main/resources/HealthTips.xls"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DailyTips.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            //Get the workbook instance for XLS file
            this.workbook = new HSSFWorkbook(file);
        } catch (IOException ex) {
            Logger.getLogger(DailyTips.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Get first sheet from the workbook
        this.sheet = workbook.getSheetAt(0);
        this.NumTips = sheet.getPhysicalNumberOfRows();
        Random NumberGenerator = new Random();
        this.tipIndex = NumberGenerator.nextInt(this.NumTips);


    }

    /**
     * Generate a tip from .csv file
     */
    private void generateTip() {
        HSSFRow row = sheet.getRow(this.tipIndex);
        JTextArea tip = new JTextArea(row.getCell(0).toString());
        tip = textAreaProperties(tip);
        tip.setSize(350, 200);
        c.ipady = 40;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(tip, c);
        JTextArea URL = new JTextArea(row.getCell(1).toString());
        URL = textAreaProperties(URL);
        URL.setSize(250, 200);
        c.ipady = 0;
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.PAGE_END;
        this.add(URL, c);
    }

    /**
     * Create space for text
     * @param textArea
     * @return
     */
    private JTextArea textAreaProperties(JTextArea textArea) {
        textArea.setBorder(null);
        textArea.setBackground(new Color(0, 0, 0, 0));
        textArea.setEditable(false);
        textArea.setCursor(null);
        textArea.setOpaque(false);
        textArea.setFocusable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);
        return textArea;
    }

    /**
     * Get next tip
     */
    private void increaseIndex() {
        this.tipIndex = (this.tipIndex + 1) % this.NumTips;
        clearTip();
    }

    /**
     * Get previous tip
     */
    private void decreaseIndex() {
        if (this.tipIndex == 0) {
            this.tipIndex = this.NumTips;
            return;
        } else {
            this.tipIndex--;
        }
        clearTip();
    }

    /**
     * move to next tip
     */
    private void clearTip() {
        this.getContentPane().remove(5);
        this.getContentPane().remove(4);
        this.generateTip();
        this.revalidate();
        this.repaint();
    }

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     *
     * @param srcImg - source image to scale
     * @param w      - desired width
     * @param h      - desired height
     * @return - the new resized image
     */
    private BufferedImage getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }
}

/**
 * Small image window
 */
class ImgPanel extends JComponent {
    private Image image;

    public ImgPanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}