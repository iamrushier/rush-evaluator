package in.iamrushier.evaluator.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OutputFormatter {
    private OutputFormatter() {
    }

    public static String consistentScientific(String number) {
        if (number.contains("E")) {
            String[] parts = number.split("E");
            BigDecimal baseBD = new BigDecimal(parts[0]);
            baseBD = baseBD.setScale(6, RoundingMode.HALF_UP);
            int exponent = Integer.parseInt(parts[1]);
            String base = String.valueOf(baseBD.doubleValue());
            String[] part = base.split("\\.");
            int lenThreshold = 1;
            if (parts[0].charAt(0) == '-') {
                lenThreshold = 2;
            }
            while (part[0].length() > lenThreshold) {
                part[1] = part[0].substring(part[0].length() - 1) + part[1];
                part[0] = part[0].substring(0, part[0].length() - 1);
                exponent++;
            }
            base = part[0] + "." + part[1];
            number = Double.parseDouble(base) + "E" + exponent;
        }
        return number.replaceAll("E(\\d)", "E+$1");
    }

    public static String formatResult(BigDecimal result) {
        if (result.stripTrailingZeros().scale() <= 0) {
            return result.toBigInteger().toString();
        } else {
            return result.toPlainString();
        }
    }
}
