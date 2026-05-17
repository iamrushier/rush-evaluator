package io.github.iamrushier.evaluator.parser;

import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.function.Function;
import io.github.iamrushier.evaluator.util.CalculationEngine;
import io.github.iamrushier.evaluator.util.Constants;
import io.github.iamrushier.evaluator.util.Operand;
import io.github.iamrushier.evaluator.util.OperationRegistry;

import java.math.BigDecimal;
import java.util.List;

/**
 * {@code ExpressionParser} is responsible for parsing and evaluating mathematical expressions.
 * It uses a recursive descent parser working on a stream of tokens from the Lexer.
 */
public class ExpressionParser {
    private List<Token> tokens;
    private int pos;

    /**
     * Parses and evaluates the given mathematical expression.
     * This is the entry point for the parsing process.
     *
     * @param expression The mathematical expression string to parse.
     * @return The result of the parsed expression as a String.
     */
    public String parse(String expression) {
        Lexer lexer = new Lexer(expression);
        this.tokens = lexer.tokenize();
        this.pos = 0;
        return parseExpression().toString();
    }

    private Token peek() {
        return tokens.get(pos);
    }

    private Token eat() {
        return tokens.get(pos++);
    }

    private boolean match(TokenType type) {
        if (peek().getType() == type) {
            eat();
            return true;
        }
        return false;
    }

    private boolean matchOperator(String op) {
        if (peek().getType() == TokenType.OPERATOR && peek().getValue().equals(op)) {
            eat();
            return true;
        }
        return false;
    }

    /**
     * Parses an expression, handling addition and subtraction.
     */
    private Operand parseExpression() {
        Operand result = parseTerm();
        while (peek().getType() == TokenType.OPERATOR) {
            String opValue = peek().getValue();
            if (opValue.equals("+") || opValue.equals("-")) {
                eat();
                // In a calculator, an operator cannot be followed by another operator
                if (peek().getType() == TokenType.OPERATOR || peek().getType() == TokenType.EOF) {
                    throw new SyntaxException("Invalid expression");
                }
                Operand nextTerm = parseTerm();
                result = CalculationEngine.calculate(result, nextTerm, opValue.charAt(0));
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Parses a term, handling multiplication and division.
     */
    private Operand parseTerm() {
        Operand result = parsePower();
        while (peek().getType() == TokenType.OPERATOR) {
            String opValue = peek().getValue();
            if (opValue.equals("*") || opValue.equals("/")) {
                eat();
                if (peek().getType() == TokenType.OPERATOR || peek().getType() == TokenType.EOF) {
                    throw new SyntaxException("Invalid expression");
                }
                Operand nextFactor = parsePower();
                result = CalculationEngine.calculate(result, nextFactor, opValue.charAt(0));
            } else {
                break;
            }
        }
        return result;
    }

    /**
     * Parses a power operation, handling exponentiation.
     */
    private Operand parsePower() {
        Operand result = parseFactor();
        while (matchOperator("^")) {
            if (peek().getType() == TokenType.OPERATOR || peek().getType() == TokenType.EOF) {
                throw new SyntaxException("Invalid expression");
            }
            Operand exponent = parseFactor();
            result = CalculationEngine.calculate(result, exponent, '^');
        }
        return result;
    }

    /**
     * Parses a factor: numbers, parenthesized expressions, constants, roots, or functions.
     */
    private Operand parseFactor() {
        boolean isNegative = false;

        // Support only ONE leading + or - sign
        if (peek().getType() == TokenType.OPERATOR) {
            String op = peek().getValue();
            if (op.equals("+") || op.equals("-")) {
                isNegative = op.equals("-");
                eat();
                // Reject double signs at the start of a factor
                if (peek().getType() == TokenType.OPERATOR) {
                    throw new SyntaxException("Invalid expression");
                }
            }
        }

        Operand result;
        if (match(TokenType.PAREN_OPEN)) {
            result = parseExpression();
            if (!match(TokenType.PAREN_CLOSE)) {
                // For a calculator, we might want to automatically close, but for robustness we throw
                throw new SyntaxException("Missing closing parenthesis");
            }
        } else if (peek().getType() == TokenType.ROOT) {
            String rootType = eat().getValue();
            if (rootType.equals("\u221a")) { // √
                result = CalculationEngine.sqrt(parseFactor());
            } else { // ∛
                result = CalculationEngine.cbrt(parseFactor());
            }
        } else if (peek().getType() == TokenType.CONSTANT) {
            String constant = eat().getValue();
            switch (constant) {
                case "\u03c0": case "PI": result = new Operand(Constants.PI); break;
                case "e": result = new Operand(Constants.EULER); break;
                case "\u221e": case "Infinity": result = new Operand(Double.POSITIVE_INFINITY); break;
                default: throw new SyntaxException("Unknown constant: " + constant);
            }
        } else if (peek().getType() == TokenType.FUNCTION) {
            result = parseFunction();
        } else if (peek().getType() == TokenType.NUMBER) {
            result = Operand.of(eat().getValue());
        } else {
            throw new SyntaxException("Invalid expression");
        }

        // Handle nested power at factor level for higher precedence
        while (matchOperator("^")) {
            Operand exponent = parseFactor();
            result = CalculationEngine.calculate(result, exponent, '^');
        }

        return isNegative ? CalculationEngine.calculate(new Operand(BigDecimal.ZERO), result, '-') : result;
    }

    /**
     * Parses a function call.
     */
    private Operand parseFunction() {
        String functionName = eat().getValue();
        if (match(TokenType.PAREN_OPEN)) {
            Operand argument = parseExpression();
            if (!match(TokenType.PAREN_CLOSE)) {
                 throw new SyntaxException("Missing closing parenthesis in function: " + functionName);
            }
            
            Function func = OperationRegistry.getFunction(functionName.toLowerCase());
            if (func != null) {
                return func.apply(argument);
            } else {
                throw new SyntaxException("Unknown function: " + functionName);
            }
        } else {
            throw new SyntaxException("Expected '(' after function: " + functionName);
        }
    }
}
