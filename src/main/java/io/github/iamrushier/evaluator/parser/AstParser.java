package io.github.iamrushier.evaluator.parser;

import io.github.iamrushier.evaluator.ast.BinaryOpNode;
import io.github.iamrushier.evaluator.ast.ExpressionNode;
import io.github.iamrushier.evaluator.ast.FunctionNode;
import io.github.iamrushier.evaluator.ast.NumberNode;
import io.github.iamrushier.evaluator.ast.UnaryOpNode;
import io.github.iamrushier.evaluator.ast.VariableNode;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.util.Constants;
import io.github.iamrushier.evaluator.util.Operand;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code AstParser} builds an abstract syntax tree (AST) of {@link ExpressionNode}s from a token stream.
 */
public class AstParser {

    private final List<Token> tokens;
    private final EvaluationConfig config;
    private int pos;

    /**
     * Constructs an {@code AstParser} with the given tokens and evaluation configuration.
     *
     * @param tokens the list of tokens from {@link Lexer}
     * @param config the active evaluation configuration
     */
    public AstParser(List<Token> tokens, EvaluationConfig config) {
        this.tokens = tokens;
        this.config = config != null ? config : EvaluationConfig.defaultConfig();
        this.pos = 0;
    }

    /**
     * Parses the token stream into an optimized, constant-folded {@link ExpressionNode} tree.
     *
     * @return the root {@link ExpressionNode} of the AST
     * @throws SyntaxException if the token stream is invalid
     */
    public ExpressionNode parse() {
        if (tokens == null || tokens.isEmpty() || peek().getType() == TokenType.EOF) {
            throw new SyntaxException("Empty expression");
        }

        ExpressionNode root = parseExpression();

        if (peek().getType() != TokenType.EOF) {
            throw new SyntaxException("Unexpected token after expression: " + peek().getValue());
        }

        // Perform constant folding optimization
        return root.foldConstants(config);
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

    private ExpressionNode parseExpression() {
        ExpressionNode result = parseTerm();
        while (peek().getType() == TokenType.OPERATOR) {
            String op = peek().getValue();
            if (op.equals("+") || op.equals("-")) {
                eat();
                if (peek().getType() == TokenType.OPERATOR || peek().getType() == TokenType.EOF) {
                    throw new SyntaxException("Invalid expression");
                }
                ExpressionNode nextTerm = parseTerm();
                result = new BinaryOpNode(op.charAt(0), result, nextTerm);
            } else {
                break;
            }
        }
        return result;
    }

    private ExpressionNode parseTerm() {
        ExpressionNode result = parsePower();
        while (true) {
            if (peek().getType() == TokenType.OPERATOR) {
                String op = peek().getValue();
                if (op.equals("*") || op.equals("/")) {
                    eat();
                    if (peek().getType() == TokenType.OPERATOR || peek().getType() == TokenType.EOF) {
                        throw new SyntaxException("Invalid expression");
                    }
                    ExpressionNode nextFactor = parsePower();
                    result = new BinaryOpNode(op.charAt(0), result, nextFactor);
                } else {
                    break;
                }
            } else if (peek().getType() == TokenType.PAREN_OPEN
                    || peek().getType() == TokenType.VARIABLE
                    || peek().getType() == TokenType.FUNCTION
                    || peek().getType() == TokenType.CONSTANT
                    || peek().getType() == TokenType.ROOT) {
                // Implicit multiplication: e.g. x(y + 1), (x)(y), 2x
                ExpressionNode nextFactor = parsePower();
                result = new BinaryOpNode('*', result, nextFactor);
            } else {
                break;
            }
        }
        return result;
    }

    private ExpressionNode parsePower() {
        ExpressionNode result = parseFactor();
        while (matchOperator("^")) {
            if (peek().getType() == TokenType.OPERATOR || peek().getType() == TokenType.EOF) {
                throw new SyntaxException("Invalid expression");
            }
            ExpressionNode exponent = parseFactor();
            result = new BinaryOpNode('^', result, exponent);
        }
        return result;
    }

    private ExpressionNode parseFactor() {
        boolean isNegative = false;

        // Support only ONE leading + or - sign
        if (peek().getType() == TokenType.OPERATOR) {
            String op = peek().getValue();
            if (op.equals("+") || op.equals("-")) {
                isNegative = op.equals("-");
                eat();
                if (peek().getType() == TokenType.OPERATOR) {
                    throw new SyntaxException("Invalid expression");
                }
            }
        }

        ExpressionNode result;
        if (match(TokenType.PAREN_OPEN)) {
            result = parseExpression();
            if (!match(TokenType.PAREN_CLOSE)) {
                throw new SyntaxException("Missing closing parenthesis");
            }
        } else if (peek().getType() == TokenType.ROOT) {
            String rootType = eat().getValue();
            char rootChar = rootType.charAt(0);
            result = new UnaryOpNode(rootChar, parseFactor());
        } else if (peek().getType() == TokenType.CONSTANT) {
            String constant = eat().getValue();
            switch (constant) {
                case "\u03c0": case "PI": result = new NumberNode(new Operand(Constants.PI)); break;
                case "e": result = new NumberNode(new Operand(Constants.EULER)); break;
                case "\u221e": case "Infinity": result = new NumberNode(new Operand(Double.POSITIVE_INFINITY)); break;
                default: throw new SyntaxException("Unknown constant: " + constant);
            }
        } else if (peek().getType() == TokenType.FUNCTION) {
            result = parseFunction();
        } else if (peek().getType() == TokenType.NUMBER) {
            result = new NumberNode(Operand.of(eat().getValue()));
        } else if (peek().getType() == TokenType.VARIABLE) {
            result = new VariableNode(eat().getValue());
        } else {
            throw new SyntaxException("Invalid expression");
        }

        while (matchOperator("^")) {
            ExpressionNode exponent = parseFactor();
            result = new BinaryOpNode('^', result, exponent);
        }

        return isNegative ? new UnaryOpNode('-', result) : result;
    }

    private ExpressionNode parseFunction() {
        String functionName = eat().getValue();
        if (!match(TokenType.PAREN_OPEN)) {
            throw new SyntaxException("Expected '(' after function: " + functionName);
        }

        List<ExpressionNode> args = new ArrayList<>();
        if (!match(TokenType.PAREN_CLOSE)) {
            do {
                args.add(parseExpression());
            } while (match(TokenType.COMMA));

            if (!match(TokenType.PAREN_CLOSE)) {
                throw new SyntaxException("Missing closing parenthesis in function: " + functionName);
            }
        }

        return new FunctionNode(functionName, args);
    }
}
