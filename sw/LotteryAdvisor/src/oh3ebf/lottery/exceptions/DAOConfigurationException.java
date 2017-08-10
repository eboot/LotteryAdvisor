/**
 * Software: DataFeedLib
 * Module: DAOConfigurationException class
 * Version: 0.1
 * Licence: GPL2
 * Owner: Kim Kristo
 * Date creation : 7.1.2016
 *
 */
package oh3ebf.lottery.exceptions;

public class DAOConfigurationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a DAOConfigurationException with the given detail message.
     *
     * @param message The detail message of the DAOConfigurationException.
     */
    public DAOConfigurationException(String message) {
        super(message);
    }

    /**
     * Constructs a DAOConfigurationException with the given root cause.
     *
     * @param cause The root cause of the DAOConfigurationException.
     */
    public DAOConfigurationException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a DAOConfigurationException with the given detail message and
     * root cause.
     *
     * @param message The detail message of the DAOConfigurationException.
     * @param cause The root cause of the DAOConfigurationException.
     */
    public DAOConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
