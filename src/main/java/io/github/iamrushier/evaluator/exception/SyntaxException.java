package io.github.iamrushier.evaluator.exception;

/**
 * Thrown when a mathematical expression has invalid syntax.
 */
public class SyntaxException extends EvaluationException {
    /**
     * Constructs a {@code SyntaxException} with the specified detail message.
     *
     * @param message the detail message
     */
    public SyntaxException(String message) {
        super(message);
    }
}
