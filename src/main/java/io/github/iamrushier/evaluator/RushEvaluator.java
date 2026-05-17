package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.parser.ExpressionParser;
import io.github.iamrushier.evaluator.util.ExpressionValidator;
import io.github.iamrushier.evaluator.util.OutputFormatter;

import java.math.BigDecimal;

/**
 * {@code RushEvaluator} is a utility class for evaluating mathematical expressions.
 * It provides a static method to parse and evaluate a given expression string.
 */
public class RushEvaluator {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private RushEvaluator() {
    }

    /**
     * Evaluates a given mathematical expression string.
     * The expression can include numbers, basic arithmetic operators (+, -, *, /),
     * power operator (^), trigonometric functions (sin, cos, tan, asin, acos, atan),
     * logarithmic functions (log, ln), and constants (π, e, ∞).
     *
     * @param expression The mathematical expression string to evaluate.
     * @return The result of the evaluation as a formatted string.
     * @throws NumberFormatException if the result is undefined (e.g., 0/0).
     * @throws IllegalArgumentException if the expression is invalid or leads to a domain error
     *                                  (e.g., log of a non-positive number, asin/acos of values outside [-1, 1]).
     */
    public static String evaluate(String expression) {
        // Validate and preprocess the expression string
        expression = ExpressionValidator.validateExpression(expression);
        ExpressionParser parser = new ExpressionParser();
        String resultString = parser.parse(expression);

        // Handle specific undefined cases
        if (resultString.equals("NaN")) {
            throw new NumberFormatException("Undefined");
        }
        
        // Return result directly as parser/operand now handles formatting consistently
        return resultString;
    }
}