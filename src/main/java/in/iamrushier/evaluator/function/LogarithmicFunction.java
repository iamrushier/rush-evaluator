package in.iamrushier.evaluator.function;

import java.math.BigDecimal;

public class LogarithmicFunction {
    public static String handleLog(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Domain error");
        }
        return String.valueOf(Math.log10(value.doubleValue()));
    }

    public static String handleLn(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Domain error");
        }
        return String.valueOf(Math.log(value.doubleValue()));
    }
}
