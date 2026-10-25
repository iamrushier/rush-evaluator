package io.github.iamrushier.evaluator.ast;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.util.CalculationEngine;
import io.github.iamrushier.evaluator.util.Operand;
import java.util.Objects;
import java.util.Set;

/**
 * {@code BinaryOpNode} represents a binary operation (+, -, *, /, ^) between two child nodes.
 */
public class BinaryOpNode implements ExpressionNode {

    private final char operator;
    private final ExpressionNode left;
    private final ExpressionNode right;

    /**
     * Constructs a {@code BinaryOpNode}.
     *
     * @param operator the binary operator ('+', '-', '*', '/', '^')
     * @param left the left child node (cannot be null)
     * @param right the right child node (cannot be null)
     */
    public BinaryOpNode(char operator, ExpressionNode left, ExpressionNode right) {
        this.operator = operator;
        this.left = Objects.requireNonNull(left, "Left child must not be null");
        this.right = Objects.requireNonNull(right, "Right child must not be null");
    }

    /**
     * Returns the binary operator character.
     *
     * @return the operator
     */
    public char getOperator() {
        return operator;
    }

    /**
     * Returns the left child node.
     *
     * @return the left node
     */
    public ExpressionNode getLeft() {
        return left;
    }

    /**
     * Returns the right child node.
     *
     * @return the right node
     */
    public ExpressionNode getRight() {
        return right;
    }

    @Override
    public Operand evaluate(EvaluationConfig config) {
        Operand leftVal = left.evaluate(config);
        Operand rightVal = right.evaluate(config);
        return CalculationEngine.calculate(leftVal, rightVal, operator);
    }

    @Override
    public void collectVariables(Set<String> variables) {
        left.collectVariables(variables);
        right.collectVariables(variables);
    }

    @Override
    public ExpressionNode foldConstants(EvaluationConfig config) {
        ExpressionNode foldedLeft = left.foldConstants(config);
        ExpressionNode foldedRight = right.foldConstants(config);

        if (foldedLeft.isConstant() && foldedRight.isConstant()) {
            BinaryOpNode temp = new BinaryOpNode(operator, foldedLeft, foldedRight);
            return new NumberNode(temp.evaluate(config));
        }

        return new BinaryOpNode(operator, foldedLeft, foldedRight);
    }

    @Override
    public boolean isConstant() {
        return left.isConstant() && right.isConstant();
    }

    @Override
    public String toString() {
        return "(" + left + " " + operator + " " + right + ")";
    }
}
