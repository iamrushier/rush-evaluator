package io.github.iamrushier.evaluator.parser;

import io.github.iamrushier.evaluator.function.LogarithmicFunction;
import io.github.iamrushier.evaluator.function.TrigonometricFunction;
import io.github.iamrushier.evaluator.operator.BasicOperator;
import io.github.iamrushier.evaluator.operator.PowerOperator;
import io.github.iamrushier.evaluator.util.Constants;

import java.math.BigDecimal;

/**
 * {@code ExpressionParser} is responsible for parsing and evaluating mathematical expressions.
 * It uses a recursive descent parser to handle different levels of operator precedence.
 */
public class ExpressionParser {
    private int index = 0;

    /**
     * Parses and evaluates the given mathematical expression.
     * This is the entry point for the parsing process.
     *
     * @param expression The mathematical expression string to parse.
     * @return The result of the parsed expression as a String.
     */
    public String parse(String expression) {
        index = 0;
        return parseExpression(expression);
    }

    /**
     * Parses an expression, handling addition and subtraction.
     * @param expression The full expression string.
     * @return The result of the expression as a String.
     */
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

    /**
     * Parses a term, handling multiplication and division.
     * @param expression The full expression string.
     * @return The result of the term as a String.
     */
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

    /**
     * Parses a power operation, handling exponentiation.
     * @param expression The full expression string.
     * @return The result of the power operation as a String.
     */
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

    /**
     * Checks if the current index is valid and the character at the index matches the given character.
     * @param index The current index.
     * @param expression The expression string.
     * @param character The character to match.
     * @return True if the index is valid and the character matches, false otherwise.
     */
    private boolean isIndexValidAndCharIs(int index, String expression, char character) {
        return index < expression.length() && expression.charAt(index) == character;
    }

    /**
     * Parses a factor, which can be a number, a parenthesized expression, a constant, or a function.
     * @param expression The full expression string.
     * @return The result of the factor as a String.
     */
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
        } else if (isIndexValidAndCharIs(index, expression, '\u03c0')) {
            index++;
            result = Constants.PI.toPlainString();
        } else if (isIndexValidAndCharIs(index, expression, 'e')) {
            index++;
            result = Constants.EULER.toPlainString();
        } else if (isIndexValidAndCharIs(index, expression, '\u221e')) {
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

    /**
     * Parses a number from the expression string.
     * @param expression The full expression string.
     * @return The parsed number as a String.
     */
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

    /**
     * Parses a function call from the expression string.
     * @param expression The full expression string.
     * @return The result of the function call as a String.
     * @throws IllegalArgumentException if the function is unknown or the syntax is invalid.
     */
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
