package filters;

/**
 * The exception thrown when parsing a string fails.
 */
public class FailedSyntaxException extends Exception {
    public FailedSyntaxException(String s) {
        super(s);
    }
}
