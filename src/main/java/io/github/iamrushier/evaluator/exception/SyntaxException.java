package io.github.iamrushier.evaluator.exception;

/**
 * Thrown when a mathematical expression has invalid syntax, optionally containing the character index where the error occurred.
 */
public class SyntaxException extends EvaluationException {

    private final int position;

    /**
     * Constructs a {@code SyntaxException} with the specified detail message and unknown position (-1).
     *
     * @param message the detail message
     */
    public SyntaxException(String message) {
        this(message, -1);
    }

    /**
     * Constructs a {@code SyntaxException} with the specified detail message and source position.
     *
     * @param message the detail message
     * @param position the zero-based character index where the error was detected, or -1 if unknown
     */
    public SyntaxException(String message, int position) {
        super(formatMessage(message, position));
        this.position = position;
    }

    private static String formatMessage(String message, int position) {
        if (position >= 0 && message != null && !message.contains("at position")) {
            return message + " at position " + position;
        }
        return message;
    }

    /**
     * Returns the character position in the expression where the syntax error occurred, or -1 if unknown.
     *
     * @return the zero-based character index, or -1
     */
    public int getPosition() {
        return position;
    }
}
