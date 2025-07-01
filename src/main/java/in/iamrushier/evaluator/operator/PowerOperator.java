package in.iamrushier.evaluator.operator;

import in.iamrushier.evaluator.util.OutputFormatter;

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
        try {
            BigDecimal base = new BigDecimal(baseStr);
            BigDecimal exponent = new BigDecimal(exponentStr);
            if (base.compareTo(BigDecimal.ZERO) == 0 && exponent.compareTo(BigDecimal.ZERO) == 0) {
                throw new NumberFormatException("Undefined");
            }
            // Handle negative and fractional exponents for BigDecimal
            if (exponent.scale() > 0 || exponent.compareTo(BigDecimal.ZERO) < 0) {
                return OutputFormatter.formatResult(new BigDecimal(1).divide(base.pow(exponent.abs().intValueExact(), MathContext.DECIMAL128)));
            } else {
                return OutputFormatter.formatResult(base.pow(exponent.intValueExact(), MathContext.DECIMAL128));
            }
        } catch (Exception e) {
            // Fallback to double for operations that might exceed BigDecimal's practical limits or for non-exact results
            double base = Double.parseDouble(baseStr);
            double exponent = Double.parseDouble(exponentStr);
            if (base == 0 && exponent == 0) {
                throw new NumberFormatException("Undefined");
            }
            return String.valueOf(Math.pow(base, exponent));
        }
    }
}
