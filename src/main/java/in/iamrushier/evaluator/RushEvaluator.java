package in.iamrushier.evaluator;

import in.iamrushier.evaluator.parser.ExpressionParser;
import in.iamrushier.evaluator.util.ExpressionValidator;
import in.iamrushier.evaluator.util.OutputFormatter;

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
        // Check for extremely large numbers that might exceed double precision
        if (!resultString.contains("Infinity") && !resultString.contains("-Infinity")) {
            try {
                BigDecimal bd = new BigDecimal(resultString);
                if (bd.abs().compareTo(BigDecimal.valueOf(Math.pow(10, 308))) > 0)
                    return "Can't calculate";
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid expression");
            }
        }
        // Format the result for consistent scientific notation
        try {
            BigDecimal bd = new BigDecimal(resultString);
            return OutputFormatter.consistentScientific(String.valueOf(bd.doubleValue()));
        } catch (Exception e) {
            // Fallback for cases where BigDecimal conversion might fail but Double.parseDouble works
            double result = Double.parseDouble(resultString);
            resultString = String.valueOf(result);
            resultString = OutputFormatter.consistentScientific(resultString);
            return resultString.replace("Infinity", "∞");
        }
    }
}