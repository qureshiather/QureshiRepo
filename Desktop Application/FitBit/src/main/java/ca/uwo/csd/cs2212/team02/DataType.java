package ca.uwo.csd.cs2212.team02;

import java.io.IOException;

/**
 * Object which acts as the data type for all the different metrics displayed
 */
public class DataType {
    private Date date;
    private int ID;
    private double value;
    private double goal;
    private boolean goalAchieved;
    private BestDay bestDay;
    private double lifeTime;

    /**
     * Constructor for data type class
     *
     * @param ID   indicator of type of data (e.g. steps, distance, etc.)
     * @param date date for which the data will be returned
     */
    public DataType(int ID, Date date) throws APIException,IOException{
        this.ID = ID;

        this.value = APIHandling.getDailyData(ID, date);

        if (ID < 3) {
            this.bestDay = APIHandling.getBestDayData(ID);
            this.lifeTime = APIHandling.getLifeTimeData(ID);
        }
        if (ID < 4) {
            this.goal = APIHandling.getGoalData(ID);
            if (goal <= value) {
                goalAchieved = true;
            } else {
                goalAchieved = false;
            }
        }
    }

    /**
     * Getter for date
     *
     * @return Date being used to generate data
     */
    public Date getDate() {
        return date;
    }

    /**
     * Getter for data ID
     *
     * @return ID of current data type
     */
    public int getID() {
        return ID;
    }

    /**
     * @return Current daily value of the data type
     */
    public double getValue() {
        return value;
    }

    /**
     * Getter for daily Goal
     *
     * @return Goal for current data type
     */
    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal){
        this.goal = goal;
    }

    /**
     * Getter for best day
     *
     * @return BestDay for current data type, storing a date and value
     */
    public BestDay getBestDay() {
        return bestDay;
    }

    /**
     * Getter for life time total
     *
     * @return Lifetime totals for current data type
     */
    public double getLifeTime() {
        return lifeTime;
    }

    /**
     * Checks whether the user daily data is higher than the daily goal
     *
     * @return true if goal has been reached, false otherwise
     */
    public boolean isGoalAchieved() {
        return goalAchieved;
    }
}
