package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.util.Operand;

/**
 * Interface for mathematical functions (e.g., sin, cos, log).
 */
public interface Function {
    Operand apply(Operand argument);
}
