package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.util.Operand;

/**
 * Interface for mathematical functions (e.g., sin, cos, log).
 */
public interface Function {
    /**
     * Applies the function to the given argument.
     *
     * @param argument the function argument
     * @return the result of the function evaluation
     */
    Operand apply(Operand argument);
}
