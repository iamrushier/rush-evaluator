package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.util.Operand;
import java.util.Collections;
import java.util.List;

/**
 * Interface for mathematical functions supporting single or multiple arguments.
 */
public interface Function {

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

    /**
     * Applies the function to a list of arguments using the provided configuration.
     *
     * @param arguments the list of function arguments (cannot be null)
     * @param config the evaluation configuration
     * @return the result of the function evaluation
     */
    default Operand apply(List<Operand> arguments, EvaluationConfig config) {
        if (arguments == null || arguments.isEmpty()) {
            throw new SyntaxException("Function requires at least one argument");
        }
        return apply(arguments.get(0), config);
    }
}
