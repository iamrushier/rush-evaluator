package io.github.iamrushier.evaluator.operator;

import io.github.iamrushier.evaluator.util.CalculationEngine;
import io.github.iamrushier.evaluator.util.Operand;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * {@code PowerOperator} provides a static method for calculating the power of a number.
 * It handles both positive and negative integer exponents, and attempts to use {@code BigDecimal}
 * for precision.
 */
public class PowerOperator {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private PowerOperator() {
    }

    /**
     * Calculates the result of a base raised to an exponent.
     * It attempts to use {@code BigDecimal} for precision, falling back to {@code double} if necessary.
     *
     * @param baseStr The base as a String.
     * @param exponentStr The exponent as a String.
     * @return The result of the power operation as a String.
     * @throws NumberFormatException if both base and exponent are zero (undefined).
     */
    public static String power(String baseStr, String exponentStr) {
        Operand result = CalculationEngine.power(Operand.of(baseStr), Operand.of(exponentStr));
        return result.toString();
    }
}
