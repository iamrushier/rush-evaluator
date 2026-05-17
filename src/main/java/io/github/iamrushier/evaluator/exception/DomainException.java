package io.github.iamrushier.evaluator.exception;

/**
 * Thrown when a mathematical operation is undefined or out of domain.
 */
public class DomainException extends EvaluationException {
    /**
     * Constructs a {@code DomainException} with the specified detail message.
     *
     * @param message the detail message
     */
    public DomainException(String message) {
        super(message);
    }
}
