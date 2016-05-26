package ca.uwo.csd.cs2212.team02;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AccoladeAchievements generates the UI of the accolades sections and calls on AccoladeConfig to store and load serialized
 * configuration objects.
 */
public class AccoladeAchievements extends JFrame {


    private LinkedList<Accolade> unlocked;

    private int index;
    private GridBagConstraints c;
    private JButton badge;


    /**
     * @param unlocked list of unlocked accolades
     */
    public AccoladeAchievements(LinkedList<Accolade> unlocked) {
        this.unlocked = unlocked;
        initUI();
        generateScreen();
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
    }

    /**
     * Paint achievement screen
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


        JLabel title = new JLabel("CONGRATULATIONS!");
        title.setFont(new Font("Lucida Grande", Font.BOLD, 20));
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
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 2;
        c.gridy = GridBagConstraints.PAGE_END;
        this.add(prev, c);
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = GridBagConstraints.RELATIVE;
        c.gridy = GridBagConstraints.PAGE_END;
        c.fill = 0;
        this.add(next, c);
        c.gridx = 3;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(title, c);
        c.gridx = 4;
        c.gridy = GridBagConstraints.PAGE_END;
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        this.add(exit, c);
    }

    /**
     * Generate the accolade to be shown
     */
    private void generateScreen() {

        Accolade temp = unlocked.get(this.index);
        JTextArea AccName = new JTextArea(temp.getName());
        AccName = textAreaProperties(AccName);
        AccName.setFont(new Font("Lucida Grande", Font.BOLD, 14));
        AccName.setSize(200, 70);
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 4;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(AccName, c);


        JLabel logo = new JLabel(new ImageIcon(((new ImageIcon("src/main/resources/images/" + temp.getName() + ".png")).getImage()).getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        logo.setSize(200, 200);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        this.add(logo, c);
        //logo.setBounds(10, 20, 240, 200);

        JTextArea AccDes = new JTextArea(temp.getDescription());
        AccDes = textAreaProperties(AccDes);
        c.ipady = 30;
        AccDes.setSize(250, 200);
        c.ipady = 30;
        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 3;
        c.anchor = GridBagConstraints.PAGE_END;
        this.add(AccDes, c);

    }

    /**
     * View next accolade
     */
    private void increaseIndex() {
        if (this.index == unlocked.size() - 1) {
            this.index = 0;
        } else {
            this.index++;
        }
        clearScreen();
    }

    /**
     * View previous accolade
     */
    private void decreaseIndex() {
        if (this.index == 0) {
            this.index = unlocked.size() - 1;
            return;
        } else {
            this.index--;
        }
        clearScreen();
    }

    /**
     * Formats text area
     *
     * @param textArea to be formatted
     * @return Formatted text area
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
     * Clears screen for next accolade and paints it
     */
    private void clearScreen() {
        this.getContentPane().remove(6);
        this.getContentPane().remove(5);
        this.getContentPane().remove(4);
        this.generateScreen();
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
 * Paints small achievement window
 */
class ImagePanel extends JComponent {
    private Image image;

    public ImagePanel(Image image) {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
