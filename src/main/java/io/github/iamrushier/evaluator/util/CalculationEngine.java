package io.github.iamrushier.evaluator.util;

import io.github.iamrushier.evaluator.operator.BinaryOperator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code CalculationEngine} centralizes mathematical calculations with precision management.
 * it handles the transition between {@code BigDecimal} and {@code double} fallback.
 */
public class CalculationEngine {

    private static final Map<Character, BinaryOperator> operators = new HashMap<>();

    static {
        operators.put('+', (left, right) -> {
            if (left.isBigDecimal() && right.isBigDecimal()) {
                return new Operand(left.getAsBigDecimal().add(right.getAsBigDecimal()));
            }
            return new Operand(left.getAsDouble() + right.getAsDouble());
        });
        operators.put('-', (left, right) -> {
            if (left.isBigDecimal() && right.isBigDecimal()) {
                return new Operand(left.getAsBigDecimal().subtract(right.getAsBigDecimal()));
            }
            return new Operand(left.getAsDouble() - right.getAsDouble());
        });
        operators.put('*', (left, right) -> {
            if (left.isBigDecimal() && right.isBigDecimal()) {
                return new Operand(left.getAsBigDecimal().multiply(right.getAsBigDecimal()));
            }
            return new Operand(left.getAsDouble() * right.getAsDouble());
        });
        operators.put('/', (left, right) -> {
            if (left.isBigDecimal() && right.isBigDecimal()) {
                try {
                    return new Operand(left.getAsBigDecimal().divide(right.getAsBigDecimal(), MathContext.DECIMAL128));
                } catch (ArithmeticException e) {
                    // Fallback to double handled below
                }
            }
            return new Operand(left.getAsDouble() / right.getAsDouble());
        });
        // Register power operator as well to unify CalculationEngine
        operators.put('^', CalculationEngine::power);
    }

    private CalculationEngine() {}

    /**
     * Performs a binary arithmetic operation.
     */
    public static Operand calculate(Operand left, Operand right, char operator) {
        BinaryOperator op = operators.get(operator);
        if (op == null) {
            throw new ArithmeticException("Invalid expression");
        }
        return op.apply(left, right);
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
}
