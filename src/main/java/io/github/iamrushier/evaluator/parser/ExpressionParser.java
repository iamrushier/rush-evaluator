package io.github.iamrushier.evaluator.parser;

import io.github.iamrushier.evaluator.function.LogarithmicFunction;
import io.github.iamrushier.evaluator.function.TrigonometricFunction;
import io.github.iamrushier.evaluator.util.CalculationEngine;
import io.github.iamrushier.evaluator.util.Constants;
import io.github.iamrushier.evaluator.util.Operand;

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
        return parseExpression(expression).toString();
    }

    /**
     * Parses an expression, handling addition and subtraction.
     * @param expression The full expression string.
     * @return The result of the expression as an Operand.
     */
    private Operand parseExpression(String expression) {
        Operand result = parseTerm(expression);
        while (index < expression.length()) {
            char operator = expression.charAt(index);
            if (operator == '+' || operator == '-') {
                index++;
                // In a calculator, an operator cannot be followed by another operator
                if (index < expression.length() && (expression.charAt(index) == '+' || expression.charAt(index) == '-' || expression.charAt(index) == '*' || expression.charAt(index) == '/' || expression.charAt(index) == '^')) {
                    throw new NumberFormatException("Invalid expression");
                }
                Operand nextTerm = parseTerm(expression);
                result = CalculationEngine.calculate(result, nextTerm, operator);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Parses a term, handling multiplication and division.
     * @param expression The full expression string.
     * @return The result of the term as an Operand.
     */
    private Operand parseTerm(String expression) {
        Operand result = parsePower(expression);
        while (index < expression.length()) {
            char operator = expression.charAt(index);
            if (operator == '*' || operator == '/') {
                index++;
                // In a calculator, an operator cannot be followed by another operator
                if (index < expression.length() && (expression.charAt(index) == '+' || expression.charAt(index) == '-' || expression.charAt(index) == '*' || expression.charAt(index) == '/' || expression.charAt(index) == '^')) {
                    throw new NumberFormatException("Invalid expression");
                }
                Operand nextFactor = parsePower(expression);
                result = CalculationEngine.calculate(result, nextFactor, operator);
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Parses a power operation, handling exponentiation.
     * @param expression The full expression string.
     * @return The result of the power operation as an Operand.
     */
    private Operand parsePower(String expression) {
        Operand result = parseFactor(expression);
        while (index < expression.length()) {
            char operator = expression.charAt(index);
            if (operator == '^') {
                index++;
                // In a calculator, an operator cannot be followed by another operator
                if (index < expression.length() && (expression.charAt(index) == '+' || expression.charAt(index) == '-' || expression.charAt(index) == '*' || expression.charAt(index) == '/' || expression.charAt(index) == '^')) {
                    throw new NumberFormatException("Invalid expression");
                }
                Operand exponent = parseFactor(expression);
                result = CalculationEngine.power(result, exponent);
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
     * @return The result of the factor as an Operand.
     */
    private Operand parseFactor(String expression) {
        Operand result;
        boolean isNegative = false;

        // Support only ONE leading + or - sign (e.g., -5, (-5), sin(-30))
        if (index < expression.length() && (expression.charAt(index) == '+' || expression.charAt(index) == '-')) {
            isNegative = expression.charAt(index) == '-';
            index++;
            
            // Reject double signs at the start of a factor (e.g., "--2")
            if (index < expression.length() && (expression.charAt(index) == '+' || expression.charAt(index) == '-')) {
                throw new NumberFormatException("Invalid expression");
            }
        }

        if (isIndexValidAndCharIs(index, expression, '(')) {
            index++;
            result = parseExpression(expression);
            if (isIndexValidAndCharIs(index, expression, ')')) {
                index++;
            }
        } else if (isIndexValidAndCharIs(index, expression, '\u03c0')) {
            index++;
            result = new Operand(Constants.PI);
        } else if (isIndexValidAndCharIs(index, expression, 'e')) {
            index++;
            result = new Operand(Constants.EULER);
        } else if (isIndexValidAndCharIs(index, expression, '\u221e')) {
            index++;
            result = new Operand(Double.POSITIVE_INFINITY);
        } else if (index < expression.length() && Character.isLetter(expression.charAt(index))) {
            result = parseFunction(expression);
        } else {
            String number = parseNumber(expression);
            if (number.isEmpty()) {
                throw new NumberFormatException("Invalid expression");
            }
            result = Operand.of(number);
        }

        while (isIndexValidAndCharIs(index, expression, '^')) {
            index++;
            Operand exponent = parseFactor(expression);
            result = CalculationEngine.power(result, exponent);
        }

        return isNegative ? CalculationEngine.calculate(new Operand(BigDecimal.ZERO), result, '-') : result;
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
     * @return The result of the function call as an Operand.
     * @throws IllegalArgumentException if the function is unknown or the syntax is invalid.
     */
    private Operand parseFunction(String expression) {
        StringBuilder functionName = new StringBuilder();
        while (index < expression.length() && Character.isLetter(expression.charAt(index))) {
            functionName.append(expression.charAt(index));
            index++;
        }

        if (isIndexValidAndCharIs(index, expression, '(')) {
            index++;
            Operand argument = parseExpression(expression);
            if (isIndexValidAndCharIs(index, expression, ')')) {
                index++;
            }

            switch (functionName.toString()) {
                case "sin":
                    return new Operand(Double.parseDouble(TrigonometricFunction.handleSin(argument.getAsBigDecimal())));
                case "cos":
                    return new Operand(Double.parseDouble(TrigonometricFunction.handleCos(argument.getAsBigDecimal())));
                case "tan":
                    return new Operand(Double.parseDouble(TrigonometricFunction.handleTan(argument.getAsBigDecimal())));
                case "asin":
                    return new Operand(Double.parseDouble(TrigonometricFunction.handleAsin(argument.getAsBigDecimal())));
                case "acos":
                    return new Operand(Double.parseDouble(TrigonometricFunction.handleAcos(argument.getAsBigDecimal())));
                case "atan":
                    return new Operand(Double.parseDouble(TrigonometricFunction.handleAtan(argument.toString())));
                case "log":
                    return new Operand(Double.parseDouble(LogarithmicFunction.handleLog(argument.getAsBigDecimal())));
                case "ln":
                    return new Operand(Double.parseDouble(LogarithmicFunction.handleLn(argument.getAsBigDecimal())));
                default:
                    throw new IllegalArgumentException("Invalid expression");
            }
        } else {
            throw new IllegalArgumentException("Invalid expression");
        }
    }
}
