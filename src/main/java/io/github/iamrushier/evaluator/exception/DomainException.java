package io.github.iamrushier.evaluator.exception;

/**
 * Thrown when a mathematical operation is undefined or out of domain.
 */
public class DomainException extends EvaluationException {
    public DomainException(String message) {
        super(message);
    }
}
