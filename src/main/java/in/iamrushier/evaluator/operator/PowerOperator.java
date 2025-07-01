package in.iamrushier.evaluator.operator;

import in.iamrushier.evaluator.util.OutputFormatter;

import java.math.BigDecimal;
import java.math.MathContext;

public class PowerOperator {
    private PowerOperator() {
    }

    public static String power(String baseStr, String exponentStr) {
        try {
            BigDecimal base = new BigDecimal(baseStr);
            BigDecimal exponent = new BigDecimal(exponentStr);
            if (base.compareTo(BigDecimal.ZERO) == 0 && exponent.compareTo(BigDecimal.ZERO) == 0) {
                throw new NumberFormatException("Undefined");
            }
            if (exponent.scale() > 0 || exponent.compareTo(BigDecimal.ZERO) < 0) {
                return OutputFormatter.formatResult(new BigDecimal(1).divide(base.pow(exponent.abs().intValueExact(), MathContext.DECIMAL128)));
            } else {
                return OutputFormatter.formatResult(base.pow(exponent.intValueExact(), MathContext.DECIMAL128));
            }
        } catch (Exception e) {
            double base = Double.parseDouble(baseStr);
            double exponent = Double.parseDouble(exponentStr);
            if (base == 0 && exponent == 0) {
                throw new NumberFormatException("Undefined");
            }
            return String.valueOf(Math.pow(base, exponent));
        }
    }
}
