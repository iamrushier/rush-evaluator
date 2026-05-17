package io.github.iamrushier.evaluator.operator;

import io.github.iamrushier.evaluator.util.Operand;

/**
 * Interface for binary operators (e.g., +, -, *, /, ^).
 */
public interface BinaryOperator {
    Operand apply(Operand left, Operand right);
}
