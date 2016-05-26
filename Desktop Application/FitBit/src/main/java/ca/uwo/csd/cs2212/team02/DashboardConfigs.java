/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uwo.csd.cs2212.team02;

/**
 * Custom dashboard configurations
 */
public class DashboardConfigs implements java.io.Serializable {

    private boolean calories, sedentary, active, HMS, steps, floors, distance;
    private static final long serialVersionUID = 2L;

    /**
     * Constructor
     *
     * @param settings user's settings
     */
    public DashboardConfigs(boolean[] settings) {
        this.calories = settings[0];
        this.sedentary = settings[1];
        this.active = settings[2];
        this.HMS = settings[3];
        this.steps = settings[4];
        this.floors = settings[5];
        this.distance = settings[6];
    }

    /**
     * @return user's settings
     */
    public boolean[] getConfigs() {
        return new boolean[]{calories, sedentary, active, HMS, steps, floors, distance};
    }
}
