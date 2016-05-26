package ca.uwo.csd.cs2212.team02;


import java.awt.*;
import java.util.ArrayList;

/**
 * Create bar chart UI display
 */

public class BarChart {
    private int width;
    private int height;
    private ArrayList<Double> data;
    private ArrayList<String> legends;
    private ArrayList<String> datanames;

    public BarChart(int aWidth, int aHeight) {
        width = aWidth;
        height = aHeight;
        data = new ArrayList<Double>();
        legends = new ArrayList<String>();
        datanames = new ArrayList<String>();
    }

    /**
     * Add value to array
     *
     * @param value to be added
     */
    public void add(double value) {
        data.add(value);
    }

    /**
     * add label on the bar
     *
     * @param legend to be added
     */
    public void addlegends(String legend) {
        legends.add(legend);
    }

    /**
     * add name to chart
     *
     * @param name
     */
    public void addnames(String name) {
        datanames.add(name);
    }

    /**
     * Draw bar chart
     *
     * @param g2
     */
    public void draw(Graphics2D g2) {
        int i = 0;
        double max = 1;

        Font labelFont = new Font("SansSerif", Font.PLAIN, 20);
        FontMetrics labelFontMetrics = g2.getFontMetrics(labelFont);
        int stringlength = labelFontMetrics.charWidth('A') * 10;

        int xlength = width - 1;
        int yheight = height - 1;


        int yhigh = yheight;
        for (i = 0; i < data.size(); i++) {

            int barHeight = yheight / (int) (data.size() * 1.5);
            int barWidth = (int) Math.round(xlength * (data.get(i) / max));
            Rectangle bar =
                    new Rectangle(stringlength, yhigh, barWidth, barHeight);
            Rectangle full = new Rectangle(stringlength, yhigh, xlength, barHeight);
            if (data.get(i) > 1) {
                g2.setColor(new Color(48, 203, 0)); //GREEN
                g2.fill(bar);
                g2.draw(bar);
                g2.setColor(new Color(17, 89, 235)); //BLUE
                g2.fill(full);
                g2.draw(full);
            } else {
                g2.setColor(new Color(213, 36, 36)); //RED
                g2.fill(full);
                g2.draw(full);
                g2.setColor(new Color(17, 89, 235)); //BLUE
                g2.fill(bar);
                g2.draw(bar);
            }

            g2.setFont(labelFont);
            g2.setColor(new Color(37, 31, 25));
            g2.drawString(legends.get(i), stringlength + barWidth, yhigh + labelFontMetrics.getAscent());

            yhigh = yhigh - barHeight - 30;
        }
    }
}