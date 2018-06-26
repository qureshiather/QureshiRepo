package ca.uwo.csd.cs2212.team02;

import java.util.LinkedList;

/**
 * AccoladeConfig is an object containing a linked list of accolades, which would be serialized / deserialized
 * to store the accolade status
 */

public class AccoladeConfig implements java.io.Serializable {
    private LinkedList<Accolade> fullList = new LinkedList<Accolade>();

    private static final long serialVersionUID = 2L;

    /**
     * Constructor
     *
     * @param list of all accolades incl. date unlocked/whether unlocked or not
     */
    public AccoladeConfig(LinkedList<Accolade> list) {
        fullList = list;
    }

    /**
     * @return List of all accolades
     */
    public LinkedList<Accolade> geFullList() {
        return fullList;
    }
}
