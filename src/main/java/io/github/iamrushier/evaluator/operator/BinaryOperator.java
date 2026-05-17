package io.github.iamrushier.evaluator.operator;

import io.github.iamrushier.evaluator.util.Operand;

/**
 * Interface for binary operators (e.g., +, -, *, /, ^).
 */
public interface BinaryOperator {
    /**
     * Applies the binary operator to the given operands.
     *
     * @param left the left operand
     * @param right the right operand
     * @return the result of the operation
     */
    Operand apply(Operand left, Operand right);
}
