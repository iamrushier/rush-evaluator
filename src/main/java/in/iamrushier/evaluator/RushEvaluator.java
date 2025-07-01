package in.iamrushier.evaluator;

import in.iamrushier.evaluator.parser.ExpressionParser;
import in.iamrushier.evaluator.util.ExpressionValidator;
import in.iamrushier.evaluator.util.OutputFormatter;

import java.math.BigDecimal;

public class RushEvaluator {

    private RushEvaluator() {
    }

    public static String evaluate(String expression) {
        expression = ExpressionValidator.validateExpression(expression);
        ExpressionParser parser = new ExpressionParser();
        String resultString = parser.parse(expression);

        if (resultString.equals("NaN")) {
            throw new NumberFormatException("Undefined");
        }
        if (!resultString.contains("Infinity") && !resultString.contains("-Infinity")) {
            try {
                BigDecimal bd = new BigDecimal(resultString);
                if (bd.abs().compareTo(BigDecimal.valueOf(Math.pow(10, 308))) > 0)
                    return "Can't calculate";
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid expression");
            }
        }
        try {
            BigDecimal bd = new BigDecimal(resultString);
            return OutputFormatter.consistentScientific(String.valueOf(bd.doubleValue()));
        } catch (Exception e) {
            double result = Double.parseDouble(resultString);
            resultString = String.valueOf(result);
            resultString = OutputFormatter.consistentScientific(resultString);
            return resultString.replace("Infinity", "∞");
        }
    }
}