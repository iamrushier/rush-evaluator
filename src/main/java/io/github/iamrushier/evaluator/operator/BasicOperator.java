package io.github.iamrushier.evaluator.operator;

import io.github.iamrushier.evaluator.util.OutputFormatter;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * {@code BasicOperator} provides static methods for performing basic arithmetic operations.
 * It handles addition, subtraction, multiplication, and division.
 */
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
        try {
            BigDecimal left = new BigDecimal(leftStr);
            BigDecimal right = new BigDecimal(rightStr);
            switch (operator) {
                case '+':
                    return OutputFormatter.formatResult(left.add(right));
                case '-':
                    return OutputFormatter.formatResult(left.subtract(right));
                case '*':
                    return OutputFormatter.formatResult(left.multiply(right));
                case '/':
                    return OutputFormatter.formatResult(left.divide(right, MathContext.DECIMAL128));
                default:
                    throw new ArithmeticException("Invalid expression");
            }
        } catch (Exception e) {
            double left = Double.parseDouble(leftStr);
            double right = Double.parseDouble(rightStr);
            switch (operator) {
                case '+':
                    return String.valueOf(left + right);
                case '-':
                    return String.valueOf(left - right);
                case '*':
                    return String.valueOf(left * right);
                case '/':
                    return String.valueOf(left / right);
                default:
                    throw new ArithmeticException("Invalid expression");
            }
        }

    }
}
