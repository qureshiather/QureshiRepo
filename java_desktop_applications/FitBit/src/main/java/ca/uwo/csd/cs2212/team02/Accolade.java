package ca.uwo.csd.cs2212.team02;

import java.io.Serializable;

/**
 * Accolade is an object that contains all the information about an individual accolade, including
 * ID, type, threshold, name and description
 */

public class Accolade implements Serializable {

    private int ID;
    private int DailyorLifeT;
    private int threshold;
    private String name;
    private boolean isLocked;
    private Date dateUnlocked;
    private String description;
    private static final long serialVersionUID = 2L;

    /**
     * Constructor initializes the Accolade object
     *
     * @param id          Accolade #
     * @param threshold   value the user needs to reach to unlock it
     * @param DorT        Daily vs Lifetime
     * @param name        Name of accolade
     * @param description Description of accolade
     */
    public Accolade(int id, int threshold, int DorT, String name, String description) {
        this.ID = id;
        this.DailyorLifeT = DorT;
        this.threshold = threshold;
        this.name = name;
        this.isLocked = true;
        this.dateUnlocked = null;
        this.description = description;

    }

    /**
     * @return ID of accolade
     */
    public int getID() {
        return ID;
    }

    /**
     * @return threshold of accolade
     */
    public int getThreshold() {
        return threshold;
    }

    /**
     * @return name of accolade
     */
    public String getName() {
        return name;
    }

    /**
     * @return 0 if daily, 1 if LT
     */
    public int getDailyorLifeT() {
        return DailyorLifeT;
    }

    /**
     * @return whether the user has unlocked the accolade (false if so, true if not)
     */
    public boolean isLocked() {
        return isLocked;
    }

    /**
     * @param locked if accolade is locked or not
     */
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    /**
     * @return Date on which the user unlocked the accolade
     */
    public Date getDateUnlocked() {
        return this.dateUnlocked;
    }

    /**
     * Sets the date that the user achieved the accolade
     *
     * @param date date of accomplishment
     */
    public void setDateUnlocked(Date date) {
        this.dateUnlocked = date;
    }

    /**
     * @return description of accolade
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Determines if user is viewing data from before they unlocked they accolade or not
     *
     * @param userDate date the user is currently viewing
     * @return whether the badge should be visible on the accolade page
     */
    public boolean isVisible(Date userDate) {
        if (!isLocked()) {
            return this.getDateUnlocked().compareTo(userDate) != 1;
        }
        return false;
    }
}
