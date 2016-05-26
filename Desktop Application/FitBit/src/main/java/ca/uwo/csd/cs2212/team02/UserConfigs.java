/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uwo.csd.cs2212.team02;

/**
 * Object for settings configuration, which will be serialized / deserialized to load / store data
 */
public class UserConfigs implements java.io.Serializable {
    private boolean tips, sounds;
    private static final long serialVersionUID = 2L;

    public UserConfigs() {

    }

    /**
     * Constructor
     *
     * @param settings array storing which metrics should be shown
     */
    public UserConfigs(boolean[] settings) {
        this.tips = settings[0];
        this.sounds = settings[1];
    }

    /**
     * return settings
     *
     * @return
     */
    public boolean[] getConfigs() {
        return new boolean[]{tips, sounds};
    }
}
