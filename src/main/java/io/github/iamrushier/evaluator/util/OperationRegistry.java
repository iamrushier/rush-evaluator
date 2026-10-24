package io.github.iamrushier.evaluator.util;

import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.function.Function;
import io.github.iamrushier.evaluator.function.LogarithmicFunction;
import io.github.iamrushier.evaluator.function.TrigonometricFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code OperationRegistry} manages the registration and retrieval of mathematical functions.
 */
public class OperationRegistry {
    private static final Map<String, Function> functions = new HashMap<>();

    static {
        registerFunction("sin", (arg, config) ->
            new Operand(Double.parseDouble(TrigonometricFunction.handleSin(arg.getAsBigDecimal(), getMode(config)))));
        registerFunction("cos", (arg, config) ->
            new Operand(Double.parseDouble(TrigonometricFunction.handleCos(arg.getAsBigDecimal(), getMode(config)))));
        registerFunction("tan", (arg, config) ->
            new Operand(Double.parseDouble(TrigonometricFunction.handleTan(arg.getAsBigDecimal(), getMode(config)))));
        registerFunction("asin", (arg, config) ->
            new Operand(Double.parseDouble(TrigonometricFunction.handleAsin(arg.getAsBigDecimal(), getMode(config)))));
        registerFunction("acos", (arg, config) ->
            new Operand(Double.parseDouble(TrigonometricFunction.handleAcos(arg.getAsBigDecimal(), getMode(config)))));
        registerFunction("atan", (arg, config) ->
            new Operand(Double.parseDouble(TrigonometricFunction.handleAtan(arg.toString(), getMode(config)))));
        registerFunction("log", (arg, config) ->
            new Operand(Double.parseDouble(LogarithmicFunction.handleLog(arg.getAsBigDecimal()))));
        registerFunction("ln", (arg, config) ->
            new Operand(Double.parseDouble(LogarithmicFunction.handleLn(arg.getAsBigDecimal()))));
    }

    private static AngleMode getMode(EvaluationConfig config) {
        return config != null ? config.getAngleMode() : AngleMode.DEGREES;
    }

    private static void registerFunction(String name, ConfigurableFunction func) {
        functions.put(name, func);
    }

    private OperationRegistry() {}

    /**
     * Functional interface for functions supporting evaluation configuration.
     */
    @FunctionalInterface
    public interface ConfigurableFunction extends Function {
        @Override
        default Operand apply(Operand argument) {
            return apply(argument, EvaluationConfig.defaultConfig());
        }

        @Override
        Operand apply(Operand argument, EvaluationConfig config);
    }

    /**
     * Retrieves a function by name.
     * @param name The name of the function.
     * @return The Function implementation, or null if not found.
     */
    public static Function getFunction(String name) {
        return functions.get(name);
    }
}
