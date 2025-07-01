package in.iamrushier.evaluator.operator;

import in.iamrushier.evaluator.util.OutputFormatter;

import java.math.BigDecimal;
import java.math.MathContext;

public class BasicOperator {
    private BasicOperator() {
    }

    public static String evaluateOperation(String leftStr, String rightStr, char operator) {
        try {
            BigDecimal left = new BigDecimal(leftStr);
            BigDecimal right = new BigDecimal(rightStr);
            switch (operator) {
                case '+':
                    return OutputFormatter.formatResult(left.add(right));
                case '-':
                    return OutputFormatter.formatResult(left.subtract(right));
                case '*':
                    return OutputFormatter.formatResult(left.multiply(right));
                case '/':
                    return OutputFormatter.formatResult(left.divide(right, MathContext.DECIMAL128));
                default:
                    throw new ArithmeticException("Invalid expression");
            }
        } catch (Exception e) {
            double left = Double.parseDouble(leftStr);
            double right = Double.parseDouble(rightStr);
            switch (operator) {
                case '+':
                    return String.valueOf(left + right);
                case '-':
                    return String.valueOf(left - right);
                case '*':
                    return String.valueOf(left * right);
                case '/':
                    return String.valueOf(left / right);
                default:
                    throw new ArithmeticException("Invalid expression");
            }
        }

    }
}
