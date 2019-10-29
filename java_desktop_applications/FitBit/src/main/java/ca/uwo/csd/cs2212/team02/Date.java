package ca.uwo.csd.cs2212.team02;

import java.io.Serializable;

/**
 * Class storing month, day, and year
 */
public class Date implements Serializable {
    private int year, month, day;

    /**
     * Constructor for the date class
     *
     * @param year
     * @param month
     * @param day
     */
    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Getter for year
     *
     * @return The year the user has navigated to
     */
    public int getYear() {
        return year;
    }

    /**
     * Getter for month
     *
     * @return The month the user has navigated to
     */
    public int getMonth() {
        return month;
    }

    /**
     * Getter for day
     *
     * @return The day the user has navigated to
     */
    public int getDay() {
        return day;
    }

    /**
     * Setter for year
     *
     * @param year The year the user has navigated to
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Setter for month
     *
     * @param month The month the user has navigated to
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Setter for day
     *
     * @param day The day  the user has navigated to
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Format the date as YYYY-MM-DD
     *
     * @return String containing the formatted date
     */
    public String format() {

        String date = year + "-";
        if (month < 10) {
            date = date.concat("0");
        }
        date = date.concat(Integer.toString(month) + "-");

        if (day < 10) {
            date = date.concat("0");
        }
        date = date.concat(Integer.toString(day));

        return date;
    }

    /**
     * Compares this date to another date
     *
     * @param otherDate compared to date
     * @return -1 if before, 0 if same day, 1 if after
     */
    public int compareTo(Date otherDate) {
        if (this.getYear() < otherDate.getYear()) {
            return -1;
        } else if (this.getYear() > otherDate.getYear()) {
            return 1;
        } else {
            if (this.getMonth() < otherDate.getMonth()) {
                return -1;
            } else if (this.getMonth() > otherDate.getMonth()) {
                return 1;
            } else {
                if (this.getDay() < otherDate.getDay()) {
                    return -1;
                } else if (this.getDay() > otherDate.getDay()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
}
