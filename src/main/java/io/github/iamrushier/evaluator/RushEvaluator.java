package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.ast.ExpressionNode;
import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.DomainException;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.expression.CompiledExpression;
import io.github.iamrushier.evaluator.parser.AstParser;
import io.github.iamrushier.evaluator.parser.Lexer;
import io.github.iamrushier.evaluator.util.ExpressionCache;
import io.github.iamrushier.evaluator.util.ExpressionValidator;

import java.math.BigDecimal;
import java.util.Map;

/**
 * {@code RushEvaluator} is the primary entry point for evaluating and compiling mathematical expressions.
 * It provides static methods to evaluate expressions directly or compile them into reusable {@link CompiledExpression}s
 * with AST optimizations, constant folding, and thread-safe caching.
 */
public class RushEvaluator {

    private static final ExpressionCache CACHE = new ExpressionCache(256);

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private RushEvaluator() {
    }

    // ==================== Compilation Methods ====================

    /**
     * Compiles a mathematical expression into an immutable, thread-safe {@link CompiledExpression}
     * using the default configuration (degrees angle mode).
     *
     * @param expression The mathematical expression string to compile.
     * @return a {@link CompiledExpression} representing the parsed and optimized AST
     * @throws SyntaxException if the expression is invalid
     */
    public static CompiledExpression compile(String expression) {
        return compile(expression, EvaluationConfig.defaultConfig());
    }

    /**
     * Compiles a mathematical expression into an immutable, thread-safe {@link CompiledExpression}
     * with the specified {@link AngleMode}.
     *
     * @param expression The mathematical expression string to compile.
     * @param angleMode The angle mode (DEGREES or RADIANS) to use.
     * @return a {@link CompiledExpression} representing the parsed and optimized AST
     * @throws SyntaxException if the expression is invalid
     */
    public static CompiledExpression compile(String expression, AngleMode angleMode) {
        return compile(expression, new EvaluationConfig(angleMode));
    }

    /**
     * Compiles a mathematical expression into an immutable, thread-safe {@link CompiledExpression}
     * using the provided {@link EvaluationConfig}.
     *
     * @param expression The mathematical expression string to compile.
     * @param config The evaluation configuration.
     * @return a {@link CompiledExpression} representing the parsed and optimized AST
     * @throws SyntaxException if the expression is invalid
     */
    public static CompiledExpression compile(String expression, EvaluationConfig config) {
        if (config == null) {
            config = EvaluationConfig.defaultConfig();
        }

        String cacheKey = ExpressionCache.computeKey(expression, config);
        if (cacheKey != null) {
            CompiledExpression cached = CACHE.get(cacheKey);
            if (cached != null) {
                return cached;
            }
        }

        String validated = ExpressionValidator.validateExpression(expression);
        Lexer lexer = new Lexer(validated);
        AstParser parser = new AstParser(lexer.tokenize(), config);
        ExpressionNode root = parser.parse();
        CompiledExpression compiled = new CompiledExpression(expression, root, config);

        if (cacheKey != null) {
            CACHE.put(cacheKey, compiled);
        }

        return compiled;
    }

    /**
     * Clears the internal compilation cache.
     */
    public static void clearCache() {
        CACHE.clear();
    }

    // ==================== evaluate (String output) ====================

    /**
     * Evaluates a given mathematical expression string using default settings (degrees angle mode).
     *
     * @param expression The mathematical expression string to evaluate.
     * @return The result of the evaluation as a formatted string.
     * @throws DomainException if the result is undefined (e.g., 0/0) or leads to a domain error.
     * @throws SyntaxException if the expression is invalid.
     */
    public static String evaluate(String expression) {
        return evaluate(expression, EvaluationConfig.defaultConfig());
    }

    /**
     * Evaluates a given mathematical expression string with the specified {@link AngleMode}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param angleMode The angle mode (DEGREES or RADIANS) to use for trigonometric calculations.
     * @return The result of the evaluation as a formatted string.
     * @throws DomainException if the result is undefined or leads to a domain error.
     * @throws SyntaxException if the expression is invalid.
     */
    public static String evaluate(String expression, AngleMode angleMode) {
        return evaluate(expression, new EvaluationConfig(angleMode));
    }

