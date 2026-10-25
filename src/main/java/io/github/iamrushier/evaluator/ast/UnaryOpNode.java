package io.github.iamrushier.evaluator.ast;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.util.CalculationEngine;
import io.github.iamrushier.evaluator.util.Operand;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * {@code UnaryOpNode} represents a unary operation (+, -, √, ∛) applied to a child node in the AST.
 */
public class UnaryOpNode implements ExpressionNode {

    private final char operator;
    private final ExpressionNode child;

    /**
     * Constructs a {@code UnaryOpNode}.
     *
     * @param operator the unary operator ('+', '-', '√', '∛')
     * @param child the child expression node (cannot be null)
     */
    public UnaryOpNode(char operator, ExpressionNode child) {
        this.operator = operator;
        this.child = Objects.requireNonNull(child, "Child node must not be null");
    }

    /**
     * Returns the unary operator character.
     *
     * @return the operator
     */
    public char getOperator() {
        return operator;
    }

    /**
     * Returns the child expression node.
     *
     * @return the child node
     */
    public ExpressionNode getChild() {
        return child;
    }

    @Override
    public Operand evaluate(EvaluationConfig config) {
        Operand val = child.evaluate(config);
        switch (operator) {
            case '+':
                return val;
            case '-':
                return CalculationEngine.calculate(new Operand(BigDecimal.ZERO), val, '-');
            case '\u221a': // √
                return CalculationEngine.sqrt(val);
            case '\u221b': // ∛
                return CalculationEngine.cbrt(val);
            default:
                throw new SyntaxException("Unknown unary operator: " + operator);
        }
    }

    @Override
    public void collectVariables(Set<String> variables) {
        child.collectVariables(variables);
    }

    @Override
    public ExpressionNode foldConstants(EvaluationConfig config) {
        ExpressionNode foldedChild = child.foldConstants(config);
        if (foldedChild.isConstant()) {
            UnaryOpNode temp = new UnaryOpNode(operator, foldedChild);
            return new NumberNode(temp.evaluate(config));
        }
        return new UnaryOpNode(operator, foldedChild);
    }

    @Override
    public boolean isConstant() {
        return child.isConstant();
    }

    @Override
    public String toString() {
        return operator + "(" + child + ")";
    }
}
