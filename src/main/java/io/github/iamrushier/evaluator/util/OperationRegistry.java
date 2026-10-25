package io.github.iamrushier.evaluator.util;

import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.function.Function;
import io.github.iamrushier.evaluator.function.LogarithmicFunction;
import io.github.iamrushier.evaluator.function.TrigonometricFunction;
import io.github.iamrushier.evaluator.function.UtilityFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code OperationRegistry} manages the registration and retrieval of mathematical functions.
 */
public class OperationRegistry {
    private static final Map<String, Function> functions = new HashMap<>();

    static {
        // Trigonometric
        registerFunction("sin", (arg, config) ->
            new Operand(TrigonometricFunction.sin(arg.getAsBigDecimal(), getMode(config))));
        registerFunction("cos", (arg, config) ->
            new Operand(TrigonometricFunction.cos(arg.getAsBigDecimal(), getMode(config))));
        registerFunction("tan", (arg, config) ->
            new Operand(TrigonometricFunction.tan(arg.getAsBigDecimal(), getMode(config))));
        registerFunction("asin", (arg, config) ->
            new Operand(TrigonometricFunction.asin(arg.getAsBigDecimal(), getMode(config))));
        registerFunction("acos", (arg, config) ->
            new Operand(TrigonometricFunction.acos(arg.getAsBigDecimal(), getMode(config))));
        registerFunction("atan", (arg, config) ->
            new Operand(TrigonometricFunction.atan(arg.getAsDouble(), getMode(config))));

        // Logarithmic
        registerFunction("log", (arg, config) ->
            new Operand(LogarithmicFunction.log(arg.getAsBigDecimal())));
        registerFunction("ln", (arg, config) ->
            new Operand(LogarithmicFunction.ln(arg.getAsBigDecimal())));

        // Utility & Multi-argument
        registerFunction("abs", UtilityFunction.ABS);
        registerFunction("floor", UtilityFunction.FLOOR);
        registerFunction("ceil", UtilityFunction.CEIL);
        registerFunction("round", UtilityFunction.ROUND);
        registerFunction("min", UtilityFunction.MIN);
        registerFunction("max", UtilityFunction.MAX);
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
