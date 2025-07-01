package in.iamrushier.evaluator.parser;

import in.iamrushier.evaluator.function.LogarithmicFunction;
import in.iamrushier.evaluator.function.TrigonometricFunction;
import in.iamrushier.evaluator.operator.BasicOperator;
import in.iamrushier.evaluator.operator.PowerOperator;
import in.iamrushier.evaluator.util.Constants;

import java.math.BigDecimal;

public class ExpressionParser {
    private int index = 0;

    public String parse(String expression) {
        index = 0;
        return parseExpression(expression);
    }

    private String parseExpression(String expression) {
        String result = parseTerm(expression);
        while (index < expression.length()) {
            char operator = expression.charAt(index);
            if (operator == '+' || operator == '-') {
                index++;
                String nextTerm = parseTerm(expression);
                result = BasicOperator.evaluateOperation(result, nextTerm, operator);
            } else {
                break;
            }
        }
        return result;
    }

    private String parseTerm(String expression) {
        String result = parsePower(expression);
        while (index < expression.length()) {
            char operator = expression.charAt(index);
            if (operator == '*' || operator == '/') {
                index++;
                String nextFactor = parsePower(expression);
                result = BasicOperator.evaluateOperation(result, nextFactor, operator);
            } else {
                break;
            }
        }
        return result;
    }

    private String parsePower(String expression) {
        String result = parseFactor(expression);
        while (index < expression.length()) {
            char operator = expression.charAt(index);
            if (operator == '^') {
                index++;
                String exponent = parseFactor(expression);
                result = PowerOperator.power(result, exponent);
            } else {
                break;
            }
        }
        return result;
    }

    private boolean isIndexValidAndCharIs(int index, String expression, char character) {
        return index < expression.length() && expression.charAt(index) == character;
    }

    private String parseFactor(String expression) {
        String result;
        boolean isNegative = false;
        if (isIndexValidAndCharIs(index, expression, '+')) {
            isNegative = expression.charAt(index) == '-';
            index++;
        }

        if (isIndexValidAndCharIs(index, expression, '(')) {
            index++;
            result = parseExpression(expression);
            if (isIndexValidAndCharIs(index, expression, ')')) {
                index++;
            }
        } else if (isIndexValidAndCharIs(index, expression, 'π')) {
            index++;
            result = Constants.PI.toPlainString();
        } else if (isIndexValidAndCharIs(index, expression, 'e')) {
            index++;
            result = Constants.EULER.toPlainString();
        } else if (isIndexValidAndCharIs(index, expression, '∞')) {
            index++;
            result = "Infinity";
        } else if (Character.isLetter(expression.charAt(index))) {
            result = parseFunction(expression);
        } else {
            result = parseNumber(expression);
        }

        while (isIndexValidAndCharIs(index, expression, '^')) {
            index++;
            String exponent = parseFactor(expression);
            result = PowerOperator.power(result, exponent);
        }

        return isNegative ? "-" + result : result;
    }

    private String parseNumber(String expression) {
        StringBuilder number = new StringBuilder();
        boolean hasDecimal = false;
        boolean hasExponent = false;

        while (index < expression.length()) {
            char currentChar = expression.charAt(index);

            if (Character.isDigit(currentChar)) {
                number.append(currentChar);
                index++;
            } else if (currentChar == '.' && !hasDecimal) {
                hasDecimal = true;
                number.append(currentChar);
                index++;
            } else if ((currentChar == 'e' || currentChar == 'E') && !hasExponent) {
                hasExponent = true;
                number.append(currentChar);
                index++;

                if (index < expression.length()
                        && (expression.charAt(index) == '+' || expression.charAt(index) == '-')) {
                    number.append(expression.charAt(index));
                    index++;
                }
            } else {
                break;
            }
        }

        return number.toString();
    }

    private String parseFunction(String expression) {
        StringBuilder functionName = new StringBuilder();
        while (index < expression.length() && Character.isLetter(expression.charAt(index))) {
            functionName.append(expression.charAt(index));
            index++;
        }

        if (isIndexValidAndCharIs(index, expression, '(')) {
            index++;
            String argument = parseExpression(expression);
            if (isIndexValidAndCharIs(index, expression, ')')) {
                index++;
            }

            switch (functionName.toString()) {
                case "sin":
                    return TrigonometricFunction.handleSin(new BigDecimal(argument));
                case "cos":
                    return TrigonometricFunction.handleCos(new BigDecimal(argument));
                case "tan":
                    return TrigonometricFunction.handleTan(new BigDecimal(argument));
                case "asin":
                    return TrigonometricFunction.handleAsin(new BigDecimal(argument));
                case "acos":
                    return TrigonometricFunction.handleAcos(new BigDecimal(argument));
                case "atan":
                    return TrigonometricFunction.handleAtan(argument);
                case "log":
                    return LogarithmicFunction.handleLog(new BigDecimal(argument));
                case "ln":
                    return LogarithmicFunction.handleLn(new BigDecimal(argument));
                default:
                    throw new IllegalArgumentException("Invalid expression");
            }
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
    }
}
