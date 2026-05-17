package io.github.iamrushier.evaluator.util;

import io.github.iamrushier.evaluator.exception.DomainException;
import io.github.iamrushier.evaluator.exception.SyntaxException;
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
            double r = right.getAsDouble();
            if (r == 0) {
                double l = left.getAsDouble();
                if (l == 0) return new Operand(Double.NaN);
                return new Operand(l > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY);
            }
            return new Operand(left.getAsDouble() / r);
        });
        // Register power operator as well to unify CalculationEngine
        operators.put('^', CalculationEngine::power);
    }

    private CalculationEngine() {}

    /**
     * Performs a binary arithmetic operation.
     *
     * @param left the left operand
     * @param right the right operand
     * @param operator the operator character (e.g., '+', '-', '*', '/', '^')
     * @return the result of the calculation as an {@link Operand}
     * @throws SyntaxException if the operator is invalid
     */
    public static Operand calculate(Operand left, Operand right, char operator) {
        BinaryOperator op = operators.get(operator);
        if (op == null) {
            throw new SyntaxException("Invalid expression");
        }
        return op.apply(left, right);
    }

    /**
     * Performs a power operation.
     *
     * @param base the base operand
     * @param exponent the exponent operand
     * @return the result of base raised to the power of exponent
     * @throws DomainException if both base and exponent are zero (undefined)
     */
    public static Operand power(Operand base, Operand exponent) {
        if (base.isBigDecimal() && exponent.isBigDecimal()) {
            try {
                BigDecimal b = base.getAsBigDecimal();
                BigDecimal e = exponent.getAsBigDecimal();
                
                if (b.compareTo(BigDecimal.ZERO) == 0 && e.compareTo(BigDecimal.ZERO) == 0) {
                    throw new DomainException("Undefined");
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
            throw new DomainException("Undefined");
        }
        return new Operand(Math.pow(b, e));
    }

    /**
     * Calculates the square root of an operand.
     *
     * @param operand the operand to calculate the square root of
     * @return the square root of the operand
     * @throws DomainException if the operand is negative
     */
    public static Operand sqrt(Operand operand) {
        double val = operand.getAsDouble();
        if (val < 0) {
            throw new DomainException("Domain error");
        }
        return new Operand(Math.sqrt(val));
    }

    /**
     * Calculates the cube root of an operand.
     *
     * @param operand the operand to calculate the cube root of
     * @return the cube root of the operand
     */
    public static Operand cbrt(Operand operand) {
        return new Operand(Math.cbrt(operand.getAsDouble()));
    }
}
