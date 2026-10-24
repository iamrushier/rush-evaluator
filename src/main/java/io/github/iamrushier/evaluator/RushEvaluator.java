package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.DomainException;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.parser.ExpressionParser;
import io.github.iamrushier.evaluator.util.ExpressionValidator;
import io.github.iamrushier.evaluator.util.Operand;

import java.math.BigDecimal;

/**
 * {@code RushEvaluator} is the primary entry point for evaluating mathematical expressions.
 * It provides static methods to evaluate expression strings and obtain results as formatted
 * strings, {@link BigDecimal}, or {@code double}.
 */
public class RushEvaluator {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private RushEvaluator() {
    }

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
        expression = ExpressionValidator.validateExpression(expression);
        ExpressionParser parser = new ExpressionParser(config);
        String resultString = parser.parse(expression);

        // Handle specific undefined cases
        if (resultString.equals("NaN")) {
            throw new DomainException("Undefined");
        }

        return resultString;
    }

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
        expression = ExpressionValidator.validateExpression(expression);
        ExpressionParser parser = new ExpressionParser(config);
        Operand operand = parser.parseToOperand(expression);

        if (operand.isSpecial()) {
            throw new DomainException("Result is non-finite (" + operand + ") and cannot be converted to BigDecimal");
        }
        return operand.getAsBigDecimal();
    }

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
        expression = ExpressionValidator.validateExpression(expression);
        ExpressionParser parser = new ExpressionParser(config);
        Operand operand = parser.parseToOperand(expression);
        double val = operand.getAsDouble();

        if (Double.isNaN(val)) {
            throw new DomainException("Undefined");
        }
        return val;
    }
}
