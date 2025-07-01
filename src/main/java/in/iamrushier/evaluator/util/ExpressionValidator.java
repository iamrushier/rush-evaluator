package in.iamrushier.evaluator.util;

public class ExpressionValidator {
    private ExpressionValidator() {
    }

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
