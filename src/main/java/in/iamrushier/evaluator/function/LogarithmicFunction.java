package in.iamrushier.evaluator.function;

import java.math.BigDecimal;

/**
 * Utility class for handling logarithmic functions.
 */
public class LogarithmicFunction {
    /**
     * Private constructor to prevent instantiation.
     */
    private LogarithmicFunction(){}

    /**
     * Calculates the base-10 logarithm of a given value.
     * @param value The value for which to calculate the logarithm.
     * @return The base-10 logarithm as a String.
     * @throws IllegalArgumentException if the value is less than or equal to 0.
     */
    public static String handleLog(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Domain error");
        }
        return String.valueOf(Math.log10(value.doubleValue()));
    }

    /**
     * Calculates the natural logarithm (base e) of a given value.
     * @param value The value for which to calculate the natural logarithm.
     * @return The natural logarithm as a String.
     * @throws IllegalArgumentException if the value is less than or equal to 0.
     */
    public static String handleLn(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Domain error");
        }
        return String.valueOf(Math.log(value.doubleValue()));
    }
}
