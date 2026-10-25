package io.github.iamrushier.evaluator.operator;

import io.github.iamrushier.evaluator.util.CalculationEngine;
import io.github.iamrushier.evaluator.util.Operand;

/**
 * {@code BasicOperator} provides static methods for performing basic arithmetic operations.
 * It handles addition, subtraction, multiplication, and division.
 *
 * @deprecated As of release 2.1.0, this class is deprecated in favor of internal {@link CalculationEngine}
 *             evaluation and will be removed in a future major release.
 */
@Deprecated
public class BasicOperator {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private BasicOperator() {
    }

    /**
     * Evaluates a basic arithmetic operation between two numbers.
     * It attempts to use {@code BigDecimal} for precision, falling back to {@code double} if necessary.
     *
     * @param leftStr The left operand as a String.
     * @param rightStr The right operand as a String.
     * @param operator The arithmetic operator (+, -, *, /).
     * @return The result of the operation as a String.
     * @throws ArithmeticException if an invalid operator is provided or a division by zero occurs.
     */
    public static String evaluateOperation(String leftStr, String rightStr, char operator) {
        Operand result = CalculationEngine.calculate(Operand.of(leftStr), Operand.of(rightStr), operator);
        return result.toString();
    }
}