    /**
     * Evaluates a mathematical expression string with variable bindings using default settings.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param variables Map of variable names to their values.
     * @return The result of the evaluation as a formatted string.
     * @throws DomainException if the result is undefined or leads to a domain error.
     * @throws SyntaxException if the expression is invalid or contains undefined variables.
     */
    public static String evaluate(String expression, Map<String, ?> variables) {
        return evaluate(expression, EvaluationConfig.defaultConfig().withVariables(variables));
    }

    /**
     * Evaluates a mathematical expression string with variable bindings and the specified {@link AngleMode}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param variables Map of variable names to their values.
     * @param angleMode The angle mode (DEGREES or RADIANS) to use.
     * @return The result of the evaluation as a formatted string.
     * @throws DomainException if the result is undefined or leads to a domain error.
     * @throws SyntaxException if the expression is invalid or contains undefined variables.
     */
    public static String evaluate(String expression, Map<String, ?> variables, AngleMode angleMode) {
        return evaluate(expression, new EvaluationConfig(angleMode).withVariables(variables));
    }

    /**
     * Evaluates a mathematical expression string with variable bindings using the provided {@link EvaluationConfig}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param variables Map of variable names to their values.
     * @param config The evaluation configuration.
     * @return The result of the evaluation as a formatted string.
     * @throws DomainException if the result is undefined or leads to a domain error.
     * @throws SyntaxException if the expression is invalid or contains undefined variables.
     */
    public static String evaluate(String expression, Map<String, ?> variables, EvaluationConfig config) {
        if (config == null) {
            config = EvaluationConfig.defaultConfig();
        }
        return evaluate(expression, config.withVariables(variables));
    }

    /**
     * Evaluates a given mathematical expression string using the provided {@link EvaluationConfig}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param config The evaluation configuration (defaults to {@link EvaluationConfig#defaultConfig()} if null).
     * @return The result of the evaluation as a formatted string.
     * @throws DomainException if the result is undefined or leads to a domain error.
     * @throws SyntaxException if the expression is invalid.
     */
    public static String evaluate(String expression, EvaluationConfig config) {
        if (config == null) {
            config = EvaluationConfig.defaultConfig();
        }
        return compile(expression, config).evaluate(config);
    }

    // ==================== evaluateToBigDecimal ====================

    /**
     * Evaluates an expression and returns the result directly as a {@link BigDecimal} using default settings.
     *
     * @param expression The mathematical expression string to evaluate.
     * @return The evaluated result as a {@link BigDecimal}.
     * @throws DomainException if the result is special (Infinity/NaN) or leads to a domain error.
     * @throws SyntaxException if the expression is invalid.
     */
    public static BigDecimal evaluateToBigDecimal(String expression) {
        return evaluateToBigDecimal(expression, EvaluationConfig.defaultConfig());
    }

    /**
     * Evaluates an expression and returns the result directly as a {@link BigDecimal} with the specified {@link AngleMode}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param angleMode The angle mode to use for trigonometric calculations.
     * @return The evaluated result as a {@link BigDecimal}.
     * @throws DomainException if the result is special (Infinity/NaN) or leads to a domain error.
     * @throws SyntaxException if the expression is invalid.
     */
    public static BigDecimal evaluateToBigDecimal(String expression, AngleMode angleMode) {
        return evaluateToBigDecimal(expression, new EvaluationConfig(angleMode));
    }

    /**
     * Evaluates an expression with variables and returns the result as a {@link BigDecimal} using default settings.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param variables Map of variable names to their values.
     * @return The evaluated result as a {@link BigDecimal}.
     * @throws DomainException if the result is non-finite or leads to a domain error.
     * @throws SyntaxException if the expression is invalid or contains undefined variables.
     */
    public static BigDecimal evaluateToBigDecimal(String expression, Map<String, ?> variables) {
        return evaluateToBigDecimal(expression, EvaluationConfig.defaultConfig().withVariables(variables));
    }

    /**
     * Evaluates an expression with variables and returns the result as a {@link BigDecimal} with the specified {@link AngleMode}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param variables Map of variable names to their values.
     * @param angleMode The angle mode to use.
     * @return The evaluated result as a {@link BigDecimal}.
     * @throws DomainException if the result is non-finite or leads to a domain error.
     * @throws SyntaxException if the expression is invalid or contains undefined variables.
     */
    public static BigDecimal evaluateToBigDecimal(String expression, Map<String, ?> variables, AngleMode angleMode) {
        return evaluateToBigDecimal(expression, new EvaluationConfig(angleMode).withVariables(variables));
    }

