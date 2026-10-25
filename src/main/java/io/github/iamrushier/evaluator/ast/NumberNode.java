package io.github.iamrushier.evaluator.ast;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.util.Operand;
import java.util.Objects;
import java.util.Set;

/**
 * {@code NumberNode} represents a literal numeric or constant operand in the AST.
 */
public class NumberNode implements ExpressionNode {

    private final Operand value;

    /**
     * Constructs a {@code NumberNode} wrapping the specified {@link Operand}.
     *
     * @param value the numeric value (cannot be null)
     */
    public NumberNode(Operand value) {
        this.value = Objects.requireNonNull(value, "Operand value must not be null");
    }

    /**
     * Returns the numeric value wrapped by this node.
     *
     * @return the {@link Operand} value
     */
    public Operand getValue() {
        return value;
    }

    @Override
    public Operand evaluate(EvaluationConfig config) {
        return value;
    }

    @Override
    public void collectVariables(Set<String> variables) {
        // Numbers reference no variables
    }

    @Override
    public ExpressionNode foldConstants(EvaluationConfig config) {
        return this;
    }

    @Override
    public boolean isConstant() {
        return true;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
