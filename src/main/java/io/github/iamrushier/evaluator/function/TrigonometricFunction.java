package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.exception.DomainException;
import io.github.iamrushier.evaluator.util.OutputFormatter;
import java.math.BigDecimal;

/**
 * {@code TrigonometricFunction} provides static methods for handling various trigonometric operations.
 * It includes functions for sine, cosine, tangent, and their inverses.
 */
public class TrigonometricFunction {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TrigonometricFunction() {
    }

    /**
     * Normalizes an angle to be within the range [0, 360) degrees.
     * @param angle The angle to normalize.
     * @return The normalized angle.
     */
    private static BigDecimal normalizeAngle(BigDecimal angle) {
        BigDecimal circle = BigDecimal.valueOf(360);
        BigDecimal normalized = angle.remainder(circle);
        if (normalized.compareTo(BigDecimal.ZERO) < 0) {
            normalized = normalized.add(circle);
        }
        return normalized;
    }

    /**
     * Calculates the sine of an angle.
     * @param angle The angle in degrees.
     * @return The sine value as a string.
     */
    public static String handleSin(BigDecimal angle) {
        BigDecimal normalizedAngle = normalizeAngle(angle);
        double val = normalizedAngle.doubleValue();
        if (val == 0 || val == 180) return "0";
        if (val == 90) return "1";
        if (val == 270) return "-1";
        if (val == 30 || val == 150) return "0.5";
        if (val == 210 || val == 330) return "-0.5";
        
        return OutputFormatter.formatDouble(Math.sin(Math.toRadians(val)));
    }

    /**
     * Calculates the cosine of an angle.
     * @param angle The angle in degrees.
     * @return The cosine value as a string.
     */
    public static String handleCos(BigDecimal angle) {
        BigDecimal normalizedAngle = normalizeAngle(angle);
        double val = normalizedAngle.doubleValue();
        if (val == 90 || val == 270) return "0";
        if (val == 0) return "1";
        if (val == 180) return "-1";
        if (val == 60 || val == 300) return "0.5";
        if (val == 120 || val == 240) return "-0.5";
        
        return OutputFormatter.formatDouble(Math.cos(Math.toRadians(val)));
    }

    /**
     * Calculates the tangent of an angle.
     * @param angle The angle in degrees.
     * @return The tangent value as a string.
     */
    public static String handleTan(BigDecimal angle) {
        BigDecimal normalizedAngle = normalizeAngle(angle);
        double val = normalizedAngle.doubleValue();
        if (val == 90 || val == 270) return "Infinity";
        if (val == 0 || val == 180) return "0";
        if (val == 45 || val == 225) return "1";
        if (val == 135 || val == 315) return "-1";
        
        return OutputFormatter.formatDouble(Math.tan(Math.toRadians(val)));
    }

    /**
     * Calculates the inverse sine (arcsine) of a value.
     * @param value The value to calculate the arcsine of.
     * @return The arcsine value in degrees as a string.
     */
    public static String handleAsin(BigDecimal value) {
        double val = value.doubleValue();
        if (val < -1 || val > 1) {
            throw new DomainException("Domain error");
        }
        if (val == 0) return "0";
        if (val == 1) return "90";
        if (val == -1) return "-90";
        if (val == 0.5) return "30";
        if (val == -0.5) return "-30";
        
        return OutputFormatter.formatDouble(Math.toDegrees(Math.asin(val)));
    }

    /**
     * Calculates the inverse cosine (arccosine) of a value.
     * @param value The value to calculate the arccosine of.
     * @return The arccosine value in degrees as a string.
     */
    public static String handleAcos(BigDecimal value) {
        double val = value.doubleValue();
        if (val < -1 || val > 1) {
            throw new DomainException("Domain error");
        }
        if (val == 0) return "90";
        if (val == 1) return "0";
        if (val == -1) return "180";
        if (val == 0.5) return "60";
        if (val == -0.5) return "120";
        
        return OutputFormatter.formatDouble(Math.toDegrees(Math.acos(val)));
    }

    /**
     * Calculates the inverse tangent (arctangent) of a value.
     * @param value The value to calculate the arctangent of.
     * @return The arctangent value in degrees as a string.
     */
    public static String handleAtan(String value) {
        if (value.equals("Infinity") || value.equals("∞")) {
            return "90"; // atan(∞) = 90 degrees
        } else if (value.equals("-Infinity") || value.equals("-∞")) {
            return "-90"; // atan(-∞) = -90 degrees
        }
        double val = Double.parseDouble(value);
        if (val == 0) return "0";
        if (val == 1) return "45";
        if (val == -1) return "-45";
        
        return OutputFormatter.formatDouble(Math.toDegrees(Math.atan(val)));
    }
}