    /**
     * Evaluates an expression with variables and returns the result as a {@link BigDecimal} using the provided {@link EvaluationConfig}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param variables Map of variable names to their values.
     * @param config The evaluation configuration.
     * @return The evaluated result as a {@link BigDecimal}.
     * @throws DomainException if the result is non-finite or leads to a domain error.
     * @throws SyntaxException if the expression is invalid or contains undefined variables.
     */
    public static BigDecimal evaluateToBigDecimal(String expression, Map<String, ?> variables, EvaluationConfig config) {
        if (config == null) {
            config = EvaluationConfig.defaultConfig();
        }
        return evaluateToBigDecimal(expression, config.withVariables(variables));
    }

    /**
     * Evaluates an expression and returns the result directly as a {@link BigDecimal} using the provided {@link EvaluationConfig}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param config The evaluation configuration.
     * @return The evaluated result as a {@link BigDecimal}.
     * @throws DomainException if the result is special (Infinity/NaN) or leads to a domain error.
     * @throws SyntaxException if the expression is invalid.
     */
    public static BigDecimal evaluateToBigDecimal(String expression, EvaluationConfig config) {
        if (config == null) {
            config = EvaluationConfig.defaultConfig();
        }
        return compile(expression, config).evaluateToBigDecimal(config);
    }

    // ==================== evaluateToDouble ====================

    /**
     * Evaluates an expression and returns the result as a primitive {@code double} using default settings.
     *
     * @param expression The mathematical expression string to evaluate.
     * @return The evaluated result as a double.
     * @throws DomainException if the expression results in an undefined value.
     * @throws SyntaxException if the expression is invalid.
     */
    public static double evaluateToDouble(String expression) {
        return evaluateToDouble(expression, EvaluationConfig.defaultConfig());
    }

    /**
     * Evaluates an expression and returns the result as a primitive {@code double} with the specified {@link AngleMode}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param angleMode The angle mode to use for trigonometric calculations.
     * @return The evaluated result as a double.
     * @throws DomainException if the expression results in an undefined value.
     * @throws SyntaxException if the expression is invalid.
     */
    public static double evaluateToDouble(String expression, AngleMode angleMode) {
        return evaluateToDouble(expression, new EvaluationConfig(angleMode));
    }

    /**
     * Evaluates an expression with variables and returns the result as a primitive {@code double} using default settings.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param variables Map of variable names to their values.
     * @return The evaluated result as a double.
     * @throws DomainException if the expression results in an undefined value.
     * @throws SyntaxException if the expression is invalid or contains undefined variables.
     */
    public static double evaluateToDouble(String expression, Map<String, ?> variables) {
        return evaluateToDouble(expression, EvaluationConfig.defaultConfig().withVariables(variables));
    }

    /**
     * Evaluates an expression with variables and returns the result as a primitive {@code double} with the specified {@link AngleMode}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param variables Map of variable names to their values.
     * @param angleMode The angle mode to use.
     * @return The evaluated result as a double.
     * @throws DomainException if the expression results in an undefined value.
     * @throws SyntaxException if the expression is invalid or contains undefined variables.
     */
    public static double evaluateToDouble(String expression, Map<String, ?> variables, AngleMode angleMode) {
        return evaluateToDouble(expression, new EvaluationConfig(angleMode).withVariables(variables));
    }

    /**
     * Evaluates an expression with variables and returns the result as a primitive {@code double} using the provided {@link EvaluationConfig}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param variables Map of variable names to their values.
     * @param config The evaluation configuration.
     * @return The evaluated result as a double.
     * @throws DomainException if the expression results in an undefined value.
     * @throws SyntaxException if the expression is invalid or contains undefined variables.
     */
    public static double evaluateToDouble(String expression, Map<String, ?> variables, EvaluationConfig config) {
        if (config == null) {
            config = EvaluationConfig.defaultConfig();
        }
        return evaluateToDouble(expression, config.withVariables(variables));
    }

    /**
     * Evaluates an expression and returns the result as a primitive {@code double} using the provided {@link EvaluationConfig}.
     *
     * @param expression The mathematical expression string to evaluate.
     * @param config The evaluation configuration.
     * @return The evaluated result as a double.
     * @throws DomainException if the expression results in an undefined value.
     * @throws SyntaxException if the expression is invalid.
     */
    public static double evaluateToDouble(String expression, EvaluationConfig config) {
        if (config == null) {
            config = EvaluationConfig.defaultConfig();
        }
        return compile(expression, config).evaluateToDouble(config);
    }
}
