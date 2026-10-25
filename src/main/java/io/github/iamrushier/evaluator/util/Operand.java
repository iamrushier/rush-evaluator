package io.github.iamrushier.evaluator.util;

import io.github.iamrushier.evaluator.exception.DomainException;
import java.math.BigDecimal;

/**
 * {@code Operand} represents a numerical value in an expression.
 * It wraps either a {@link BigDecimal} for high precision or a {@code double} as a fallback.
 */
public class Operand {
    private final BigDecimal bigDecimalValue;
    private final Double doubleValue;
    private final boolean isSpecial; // For Infinity, NaN

    /**
     * Constructs an {@code Operand} with a {@link BigDecimal} value.
     *
     * @param value the BigDecimal value
     */
    public Operand(BigDecimal value) {
        this.bigDecimalValue = value;
        this.doubleValue = null;
        this.isSpecial = false;
    }

    /**
     * Constructs an {@code Operand} with a {@code double} value.
     *
     * @param value the double value
     */
    public Operand(double value) {
        this.bigDecimalValue = null;
        this.doubleValue = value;
        this.isSpecial = Double.isInfinite(value) || Double.isNaN(value);
    }

    /**
     * Creates an {@code Operand} from a string representation.
     *
     * @param value the string representation of the value
     * @return a new {@code Operand}
     * @throws NumberFormatException if the value is not a valid number
     */
    public static Operand of(String value) {
        try {
            if (value.equals("Infinity") || value.equals("∞")) {
                return new Operand(Double.POSITIVE_INFINITY);
            }
            if (value.equals("-Infinity") || value.equals("-∞")) {
                return new Operand(Double.NEGATIVE_INFINITY);
            }
            if (value.equals("NaN")) {
                return new Operand(Double.NaN);
            }
            return new Operand(new BigDecimal(value));
        } catch (NumberFormatException e) {
            // Check for scientific notation or other double formats
            return new Operand(Double.parseDouble(value));
        }
    }

    /**
     * Returns the value as a {@link BigDecimal}.
     *
     * @return the BigDecimal value
     * @throws DomainException if this operand represents a non-finite value (Infinity or NaN)
     */
    public BigDecimal getAsBigDecimal() {
        if (bigDecimalValue != null) {
            return bigDecimalValue;
        }
        if (doubleValue != null && (doubleValue.isInfinite() || doubleValue.isNaN())) {
            throw new DomainException("Cannot convert non-finite value (" + doubleValue + ") to BigDecimal");
        }
        return BigDecimal.valueOf(doubleValue);
    }

    /**
     * Returns the value as a {@code double}.
     *
     * @return the double value
     */
    public double getAsDouble() {
        if (doubleValue != null) {
            return doubleValue;
        }
        return bigDecimalValue.doubleValue();
    }

    /**
     * Checks if this operand wraps a {@link BigDecimal}.
     *
     * @return {@code true} if the value is a {@link BigDecimal}, {@code false} otherwise
     */
    public boolean isBigDecimal() {
        return bigDecimalValue != null;
    }

    /**
     * Checks if this operand represents a special value (Infinity or NaN).
     *
     * @return {@code true} if it's special, {@code false} otherwise
     */
    public boolean isSpecial() {
        return isSpecial;
    }

    @Override
    public String toString() {
        if (isSpecial) {
            if (Double.isInfinite(doubleValue)) {
                return doubleValue > 0 ? "∞" : "-∞";
            }
            return "NaN";
        }

        if (bigDecimalValue != null) {
            return OutputFormatter.formatResult(bigDecimalValue);
        }

        String doubleStr = String.valueOf(doubleValue);
        if (doubleStr.contains("E") || doubleStr.contains("e")) {
            return OutputFormatter.consistentScientific(doubleStr);
        }

        return OutputFormatter.formatDouble(doubleValue);
    }
}
