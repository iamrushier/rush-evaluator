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
    public static Operand calculate(Operand left, Operand right, char operator) {
        if (left.isBigDecimal() && right.isBigDecimal()) {
            try {
                BigDecimal l = left.getAsBigDecimal();
                BigDecimal r = right.getAsBigDecimal();
                switch (operator) {
                    case '+': return new Operand(l.add(r));
                    case '-': return new Operand(l.subtract(r));
                    case '*': return new Operand(l.multiply(r));
                    case '/': return new Operand(l.divide(r, MathContext.DECIMAL128));
                    default: throw new ArithmeticException("Invalid expression");
                }
            } catch (Exception e) {
                // Fallback to double handled below
            }
        }

        double l = left.getAsDouble();
        double r = right.getAsDouble();
        switch (operator) {
            case '+': return new Operand(l + r);
            case '-': return new Operand(l - r);
            case '*': return new Operand(l * r);
            case '/': return new Operand(l / r);
            default: throw new ArithmeticException("Invalid expression");
        }
    }

    /**
     * Performs a power operation.
     */
    public static Operand power(Operand base, Operand exponent) {
        if (base.isBigDecimal() && exponent.isBigDecimal()) {
            try {
                BigDecimal b = base.getAsBigDecimal();
                BigDecimal e = exponent.getAsBigDecimal();
                
                if (b.compareTo(BigDecimal.ZERO) == 0 && e.compareTo(BigDecimal.ZERO) == 0) {
                    throw new NumberFormatException("Undefined");
                }

                if (e.scale() == 0 && e.compareTo(BigDecimal.ZERO) >= 0) {
                    return new Operand(b.pow(e.intValueExact(), MathContext.DECIMAL128));
                }
                
                if (e.scale() == 0 && e.compareTo(BigDecimal.ZERO) < 0) {
                     return new Operand(BigDecimal.ONE.divide(b.pow(e.abs().intValueExact(), MathContext.DECIMAL128), MathContext.DECIMAL128));
                }
            } catch (Exception e) {
                // Fallback to double handled below
            }
        }

        double b = base.getAsDouble();
        double e = exponent.getAsDouble();
        if (b == 0 && e == 0) {
            throw new NumberFormatException("Undefined");
        }
        return new Operand(Math.pow(b, e));
    }

    private static String formatDouble(double value) {
        String result = String.valueOf(value);
        if (result.equals("NaN")) return "NaN";
        return result; // Facade handles scientific notation and Infinity -> ∞
    }
}
