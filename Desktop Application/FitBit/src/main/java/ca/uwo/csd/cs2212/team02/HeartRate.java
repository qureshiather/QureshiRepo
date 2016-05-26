package ca.uwo.csd.cs2212.team02;

/**
 * Stores minutes in each heart rate zone and resting heart rate
 */
public class HeartRate {
    public static String[] HRNAMES = {"Out of Range", "Fat Burn", "Cardio", "Peak"};
    public static int[] HRMins = new int[4];
    public static int resting;

    /**
     * Constructor
     *
     * @param mins    Array storing # of  mins in each zone
     * @param resting user's resting heart rate
     */
    public HeartRate(int[] mins, int resting) {
        this.resting = resting;
        for (int i = 0; i < 4; i++) {
            this.HRMins[i] = mins[i];
        }

    }

    /**
     * Getter for heart rate zone minute information
     *
     * @return Array storing minutes in each of the four heart rate zones
     */
    public static int[] getHRMins() {
        return HRMins;
    }


    /**
     * Getter for heart rate zone minute information
     *
     * @return Array storing names in each of the four heart rate zones
     */
    public static String[] getNames() {
        return HRNAMES;
    }

    /**
     * Getter for user's resting heart rate
     *
     * @return user's resting heart rate for the selected date
     */
    public static int getResting() {
        return resting;
    }
}
