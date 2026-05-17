package io.github.iamrushier.evaluator.exception;

/**
 * Base class for all exceptions thrown by the RushEvaluator library.
 */
public class EvaluationException extends RuntimeException {
    public EvaluationException(String message) {
        super(message);
    }
}
