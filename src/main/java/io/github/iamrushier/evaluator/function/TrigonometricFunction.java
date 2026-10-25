package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.exception.DomainException;
import io.github.iamrushier.evaluator.util.OutputFormatter;
import java.math.BigDecimal;

/**
 * {@code TrigonometricFunction} provides static methods for handling various trigonometric operations.
 * It includes functions for sine, cosine, tangent, and their inverses, supporting both degrees and radians
 * with direct numeric and formatted string outputs.
 */
public class TrigonometricFunction {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TrigonometricFunction() {
    }

    /**
     * Normalizes an angle in degrees to be within the range [0, 360) degrees.
     * @param angle The angle to normalize in degrees.
     * @return The normalized angle in degrees.
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
     * Calculates the sine of an angle as a {@code double}.
     *
     * @param angle The angle.
     * @param mode The angle mode (DEGREES or RADIANS).
     * @return The sine value as a double.
     */
    public static double sin(BigDecimal angle, AngleMode mode) {
        if (mode == AngleMode.RADIANS) {
            return Math.sin(angle.doubleValue());
        }

        BigDecimal normalizedAngle = normalizeAngle(angle);
        double val = normalizedAngle.doubleValue();
        if (val == 0 || val == 180) return 0.0;
        if (val == 90) return 1.0;
        if (val == 270) return -1.0;
        if (val == 30 || val == 150) return 0.5;
        if (val == 210 || val == 330) return -0.5;

        return Math.sin(Math.toRadians(val));
    }

    /**
     * Calculates the cosine of an angle as a {@code double}.
     *
     * @param angle The angle.
     * @param mode The angle mode (DEGREES or RADIANS).
     * @return The cosine value as a double.
     */
    public static double cos(BigDecimal angle, AngleMode mode) {
        if (mode == AngleMode.RADIANS) {
            return Math.cos(angle.doubleValue());
        }

        BigDecimal normalizedAngle = normalizeAngle(angle);
        double val = normalizedAngle.doubleValue();
        if (val == 90 || val == 270) return 0.0;
        if (val == 0) return 1.0;
        if (val == 180) return -1.0;
        if (val == 60 || val == 300) return 0.5;
        if (val == 120 || val == 240) return -0.5;

        return Math.cos(Math.toRadians(val));
    }

    /**
     * Calculates the tangent of an angle as a {@code double}.
     *
     * @param angle The angle.
     * @param mode The angle mode (DEGREES or RADIANS).
     * @return The tangent value as a double.
     */
    public static double tan(BigDecimal angle, AngleMode mode) {
        if (mode == AngleMode.RADIANS) {
            return Math.tan(angle.doubleValue());
        }

        BigDecimal normalizedAngle = normalizeAngle(angle);
        double val = normalizedAngle.doubleValue();
        if (val == 90 || val == 270) return Double.POSITIVE_INFINITY;
        if (val == 0 || val == 180) return 0.0;
        if (val == 45 || val == 225) return 1.0;
        if (val == 135 || val == 315) return -1.0;

        return Math.tan(Math.toRadians(val));
    }

    /**
     * Calculates the inverse sine (arcsine) of a value as a {@code double}.
     *
     * @param value The value to calculate the arcsine of.
     * @param mode The angle mode.
     * @return The arcsine value.
     * @throws DomainException if value is not in [-1, 1].
     */
    public static double asin(BigDecimal value, AngleMode mode) {
        double val = value.doubleValue();
        if (val < -1 || val > 1) {
            throw new DomainException("Domain error");
        }
        if (mode == AngleMode.RADIANS) {
            return Math.asin(val);
        }

        if (val == 0) return 0.0;
        if (val == 1) return 90.0;
        if (val == -1) return -90.0;
        if (val == 0.5) return 30.0;
        if (val == -0.5) return -30.0;

        return Math.toDegrees(Math.asin(val));
    }

    /**
     * Calculates the inverse cosine (arccosine) of a value as a {@code double}.
     *
     * @param value The value to calculate the arccosine of.
     * @param mode The angle mode.
     * @return The arccosine value.
     * @throws DomainException if value is not in [-1, 1].
     */
    public static double acos(BigDecimal value, AngleMode mode) {
        double val = value.doubleValue();
        if (val < -1 || val > 1) {
            throw new DomainException("Domain error");
        }
        if (mode == AngleMode.RADIANS) {
            return Math.acos(val);
        }

        if (val == 0) return 90.0;
        if (val == 1) return 0.0;
        if (val == -1) return 180.0;
        if (val == 0.5) return 60.0;
        if (val == -0.5) return 120.0;

        return Math.toDegrees(Math.acos(val));
    }

