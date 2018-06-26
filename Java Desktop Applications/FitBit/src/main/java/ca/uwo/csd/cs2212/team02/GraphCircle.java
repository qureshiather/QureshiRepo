package ca.uwo.csd.cs2212.team02;

import java.awt.AWTException;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.lang.String;
import java.lang.Integer;
import java.awt.Font;
import java.awt.image.BufferedImage;

/**
 * Used to generate circle graphs to display data
 */
public class GraphCircle extends JPanel {
    private int circleSize, circleX, circleY; //inital circle: size and position
    private int startAngle = 90, arcAngle1, arcAngle2, arcPercentage, completed;
    private int linewidth = 10;
    private int frameWidth, frameHeight;
    private float fontSize;
    private String str;
    private int goal;
    //startAngle: arc starts from the 12 o'clock and is completed to the counter-clockwise
    //input the posion of circle:x,y is the location of its right corner
    //circleSize is the radius of circle
    //arcPercentage is the completed percentage in 100%(means its an integer between 0 and 100)
    //inewidth is the arc width

    /**
     * Constructor method
     *
     * @param arcPercentage - the integer percentage of completed/goal; i.e.
     *                      1000 steps out of 8000 step goal = 80
     * @param completed     - the number of units completed; i.e. if 1000 floors
     *                      climbed, enter 1000
     * @param str           - the name of the units; i.e. if 1000 floors climbed, enter 1000
     */
    public GraphCircle(int arcPercentage, int completed, String str, int goal) {
        if (str.equals("Skip")) {
            this.circleSize = 450;
        } else {
            this.circleSize = 200;
        }
        this.circleX = linewidth * 4;
        this.circleY = linewidth * 4;
        this.frameHeight = circleSize + linewidth * 6;
        this.frameWidth = circleSize + linewidth * 6;
        this.arcPercentage = arcPercentage;
        this.goal = goal;
        this.linewidth = linewidth;
        this.completed = completed;
        this.str = str;
        setSize(frameWidth, frameHeight);
        setVisible(true);
    }

    /**
     * Paints the circle graph
     */

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setComposite(AlphaComposite.Src);
        g2.setStroke(new BasicStroke(linewidth));

        //calculate the arc: arcAngle2 is the completed arc, arcAngle1 is the remaining arc
        arcAngle1 = 360 - arcAngle2;
        arcAngle2 = arcPercentage * 360 / 100;


        //remaining goal
        g2.setColor(new Color(213, 36, 36)); //RED
        g2.drawArc(circleX, circleY, circleSize, circleSize, startAngle, -arcAngle1);


        //completed
        g2.setColor(new Color(17, 89, 235)); //BLUE
        g2.drawArc(circleX, circleY, circleSize, circleSize, startAngle, arcAngle2);

        if (this.arcPercentage > 100) {
            g2.setColor(new Color(48, 203, 0)); //GREEN
            g2.drawArc(circleX, circleY, circleSize, circleSize, startAngle, arcAngle2 - 360);
        }

        //draw string of completeness
        String str1 = Integer.toString(completed);
        fontSize = circleSize / 8;
        Font font = g.getFont().deriveFont(fontSize);
        g2.setFont(font);
        g2.setColor(new Color(37, 31, 25)); //Brown/black
        // adjust the postion of string by its length
        if (!str.equals("Skip")) {
            g2.drawString(str1, circleX + circleSize / 2 - str1.length() * 8, circleY + circleSize / 3 + fontSize / 2);
            g2.drawString(str, circleX + circleSize / 2 - str.length() * 6, circleY + circleSize / 2 + fontSize);
        } else {
            fontSize = circleSize / 4;
            font = g.getFont().deriveFont(fontSize);
            g2.setFont(font);
            g2.drawString(str1, circleX + circleSize / 2 - fontSize / 2 - str1.length() * 12, circleY + circleSize / 2 + fontSize / 2);
        }
        if (goal >= 0) {
            String temp = "" + goal;
            g2.setColor(Color.BLACK);
            g2.drawString(temp, circleX + circleSize / 2 - temp.length() * 8, circleY - 20);
            g2.drawLine(circleX + circleSize / 2, circleY - 10, circleX + circleSize / 2, circleY + 10);

        }

        g2.dispose();
    }

    /**
     * Generates an image of the GraphCircle
     *
     * @return a BufferedImage of the circle graph generated in this class
     * @throws AWTException
     */
    public BufferedImage createImage() throws AWTException {

        int w = this.frameWidth;
        int h = this.frameHeight;
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.setComposite(AlphaComposite.Clear);
        this.paint(g);
        g.dispose();
        return bi;
    }
}
     