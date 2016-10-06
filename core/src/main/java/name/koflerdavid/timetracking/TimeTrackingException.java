package name.koflerdavid.timetracking;


public class TimeTrackingException extends Exception {
    public TimeTrackingException(final String message) {
        super(message);
    }

    public TimeTrackingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
