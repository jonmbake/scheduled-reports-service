package com.jonbake.report.exception;

/**
 * Transport exception.
 */
public class TransportException extends ReportsException {
    /**
     * Construct with message and type.
     *
     * @param message - error message
     */
    public TransportException (final String message) {
        super(message, "Report Transport Error");
    }
}