    /**
     * Calculates the inverse tangent (arctangent) of a value as a {@code double}.
     *
     * @param value The value to calculate the arctangent of.
     * @param mode The angle mode.
     * @return The arctangent value.
     */
    public static double atan(double value, AngleMode mode) {
        if (mode == AngleMode.RADIANS) {
            return Math.atan(value);
        }

        if (value == Double.POSITIVE_INFINITY) return 90.0;
        if (value == Double.NEGATIVE_INFINITY) return -90.0;
        if (value == 0) return 0.0;
        if (value == 1) return 45.0;
        if (value == -1) return -45.0;

        return Math.toDegrees(Math.atan(value));
    }

    // ================= String-based backward compatible methods =================

    /**
     * Calculates the sine of an angle in degrees as a formatted string.
     * @param angle The angle in degrees.
     * @return The sine value as a string.
     */
    public static String handleSin(BigDecimal angle) {
        return handleSin(angle, AngleMode.DEGREES);
    }

    /**
     * Calculates the sine of an angle with the specified {@link AngleMode} as a formatted string.
     * @param angle The angle.
     * @param mode The angle mode (DEGREES or RADIANS).
     * @return The sine value as a string.
     */
    public static String handleSin(BigDecimal angle, AngleMode mode) {
        return OutputFormatter.formatDouble(sin(angle, mode));
    }

    /**
     * Calculates the cosine of an angle in degrees as a formatted string.
     * @param angle The angle in degrees.
     * @return The cosine value as a string.
     */
    public static String handleCos(BigDecimal angle) {
        return handleCos(angle, AngleMode.DEGREES);
    }

    /**
     * Calculates the cosine of an angle with the specified {@link AngleMode} as a formatted string.
     * @param angle The angle.
     * @param mode The angle mode (DEGREES or RADIANS).
     * @return The cosine value as a string.
     */
    public static String handleCos(BigDecimal angle, AngleMode mode) {
        return OutputFormatter.formatDouble(cos(angle, mode));
    }

    /**
     * Calculates the tangent of an angle in degrees as a formatted string.
     * @param angle The angle in degrees.
     * @return The tangent value as a string.
     */
    public static String handleTan(BigDecimal angle) {
        return handleTan(angle, AngleMode.DEGREES);
    }

    /**
     * Calculates the tangent of an angle with the specified {@link AngleMode} as a formatted string.
     * @param angle The angle.
     * @param mode The angle mode (DEGREES or RADIANS).
     * @return The tangent value as a string.
     */
    public static String handleTan(BigDecimal angle, AngleMode mode) {
        return OutputFormatter.formatDouble(tan(angle, mode));
    }

    /**
     * Calculates the inverse sine (arcsine) of a value in degrees as a formatted string.
     * @param value The value to calculate the arcsine of.
     * @return The arcsine value in degrees as a string.
     */
    public static String handleAsin(BigDecimal value) {
        return handleAsin(value, AngleMode.DEGREES);
    }

    /**
     * Calculates the inverse sine (arcsine) of a value with the specified {@link AngleMode} as a formatted string.
     * @param value The value to calculate the arcsine of.
     * @param mode The angle mode.
     * @return The arcsine value as a string.
     */
    public static String handleAsin(BigDecimal value, AngleMode mode) {
        return OutputFormatter.formatDouble(asin(value, mode));
    }

    /**
     * Calculates the inverse cosine (arccosine) of a value in degrees as a formatted string.
     * @param value The value to calculate the arccosine of.
     * @return The arccosine value in degrees as a string.
     */
    public static String handleAcos(BigDecimal value) {
        return handleAcos(value, AngleMode.DEGREES);
    }

    /**
     * Calculates the inverse cosine (arccosine) of a value with the specified {@link AngleMode} as a formatted string.
     * @param value The value to calculate the arccosine of.
     * @param mode The angle mode.
     * @return The arccosine value as a string.
     */
    public static String handleAcos(BigDecimal value, AngleMode mode) {
        return OutputFormatter.formatDouble(acos(value, mode));
    }

    /**
     * Calculates the inverse tangent (arctangent) of a value in degrees as a formatted string.
     * @param value The value to calculate the arctangent of.
     * @return The arctangent value in degrees as a string.
     */
    public static String handleAtan(String value) {
        return handleAtan(value, AngleMode.DEGREES);
    }

    /**
     * Calculates the inverse tangent (arctangent) of a value with the specified {@link AngleMode} as a formatted string.
     * @param value The value to calculate the arctangent of.
     * @param mode The angle mode.
     * @return The arctangent value as a string.
     */
    public static String handleAtan(String value, AngleMode mode) {
        double dVal;
        if (value.equals("Infinity") || value.equals("∞")) {
            dVal = Double.POSITIVE_INFINITY;
        } else if (value.equals("-Infinity") || value.equals("-∞")) {
            dVal = Double.NEGATIVE_INFINITY;
        } else {
            dVal = Double.parseDouble(value);
        }
        return OutputFormatter.formatDouble(atan(dVal, mode));
    }
}
