package io.github.iamrushier.evaluator.util;

import io.github.iamrushier.evaluator.function.Function;
import io.github.iamrushier.evaluator.function.LogarithmicFunction;
import io.github.iamrushier.evaluator.function.TrigonometricFunction;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code OperationRegistry} manages the registration and retrieval of mathematical functions.
 */
public class OperationRegistry {
    private static final Map<String, Function> functions = new HashMap<>();

    static {
        functions.put("sin", arg -> new Operand(Double.parseDouble(TrigonometricFunction.handleSin(arg.getAsBigDecimal()))));
        functions.put("cos", arg -> new Operand(Double.parseDouble(TrigonometricFunction.handleCos(arg.getAsBigDecimal()))));
        functions.put("tan", arg -> new Operand(Double.parseDouble(TrigonometricFunction.handleTan(arg.getAsBigDecimal()))));
        functions.put("asin", arg -> new Operand(Double.parseDouble(TrigonometricFunction.handleAsin(arg.getAsBigDecimal()))));
        functions.put("acos", arg -> new Operand(Double.parseDouble(TrigonometricFunction.handleAcos(arg.getAsBigDecimal()))));
        functions.put("atan", arg -> new Operand(Double.parseDouble(TrigonometricFunction.handleAtan(arg.toString()))));
        functions.put("log", arg -> new Operand(Double.parseDouble(LogarithmicFunction.handleLog(arg.getAsBigDecimal()))));
        functions.put("ln", arg -> new Operand(Double.parseDouble(LogarithmicFunction.handleLn(arg.getAsBigDecimal()))));
    }

    private OperationRegistry() {}

    /**
     * Retrieves a function by name.
     * @param name The name of the function.
     * @return The Function implementation, or null if not found.
     */
    public static Function getFunction(String name) {
        return functions.get(name);
    }
}
