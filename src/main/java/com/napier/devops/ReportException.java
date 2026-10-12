package com.napier.devops;

/**
 * Thrown when a report cannot be produced, for example because a query fails
 * or a report file cannot be written. App catches it and exits with status 1,
 * so CI fails instead of passing with missing or incomplete reports.
 */
public class ReportException extends RuntimeException {

    /**
     * Creates the exception.
     *
     * @param message what went wrong
     * @param cause   the underlying exception, or null if there is none
     */
    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
