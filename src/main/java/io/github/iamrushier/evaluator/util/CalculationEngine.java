package io.github.iamrushier.evaluator.util;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * {@code CalculationEngine} centralizes mathematical calculations with precision management.
 * it handles the transition between {@code BigDecimal} and {@code double} fallback.
 */
public class CalculationEngine {

    private CalculationEngine() {}

    /**
     * Performs a basic arithmetic operation.
     */
    public static String calculate(String leftStr, String rightStr, char operator) {
        try {
            BigDecimal left = new BigDecimal(leftStr);
            BigDecimal right = new BigDecimal(rightStr);
            BigDecimal result;
            switch (operator) {
                case '+': result = left.add(right); break;
                case '-': result = left.subtract(right); break;
                case '*': result = left.multiply(right); break;
                case '/': result = left.divide(right, MathContext.DECIMAL128); break;
                default: throw new ArithmeticException("Invalid expression");
            }
            return OutputFormatter.formatResult(result);
        } catch (Exception e) {
            double left = Double.parseDouble(leftStr);
            double right = Double.parseDouble(rightStr);
            double result;
            switch (operator) {
                case '+': result = left + right; break;
                case '-': result = left - right; break;
                case '*': result = left * right; break;
                case '/': result = left / right; break;
                default: throw new ArithmeticException("Invalid expression");
            }
            return formatDouble(result);
        }
    }

    /**
     * Performs a power operation.
     */
    public static String power(String baseStr, String exponentStr) {
        try {
            BigDecimal base = new BigDecimal(baseStr);
            BigDecimal exponent = new BigDecimal(exponentStr);
            
            if (base.compareTo(BigDecimal.ZERO) == 0 && exponent.compareTo(BigDecimal.ZERO) == 0) {
                throw new NumberFormatException("Undefined");
            }

            // BigDecimal.pow only supports non-negative integer exponents.
            // For negative or fractional, we try to stay in BigDecimal if possible or fallback.
            if (exponent.scale() == 0 && exponent.compareTo(BigDecimal.ZERO) >= 0) {
                return OutputFormatter.formatResult(base.pow(exponent.intValueExact(), MathContext.DECIMAL128));
            }
            
            // If it's negative integer, we can do 1 / base^abs(exponent)
            if (exponent.scale() == 0 && exponent.compareTo(BigDecimal.ZERO) < 0) {
                 return OutputFormatter.formatResult(BigDecimal.ONE.divide(base.pow(exponent.abs().intValueExact(), MathContext.DECIMAL128), MathContext.DECIMAL128));
            }
            
            throw new ArithmeticException("Fallback to double for fractional power");
        } catch (Exception e) {
            double base = Double.parseDouble(baseStr);
            double exponent = Double.parseDouble(exponentStr);
            if (base == 0 && exponent == 0) {
                throw new NumberFormatException("Undefined");
            }
            return formatDouble(Math.pow(base, exponent));
        }
    }

    private static String formatDouble(double value) {
        String result = String.valueOf(value);
        if (result.equals("NaN")) return "NaN";
        return result; // Facade handles scientific notation and Infinity -> ∞
    }
}
