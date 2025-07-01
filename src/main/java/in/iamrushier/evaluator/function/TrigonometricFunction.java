package in.iamrushier.evaluator.function;

import java.math.BigDecimal;

public class TrigonometricFunction {
    private TrigonometricFunction() {
    }

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

    public static String handleAtan(String value) {
        if (value.equals("Infinity")) {
            return "90"; // atan(∞) = 90 degrees
        } else if (value.equals("-Infinity")) {
            return "-90"; // atan(-∞) = -90 degrees
        }
        return String.valueOf(Math.toDegrees(Math.atan(Double.parseDouble(value))));
    }
}
