package ca.uwo.csd.cs2212.team02;


import java.io.IOException;

/**
 * Health Meta Score provides users with an indicator of their overall health level for that day
 * Calculated using target values for different metrics along with various weights
 */
public class HealthMetaScore {

    private static final int[] DENOMINATOR = {9000, 12, 35, 2200}; //target values
    private static final String[] DATA_TYPE = {"steps", "distance travelled", "floors climbed", "calories burned"};
    private static double stepsFraction;
    private static double floorsFraction;
    private static double caloriesFraction;
    private static double distanceFraction;


    private static DataType steps;
    private static DataType distance;
    private static DataType floors;
    private static DataType calories;
    private static int score;
    private MainWindow main;

    /**
     * @param date user-selected date
     * @param dash main window
     * @throws APIException when communication isn't properly established
     * @throws IOException
     */

    public HealthMetaScore(Date date, dailyDashboard dash) throws APIException, IOException {
        steps = dash.getDataSteps();
        distance = dash.getDataDistance();
        floors = dash.getDataFloors();
        calories = dash.getDataCalories();

        stepsFraction = steps.getValue() / 9000;
        floorsFraction = floors.getValue() / 35;
        caloriesFraction = calories.getValue() / 2200;
        distanceFraction = distance.getValue() / 12;

        this.score = (int) (((stepsFraction) * 0.4 + (floorsFraction) * 0.15 + (caloriesFraction) * .3 + (distanceFraction) * .15) * 1000);
    }

    /**
     * Getter for steps fraction
     *
     * @return Ratio of current val of steps to the target value for HMS
     */
    public static double getStepsFraction() {
        return stepsFraction;
    }

    /**
     * Getter for floors fraction
     *
     * @return Ratio of current val of floors  to the target value for HMS
     */
    public static double getFloorsFraction() {
        return floorsFraction;
    }

    /**
     * Getter for calories fraction
     *
     * @return Ratio of current val of calories to the target value for HMS
     */
    public static double getCaloriesFraction() {
        return caloriesFraction;
    }

    /**
     * Getter for distance fraction
     *
     * @return Ratio of current val of steps to the target value for HMS
     */
    public static double getDistanceFraction() {
        return distanceFraction;
    }

    /**
     * Getter for score
     *
     * @return user's health meta score for the day
     */
    public static int getScore() {
        return score;
    }

    /**
     * @return ID of the lowest of the four fraction values
     */
    private static int lowest() {
        double[] temp = {stepsFraction, distanceFraction, floorsFraction, caloriesFraction};
        int lowest = 0;
        for (int i = 1; i < 4; i++) {
            if (temp[i] < temp[lowest]) {
                lowest = i;
            }
        }
        return lowest; // lowest is the ID of the dataType
    }

    /**
     * Provides user with a suggestion as to how to improve HMS
     *
     * @return Suggestion as to how the user can improve their HMS
     */
    public static String suggestion() {
        return "To improve your health meta score, you need to work on increasing your " + DATA_TYPE[lowest()] + "!";
    }

    /**
     * @return steps val
     */
    public static double getSteps() {
        return steps.getValue();
    }

    /**
     * @return distance val
     */
    public static double getDistance() {
        return distance.getValue();
    }

    /**
     * @return floors val
     */
    public static double getFloors() {
        return floors.getValue();
    }

    /**
     * @return cals val
     */
    public static double getCalories() {
        return calories.getValue();
    }
}
