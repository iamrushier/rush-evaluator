package io.github.iamrushier.evaluator.util;

import java.math.BigDecimal;

/**
 * {@code Operand} represents a numerical value in an expression.
 * It can wrap a {@code BigDecimal} for high precision or a {@code double} as a fallback.
 */
public class Operand {
    private final BigDecimal bigDecimalValue;
    private final Double doubleValue;
    private final boolean isSpecial; // For Infinity, NaN

    public Operand(BigDecimal value) {
        this.bigDecimalValue = value;
        this.doubleValue = null;
        this.isSpecial = false;
    }

    public Operand(double value) {
        this.bigDecimalValue = null;
        this.doubleValue = value;
        this.isSpecial = Double.isInfinite(value) || Double.isNaN(value);
    }

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
            try {
                return new Operand(Double.parseDouble(value));
            } catch (NumberFormatException e2) {
                // If it's a very large number that BigDecimal can't handle as a string directly
                // though usually BigDecimal handles any size if format is correct.
                throw e2;
            }
        }
    }

    public BigDecimal getAsBigDecimal() {
        if (bigDecimalValue != null) return bigDecimalValue;
        if (doubleValue.isInfinite()) {
             // Return a very large value or throw? 
             // Better to let the operation handle it or return null.
             // For now, consistent with existing logic.
             return BigDecimal.valueOf(doubleValue);
        }
        return BigDecimal.valueOf(doubleValue);
    }

    public double getAsDouble() {
        if (doubleValue != null) return doubleValue;
        return bigDecimalValue.doubleValue();
    }

    public boolean isBigDecimal() {
        return bigDecimalValue != null;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    @Override
    public String toString() {
        if (isSpecial) {
            if (Double.isInfinite(doubleValue)) return doubleValue > 0 ? "∞" : "-∞";
            return "NaN";
        }
        
        BigDecimal bd;
        if (bigDecimalValue != null) {
            bd = bigDecimalValue;
        } else {
            // For double results, check if it should be scientific or plain
            String doubleStr = String.valueOf(doubleValue);
            if (doubleStr.contains("E") || doubleStr.contains("e")) {
                return OutputFormatter.consistentScientific(doubleStr);
            }
            bd = BigDecimal.valueOf(doubleValue);
        }
        return OutputFormatter.formatResult(bd);
    }
}
