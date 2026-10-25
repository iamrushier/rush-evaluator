package io.github.iamrushier.evaluator.parser;

import io.github.iamrushier.evaluator.ast.ExpressionNode;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.util.Operand;

/**
 * {@code ExpressionParser} is responsible for parsing and evaluating mathematical expressions.
 * It coordinates tokenization via {@link Lexer} and AST evaluation via {@link AstParser}.
 */
public class ExpressionParser {
    private final EvaluationConfig config;

    /**
     * Constructs an {@code ExpressionParser} with the default configuration.
     */
    public ExpressionParser() {
        this(EvaluationConfig.defaultConfig());
    }

    /**
     * Constructs an {@code ExpressionParser} with a custom {@link EvaluationConfig}.
     *
     * @param config the configuration to use for parsing and evaluation
     */
    public ExpressionParser(EvaluationConfig config) {
        this.config = config != null ? config : EvaluationConfig.defaultConfig();
    }

    /**
     * Parses and evaluates the given mathematical expression string into an {@link Operand}.
     *
     * @param expression The mathematical expression string to parse.
     * @return The result of the parsed expression as an {@link Operand}.
     */
    public Operand parseToOperand(String expression) {
        Lexer lexer = new Lexer(expression);
        AstParser parser = new AstParser(lexer.tokenize(), config);
        ExpressionNode root = parser.parse();
        return root.evaluate(config);
    }

    /**
     * Parses and evaluates the given mathematical expression.
     *
     * @param expression The mathematical expression string to parse.
     * @return The result of the parsed expression as a String.
     */
    public String parse(String expression) {
        return parseToOperand(expression).toString();
    }
}
