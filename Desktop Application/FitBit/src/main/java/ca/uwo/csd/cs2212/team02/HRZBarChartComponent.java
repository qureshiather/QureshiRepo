package ca.uwo.csd.cs2212.team02;

import javax.swing.JComponent;
import java.awt.*;

public class HRZBarChartComponent extends JComponent {
    private int panelH;
    private int panelW;

    public HRZBarChartComponent(int panelW, int panelH) {
        this.panelH = panelH;
        this.panelW = panelW;
    }

    /**
     * Create the graphs
     *
     * @param g
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        BarChart c = new BarChart(panelW / 3, panelH / 3);
        double sum = 0.0;
        int[] values = HeartRate.getHRMins();
        String[] names = HeartRate.getNames();
        for (int i = 0; i < 4; i++) {
            sum += values[i];
        }

        for (int i = 3; i >= 0; i--) {
            c.add(values[i] / sum);
            c.addlegends(" "+String.valueOf(values[i])+" mins");
        }

        c.draw(g2);
    }
}