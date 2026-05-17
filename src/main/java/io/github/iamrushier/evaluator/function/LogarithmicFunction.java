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
    private LogarithmicFunction(){}
    /**
     * Calculates the base-10 logarithm of a number.
     * @param value The number to calculate the logarithm of.
     * @return The base-10 logarithm as a string.
     */
    public static String handleLog(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Domain error");
        }
        return OutputFormatter.formatDouble(Math.log10(value.doubleValue()));
    }

    /**
     * Calculates the natural logarithm (ln) of a number.
     * @param value The number to calculate the natural logarithm of.
     * @return The natural logarithm as a string.
     */
    public static String handleLn(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Domain error");
        }
        return OutputFormatter.formatDouble(Math.log(value.doubleValue()));
    }
}
