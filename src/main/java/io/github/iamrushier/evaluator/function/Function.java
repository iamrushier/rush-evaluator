package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.util.Operand;

/**
 * Interface for mathematical functions (e.g., sin, cos, log).
 */
public interface Function {
    /**
     * Applies the function to the given argument using default settings.
     *
     * @param argument the function argument
     * @return the result of the function evaluation
     */
    Operand apply(Operand argument);

    /**
     * Applies the function to the given argument using the provided configuration.
     *
     * @param argument the function argument
     * @param config the evaluation configuration
     * @return the result of the function evaluation
     */
    default Operand apply(Operand argument, EvaluationConfig config) {
        return apply(argument);
    }
}
