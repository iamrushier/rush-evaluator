package in.iamrushier.evaluator.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * {@code OutputFormatter} provides utility methods for formatting numerical results.
 * It includes methods for consistent scientific notation and general BigDecimal formatting.
 */
public class OutputFormatter {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private OutputFormatter() {
    }

    /**
     * Formats a number string into a consistent scientific notation.
     * If the number contains 'E' (indicating scientific notation), it adjusts the base
     * to have a single digit before the decimal point and updates the exponent accordingly.
     * It also ensures the exponent has a '+' sign if positive.
     *
     * @param number The number string to format.
     * @return The formatted number string in consistent scientific notation.
     */
    public static String consistentScientific(String number) {
        if (number.contains("E")) {
            String[] parts = number.split("E");
            BigDecimal baseBD = new BigDecimal(parts[0]);
            // Set scale for consistent precision in the base part
            baseBD = baseBD.setScale(6, RoundingMode.HALF_UP);
            int exponent = Integer.parseInt(parts[1]);
            String base = String.valueOf(baseBD.doubleValue());
            String[] part = base.split("\\.");
            int lenThreshold = 1;
            // Adjust threshold for negative numbers
            if (parts[0].charAt(0) == '-') {
                lenThreshold = 2;
            }
            // Shift decimal point to have one digit before it
            while (part[0].length() > lenThreshold) {
                part[1] = part[0].substring(part[0].length() - 1) + part[1];
                part[0] = part[0].substring(0, part[0].length() - 1);
                exponent++;
            }
            base = part[0] + "." + part[1];
            number = Double.parseDouble(base) + "E" + exponent;
        }
        // Ensure positive exponents have a '+' sign
        return number.replaceAll("E(\\d)", "E+$1");
    }

    /**
     * Formats a BigDecimal result into a plain string representation.
     * If the BigDecimal has no decimal places (after stripping trailing zeros),
     * it returns the integer part. Otherwise, it returns the full plain string.
     *
     * @param result The BigDecimal result to format.
     * @return The formatted result as a String.
     */
    public static String formatResult(BigDecimal result) {
        if (result.stripTrailingZeros().scale() <= 0) {
            return result.toBigInteger().toString();
        } else {
            return result.toPlainString();
        }
    }
}
