package ca.uwo.csd.cs2212.team02;

import javax.swing.JComponent;
import java.awt.*;

/**
 * Creates a bar chart showing progress toward "ideal" Health Meta Score
 */
public class HMSBarChartComponent extends JComponent {
    private int panelW;
    private int panelH;
    private MainWindow main;

    /**
     * @param panelW width of the panel
     * @param panelH height of the panel
     * @param main   main window
     */
    public HMSBarChartComponent(int panelW, int panelH, MainWindow main) {
        this.panelW = panelW;
        this.panelH = panelH;
        this.main = main;
    }

    /**
     * Creates the bar chart
     *
     * @param g
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        BarChart c = new BarChart(this.panelW / 3, this.panelH / 3);
        dailyDashboard dash = main.getDashboard();
        String[] names = HeartRate.getNames();
        c.add(HealthMetaScore.getCaloriesFraction());
        c.addlegends(String.valueOf((int) dash.getDataCalories().getValue()));

        c.add(HealthMetaScore.getFloorsFraction());
        c.addlegends(String.valueOf((int) dash.getDataFloors().getValue()));

        c.add(HealthMetaScore.getDistanceFraction());
        c.addlegends(String.valueOf((int) dash.getDataDistance().getValue()));

        c.add(HealthMetaScore.getStepsFraction());
        c.addlegends(String.valueOf((int) dash.getDataSteps().getValue()));

        c.draw(g2);
    }
}