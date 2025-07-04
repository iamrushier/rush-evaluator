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
     *     <li>Inserts '*' between a closing parenthesis and an opening parenthesis (e.g., ')( ' becomes ')*(').</li>
     *     <li>Inserts '*' if a closing parenthesis is followed by a number, constant (π, ∞, e), or function name.</li>
     *     <li>Inserts '*' if a number is followed by anything except a closing parenthesis, another number, or an operator (+, -, *, /, ^, . , E).</li>
     *     <li>Inserts '*' if a constant (π, ∞, e) is followed by anything except a closing parenthesis or an operator (+, -, *, /, ^).</li>
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
        expression = expression.replaceAll("\\)\\(", ")*(");
        // Add * if ')' is followed by number, constant, or function
        expression = expression.replaceAll("\\)(?=(\\d|π|∞|e|sin|cos|tan|asin|acos|atan|log|ln))", ")*");
        // Insert * if number is followed by anything except ')', number, or +,-,*,/,^
        expression = expression.replaceAll("(\\d)(?=[^)\\d+\\-*/^.E])", "$1*");
        // Insert * if π,∞,e are followed by anything except ')', or +,-,*,/,^
        expression = expression.replaceAll("([π∞e])(?=[^)+\\-*/^])", "$1*");
        return expression;
    }
}
