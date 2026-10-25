package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.exception.DomainException;
import io.github.iamrushier.evaluator.util.OutputFormatter;
import java.math.BigDecimal;

/**
 * Utility class for handling logarithmic functions.
 */
public class LogarithmicFunction {

    /**
     * Private constructor to prevent instantiation.
     */
    private LogarithmicFunction() {
    }

    /**
     * Calculates the base-10 logarithm of a number as a {@code double}.
     *
     * @param value The number to calculate the logarithm of.
     * @return The base-10 logarithm value.
     * @throws DomainException if value is less than or equal to 0.
     */
    public static double log(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Domain error");
        }
        return Math.log10(value.doubleValue());
    }

    /**
     * Calculates the natural logarithm (ln) of a number as a {@code double}.
     *
     * @param value The number to calculate the natural logarithm of.
     * @return The natural logarithm value.
     * @throws DomainException if value is less than or equal to 0.
     */
    public static double ln(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Domain error");
        }
        return Math.log(value.doubleValue());
    }

    /**
     * Calculates the base-10 logarithm of a number as a formatted string.
     *
     * @param value The number to calculate the logarithm of.
     * @return The base-10 logarithm as a string.
     * @throws DomainException if value is less than or equal to 0.
     */
    public static String handleLog(BigDecimal value) {
        return OutputFormatter.formatDouble(log(value));
    }

    /**
     * Calculates the natural logarithm (ln) of a number as a formatted string.
     *
     * @param value The number to calculate the natural logarithm of.
     * @return The natural logarithm as a string.
     * @throws DomainException if value is less than or equal to 0.
     */
    public static String handleLn(BigDecimal value) {
        return OutputFormatter.formatDouble(ln(value));
    }
}
