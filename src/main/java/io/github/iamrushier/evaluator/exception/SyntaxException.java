package io.github.iamrushier.evaluator.exception;

/**
 * Thrown when a mathematical expression has invalid syntax.
 */
public class SyntaxException extends EvaluationException {
    public SyntaxException(String message) {
        super(message);
    }
}
