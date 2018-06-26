package ca.uwo.csd.cs2212.team02;

/**
 * Exception class for fitbit API related errors
 */

public class APIException extends Exception {

    private int errType;

    /**
     * Constructor for the exception class
     *
     * @param type type of error
     */
    public APIException(int type) {

        errType = type;
    }

    /**
     * Getter for errType
     *
     * @return the integer value of the error type
     */

    public int getErrType() {

        return errType;
    }
}
