package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.util.Operand;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * {@code UtilityFunction} provides implementations of common mathematical utility functions:
 * {@code abs}, {@code floor}, {@code ceil}, {@code round}, {@code min}, and {@code max}.
 */
public class UtilityFunction {

    private UtilityFunction() {
    }

    /**
     * Absolute value function: {@code abs(x)}.
     */
    public static final Function ABS = (args, config) -> {
        if (args.isEmpty()) {
            throw new SyntaxException("abs requires 1 argument");
        }
        Operand val = args.get(0);
        if (val.isSpecial()) {
            return val.getAsDouble() < 0 ? new Operand(-val.getAsDouble()) : val;
        }
        if (val.isBigDecimal()) {
            return new Operand(val.getAsBigDecimal().abs());
        }
        return new Operand(Math.abs(val.getAsDouble()));
    };

    /**
     * Floor function: {@code floor(x)}.
     */
    public static final Function FLOOR = (args, config) -> {
        if (args.isEmpty()) {
            throw new SyntaxException("floor requires 1 argument");
        }
        Operand val = args.get(0);
        if (val.isSpecial()) {
            return val;
        }
        double d = Math.floor(val.getAsDouble());
        return new Operand(d);
    };

    /**
     * Ceiling function: {@code ceil(x)}.
     */
    public static final Function CEIL = (args, config) -> {
        if (args.isEmpty()) {
            throw new SyntaxException("ceil requires 1 argument");
        }
        Operand val = args.get(0);
        if (val.isSpecial()) {
            return val;
        }
        double d = Math.ceil(val.getAsDouble());
        return new Operand(d);
    };

    /**
     * Round function: {@code round(x)} or {@code round(x, decimals)}.
     */
    public static final Function ROUND = (args, config) -> {
        if (args.isEmpty()) {
            throw new SyntaxException("round requires at least 1 argument");
        }
        Operand val = args.get(0);
        if (val.isSpecial()) {
            return val;
        }
        int scale = 0;
        if (args.size() > 1) {
            scale = (int) args.get(1).getAsDouble();
        }

        if (val.isBigDecimal()) {
            return new Operand(val.getAsBigDecimal().setScale(scale, RoundingMode.HALF_UP));
        } else {
            return new Operand(BigDecimal.valueOf(val.getAsDouble()).setScale(scale, RoundingMode.HALF_UP).doubleValue());
        }
    };

    /**
     * Minimum function: {@code min(a, b, ...)}. Requires at least 2 arguments.
     */
    public static final Function MIN = (args, config) -> {
        if (args.size() < 2) {
            throw new SyntaxException("min requires at least 2 arguments");
        }
        Operand min = args.get(0);
        for (int i = 1; i < args.size(); i++) {
            Operand cur = args.get(i);
            if (cur.getAsDouble() < min.getAsDouble()) {
                min = cur;
            }
        }
        return min;
    };

    /**
     * Maximum function: {@code max(a, b, ...)}. Requires at least 2 arguments.
     */
    public static final Function MAX = (args, config) -> {
        if (args.size() < 2) {
            throw new SyntaxException("max requires at least 2 arguments");
        }
        Operand max = args.get(0);
        for (int i = 1; i < args.size(); i++) {
            Operand cur = args.get(i);
            if (cur.getAsDouble() > max.getAsDouble()) {
                max = cur;
            }
        }
        return max;
    };
}
