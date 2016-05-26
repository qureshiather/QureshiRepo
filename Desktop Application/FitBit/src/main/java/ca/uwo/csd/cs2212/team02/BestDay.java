package ca.uwo.csd.cs2212.team02;


/**
 * Stores user's best day (date and value)
 */
public class BestDay {
    private String day;
    private double value;

    /**
     * Constructor for the best day class
     *
     * @param day   date the user achieved his/her highest value
     * @param value value reached on that date
     */
    public BestDay(String day, double value) {
        this.day = day;
        this.value = value;
    }

    /**
     * Getter for the date
     *
     * @return the date on which this was achieved
     */
    public String getDay() {
        return day;
    }

    /**
     * Setter for the date
     *
     * @param day the date the user achieved his/her highest value
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Getter for the best day value
     *
     * @return the highest single day value for this data type
     */
    public Double getValue() {
        return value;
    }

    /**
     * Setter for the best day value
     *
     * @param value highest value user achieved
     */
    public void setValue(Double value) {
        this.value = value;
    }


}

