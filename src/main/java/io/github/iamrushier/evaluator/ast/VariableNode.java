package io.github.iamrushier.evaluator.ast;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.util.Operand;
import java.util.Objects;
import java.util.Set;

/**
 * {@code VariableNode} represents a dynamic variable reference in the AST.
 */
public class VariableNode implements ExpressionNode {

    private final String name;

    /**
     * Constructs a {@code VariableNode} for the specified variable name.
     *
     * @param name the identifier name of the variable (cannot be null)
     */
    public VariableNode(String name) {
        this.name = Objects.requireNonNull(name, "Variable name must not be null");
    }

    /**
     * Returns the variable name.
     *
     * @return the variable name
     */
    public String getName() {
        return name;
    }

    @Override
    public Operand evaluate(EvaluationConfig config) {
        if (config == null || !config.hasVariable(name)) {
            throw new SyntaxException("Undefined variable: " + name);
        }
        return config.getVariable(name);
    }

    @Override
    public void collectVariables(Set<String> variables) {
        if (variables != null) {
            variables.add(name);
        }
    }

    @Override
    public ExpressionNode foldConstants(EvaluationConfig config) {
        return this;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
