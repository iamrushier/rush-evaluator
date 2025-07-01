package in.iamrushier.evaluator.function;

import java.math.BigDecimal;

/**
 * {@code TrigonometricFunction} provides static methods for handling various trigonometric operations.
 * It includes functions for sine, cosine, tangent, and their inverse counterparts.
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
        BigDecimal normalized = angle;
        while (normalized.doubleValue() >= 360) {
            normalized = normalized.subtract(BigDecimal.valueOf(360));
        }
        while (normalized.doubleValue() < 0) {
            normalized = normalized.add(BigDecimal.valueOf(360));
        }
        return normalized;
    }

    /**
     * Calculates the sine of an angle.
     * @param angle The angle in degrees.
     * @return The sine of the angle as a String.
     */
    public static String handleSin(BigDecimal angle) {
        angle = normalizeAngle(angle);
        if (angle.doubleValue() == 0) {
            return "0";
        } else if (angle.doubleValue() == 30) {
            return "0.5";
        } else if (angle.doubleValue() == 90) {
            return "1";
        } else if (angle.doubleValue() == 150) {
            return "0.5";
        } else if (angle.doubleValue() == 180) {
            return "0";
        } else if (angle.doubleValue() == 210) {
            return "-0.5";
        } else if (angle.doubleValue() == 270) {
            return "-1";
        } else if (angle.doubleValue() == 330) {
            return "-0.5";
        } else {
            return String.valueOf(Math.sin(Math.toRadians(angle.doubleValue())));
        }
    }

    /**
     * Calculates the cosine of an angle.
     * @param angle The angle in degrees.
     * @return The cosine of the angle as a String.
     */
    public static String handleCos(BigDecimal angle) {
        angle = normalizeAngle(angle);
        if (angle.doubleValue() == 0) {
            return "1";
        } else if (angle.doubleValue() == 60) {
            return "0.5";
        } else if (angle.doubleValue() == 90) {
            return "0";
        } else if (angle.doubleValue() == 120) {
            return "-0.5";
        } else if (angle.doubleValue() == 180) {
            return "-1";
        } else if (angle.doubleValue() == 240) {
            return "-0.5";
        } else if (angle.doubleValue() == 270) {
            return "0";
        } else if (angle.doubleValue() == 300) {
            return "0.5";
        } else {
            return String.valueOf(Math.cos(Math.toRadians(angle.doubleValue())));
        }
    }

    /**
     * Calculates the tangent of an angle.
     * @param angle The angle in degrees.
     * @return The tangent of the angle as a String.
     */
    public static String handleTan(BigDecimal angle) {
        angle = normalizeAngle(angle);
        if (angle.doubleValue() == 0) {
            return "0";
        } else if (angle.doubleValue() == 45) {
            return "1";
        } else if (angle.doubleValue() == 90) {
            return "Infinity";
        } else if (angle.doubleValue() == 135) {
            return "-1";
        } else if (angle.doubleValue() == 180) {
            return "0";
        } else if (angle.doubleValue() == 225) {
            return "1";
        } else if (angle.doubleValue() == 270) {
            return "-Infinity";
        } else if (angle.doubleValue() == 315) {
            return "-1";
        } else {
            return String.valueOf(Math.tan(Math.toRadians(angle.doubleValue())));
        }
    }

    /**
     * Calculates the arcsine (inverse sine) of a value.
     * @param value The value for which to calculate the arcsine.
     * @return The arcsine in degrees as a String.
     * @throws IllegalArgumentException if the value is outside the domain [-1, 1].
     */
    public static String handleAsin(BigDecimal value) {
        if (value.doubleValue() < -1 || value.doubleValue() > 1) {
            throw new IllegalArgumentException("Domain error");
        }
        if (value.doubleValue() == 0.5) {
            return "30"; // asin(0.5) = 30 degrees
        } else if (value.doubleValue() == 1) {
            return "90"; // asin(1) = 90 degrees
        } else if (value.doubleValue() == -0.5) {
            return "-30"; // asin(1) = 90 degrees
        } else if (value.doubleValue() == -1) {
            return "-90"; // asin(-1) = -90 degrees
        }
        return String.valueOf(Math.toDegrees(Math.asin(value.doubleValue())));
    }

    /**
     * Calculates the arccosine (inverse cosine) of a value.
     * @param value The value for which to calculate the arccosine.
     * @return The arccosine in degrees as a String.
     * @throws IllegalArgumentException if the value is outside the domain [-1, 1].
     */
    public static String handleAcos(BigDecimal value) {
        if (value.doubleValue() < -1 || value.doubleValue() > 1) {
            throw new IllegalArgumentException("Domain error");
        }
        if (value.doubleValue() == 0.5) {
            return "60"; // acos(0.5) = 60 degrees
        } else if (value.doubleValue() == 1) {
            return "0"; // acos(1) = 0 degrees
        } else if (value.doubleValue() == -0.5) {
            return "120"; // asin(1) = 90 degrees
        } else if (value.doubleValue() == -1) {
            return "180"; // acos(-1) = 180 degrees
        }
        return String.valueOf(Math.toDegrees(Math.acos(value.doubleValue())));
    }

    /**
     * Calculates the arctangent (inverse tangent) of a value.
     * @param value The value for which to calculate the arctangent.
     * @return The arctangent in degrees as a String.
     */
    public static String handleAtan(String value) {
        if (value.equals("Infinity")) {
            return "90"; // atan(∞) = 90 degrees
        } else if (value.equals("-Infinity")) {
            return "-90"; // atan(-∞) = -90 degrees
        }
        return String.valueOf(Math.toDegrees(Math.atan(Double.parseDouble(value))));
    }
}
