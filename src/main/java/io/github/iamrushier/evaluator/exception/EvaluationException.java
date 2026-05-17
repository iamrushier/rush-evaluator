package io.github.iamrushier.evaluator.exception;

/**
 * Base class for all exceptions thrown by the RushEvaluator library.
 */
public class EvaluationException extends RuntimeException {
    /**
     * Constructs an {@code EvaluationException} with the specified detail message.
     *
     * @param message the detail message
     */
    public EvaluationException(String message) {
        super(message);
    }
}
