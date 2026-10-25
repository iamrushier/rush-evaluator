package io.github.iamrushier.evaluator.ast;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.function.Function;
import io.github.iamrushier.evaluator.util.Operand;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * {@code FunctionNode} represents a function call with one or more argument expressions in the AST.
 */
public class FunctionNode implements ExpressionNode {

    private final String functionName;
    private final List<ExpressionNode> arguments;

    /**
     * Constructs a {@code FunctionNode}.
     *
     * @param functionName the name of the function (cannot be null)
     * @param arguments the list of argument expression nodes (cannot be null)
     */
    public FunctionNode(String functionName, List<ExpressionNode> arguments) {
        this.functionName = Objects.requireNonNull(functionName, "Function name must not be null");
        this.arguments = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(arguments, "Arguments must not be null")));
    }

    /**
     * Returns the name of the function.
     *
     * @return the function name
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Returns an unmodifiable list of argument expression nodes.
     *
     * @return the argument expressions
     */
    public List<ExpressionNode> getArguments() {
        return arguments;
    }

    @Override
    public Operand evaluate(EvaluationConfig config) {
        List<Operand> evaluatedArgs = new ArrayList<>(arguments.size());
        for (ExpressionNode arg : arguments) {
            evaluatedArgs.add(arg.evaluate(config));
        }

        Function func = config != null ? config.getFunction(functionName) : null;
        if (func == null) {
            throw new SyntaxException("Unknown function: " + functionName);
        }

        return func.apply(evaluatedArgs, config);
    }

    @Override
    public void collectVariables(Set<String> variables) {
        for (ExpressionNode arg : arguments) {
            arg.collectVariables(variables);
        }
    }

    @Override
    public ExpressionNode foldConstants(EvaluationConfig config) {
        List<ExpressionNode> foldedArgs = new ArrayList<>(arguments.size());
        boolean allConstant = true;

        for (ExpressionNode arg : arguments) {
            ExpressionNode folded = arg.foldConstants(config);
            foldedArgs.add(folded);
            if (!folded.isConstant()) {
                allConstant = false;
            }
        }

        if (allConstant && config != null && config.hasFunction(functionName)) {
            FunctionNode temp = new FunctionNode(functionName, foldedArgs);
            return new NumberNode(temp.evaluate(config));
        }

        return new FunctionNode(functionName, foldedArgs);
    }

    @Override
    public boolean isConstant() {
        for (ExpressionNode arg : arguments) {
            if (!arg.isConstant()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return functionName + "(" + arguments + ")";
    }
}
