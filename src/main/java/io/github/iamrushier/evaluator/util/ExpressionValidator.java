package io.github.iamrushier.evaluator.util;

/**
 * {@code ExpressionValidator} provides utility methods for validating and preprocessing mathematical expressions.
 * It handles various transformations to ensure the expression is in a parsable format.
 */
public class ExpressionValidator {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private ExpressionValidator() {
    }

    /**
     * Validates and preprocesses a raw mathematical expression string.
     * This method performs the following transformations:
     * <ul>
     *     <li>Removes all whitespace.</li>
     *     <li>Replaces '×' with '*'.</li>
     *     <li>Replaces '÷' with '/'.</li>
     *     <li>Replaces '%' with '(1/100)'.</li>
     *     <li>Inserts '*' if a closing parenthesis is followed by an opening parenthesis, number, constant, function, or variable.</li>
     *     <li>Inserts '*' if a number is followed by anything except a closing parenthesis, digit, decimal point, or operator (+, -, *, /, ^).</li>
     *     <li>Inserts '*' if a constant (π, ∞, standalone e) is followed by an opening parenthesis or another operand.</li>
     * </ul>
     *
     * @param rawExpression The raw mathematical expression string.
     * @return The validated and preprocessed expression string.
     */
    public static String validateExpression(String rawExpression) {
        String expression = rawExpression.replaceAll("\\s+", "");
        expression = expression.replace("×", "*");
        expression = expression.replace("÷", "/");
        expression = expression.replace("%", "(1/100)");
        // Insert * if ')' is followed by anything except ')' or an operator (+, -, *, /, ^)
        expression = expression.replaceAll("\\)(?=[^)+\\-*/^])", ")*");
        // Insert * if number is followed by anything except ')', digit, '.', 'E', or operator (+, -, *, /, ^)
        expression = expression.replaceAll("(\\d)(?=[^)\\d+\\-*/^.E])", "$1*");
        // Insert * if π or ∞ are followed by anything except ')' or an operator (+, -, *, /, ^)
        expression = expression.replaceAll("([π∞])(?=[^)+\\-*/^])", "$1*");
        // Insert * if standalone constant 'e' is followed by anything except ')' or an operator (+, -, *, /, ^) or identifier character
        expression = expression.replaceAll("(?<![a-zA-Z0-9_])e(?=[^)+\\-*/^a-zA-Z0-9_])", "e*");
        return expression;
    }
}
