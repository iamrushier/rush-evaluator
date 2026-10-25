package io.github.iamrushier.evaluator.util;

import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.function.Function;
import io.github.iamrushier.evaluator.function.LogarithmicFunction;
import io.github.iamrushier.evaluator.function.TrigonometricFunction;
import io.github.iamrushier.evaluator.function.UtilityFunction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code OperationRegistry} manages the registration and retrieval of mathematical functions.
 */
public class OperationRegistry {
    private static final Map<String, Function> functions = new HashMap<>();

    static {
        // Trigonometric
        registerFunction("sin", (args, config) -> {
            checkUnaryArg("sin", args);
            return new Operand(TrigonometricFunction.sin(args.get(0).getAsBigDecimal(), getMode(config)));
        });
        registerFunction("cos", (args, config) -> {
            checkUnaryArg("cos", args);
            return new Operand(TrigonometricFunction.cos(args.get(0).getAsBigDecimal(), getMode(config)));
        });
        registerFunction("tan", (args, config) -> {
            checkUnaryArg("tan", args);
            return new Operand(TrigonometricFunction.tan(args.get(0).getAsBigDecimal(), getMode(config)));
        });
        registerFunction("asin", (args, config) -> {
            checkUnaryArg("asin", args);
            return new Operand(TrigonometricFunction.asin(args.get(0).getAsBigDecimal(), getMode(config)));
        });
        registerFunction("acos", (args, config) -> {
            checkUnaryArg("acos", args);
            return new Operand(TrigonometricFunction.acos(args.get(0).getAsBigDecimal(), getMode(config)));
        });
        registerFunction("atan", (args, config) -> {
            checkUnaryArg("atan", args);
            return new Operand(TrigonometricFunction.atan(args.get(0).getAsDouble(), getMode(config)));
        });

        // Logarithmic
        registerFunction("log", (args, config) -> {
            checkUnaryArg("log", args);
            return new Operand(LogarithmicFunction.log(args.get(0).getAsBigDecimal()));
        });
        registerFunction("ln", (args, config) -> {
            checkUnaryArg("ln", args);
            return new Operand(LogarithmicFunction.ln(args.get(0).getAsBigDecimal()));
        });

        // Utility & Multi-argument
        registerFunction("abs", UtilityFunction.ABS);
        registerFunction("floor", UtilityFunction.FLOOR);
        registerFunction("ceil", UtilityFunction.CEIL);
        registerFunction("round", UtilityFunction.ROUND);
        registerFunction("min", UtilityFunction.MIN);
        registerFunction("max", UtilityFunction.MAX);
    }

    private static void checkUnaryArg(String funcName, List<Operand> args) {
        if (args == null || args.isEmpty()) {
            throw new SyntaxException(funcName + " requires 1 argument");
        }
    }

    private static AngleMode getMode(EvaluationConfig config) {
        return config != null ? config.getAngleMode() : AngleMode.DEGREES;
    }

    private static void registerFunction(String name, Function func) {
        functions.put(name.toLowerCase(), func);
    }

    private OperationRegistry() {}

    /**
     * Checks if a function name is registered in the registry.
     *
     * @param name The name of the function.
     * @return {@code true} if registered, {@code false} otherwise.
     */
    public static boolean isFunction(String name) {
        return name != null && functions.containsKey(name.toLowerCase());
    }

    /**
     * Retrieves a function by name.
     * @param name The name of the function.
     * @return The Function, or null if not found.
     */
    public static Function getFunction(String name) {
        if (name == null) return null;
        return functions.get(name.toLowerCase());
    }
}
