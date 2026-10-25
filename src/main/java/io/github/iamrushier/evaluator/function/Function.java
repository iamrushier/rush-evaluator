package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.util.Operand;
import java.util.Collections;
import java.util.List;

/**
 * Functional interface for mathematical functions supporting single or multiple arguments.
 */
@FunctionalInterface
public interface Function {

    /**
     * Applies the function to a list of arguments using the provided configuration.
     * This is the single abstract method (SAM) of the interface.
     *
     * @param arguments the list of function arguments (cannot be null)
     * @param config the evaluation configuration
     * @return the result of the function evaluation
     */
    Operand apply(List<Operand> arguments, EvaluationConfig config);

    /**
     * Applies the function to a single argument using default settings.
     *
     * @param argument the function argument
     * @return the result of the function evaluation
     */
    default Operand apply(Operand argument) {
        return apply(Collections.singletonList(argument), EvaluationConfig.defaultConfig());
    }

    /**
     * Applies the function to a single argument using the provided configuration.
     *
     * @param argument the function argument
     * @param config the evaluation configuration
     * @return the result of the function evaluation
     */
    default Operand apply(Operand argument, EvaluationConfig config) {
        return apply(Collections.singletonList(argument), config);
    }
}
