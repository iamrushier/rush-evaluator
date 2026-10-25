package io.github.iamrushier.evaluator.ast;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.util.Operand;
import java.util.Set;

/**
 * {@code ExpressionNode} represents a node in the abstract syntax tree (AST) of a parsed mathematical expression.
 */
public interface ExpressionNode {

    /**
     * Evaluates this AST node using the provided evaluation configuration.
     *
     * @param config the active evaluation configuration containing variable and function bindings
     * @return the evaluated {@link Operand} result
     */
    Operand evaluate(EvaluationConfig config);

    /**
     * Collects all variable names referenced within this AST subtree.
     *
     * @param variables the set into which variable identifiers should be added
     */
    void collectVariables(Set<String> variables);

    /**
     * Recursively performs constant folding on this AST subtree.
     *
     * @param config the evaluation configuration used for constant evaluation
     * @return an optimized {@link ExpressionNode}, potentially simplified to a {@link NumberNode}
     */
    ExpressionNode foldConstants(EvaluationConfig config);

    /**
     * Indicates whether this node and all of its subtrees evaluate to a constant value without depending on dynamic variables.
     *
     * @return {@code true} if constant, {@code false} if dependent on variables
     */
    boolean isConstant();
}
