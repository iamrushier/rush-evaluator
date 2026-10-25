package io.github.iamrushier.evaluator.expression;

import io.github.iamrushier.evaluator.ast.ExpressionNode;
import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.DomainException;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.util.Operand;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * {@code CompiledExpression} represents a compiled, immutable, and thread-safe mathematical expression AST.
 * It can be evaluated repeatedly with varying variable bindings at maximum performance without re-parsing.
 */
public final class CompiledExpression {

    private final String expression;
    private final ExpressionNode rootNode;
    private final EvaluationConfig baseConfig;
    private final Set<String> variableNames;

    /**
     * Constructs a {@code CompiledExpression}.
     *
     * @param expression the original raw expression string
     * @param rootNode the optimized root {@link ExpressionNode} of the AST
     * @param baseConfig the configuration used during compilation
     */
    public CompiledExpression(String expression, ExpressionNode rootNode, EvaluationConfig baseConfig) {
        this.expression = Objects.requireNonNull(expression, "expression must not be null");
        this.rootNode = Objects.requireNonNull(rootNode, "rootNode must not be null");
        this.baseConfig = baseConfig != null ? baseConfig : EvaluationConfig.defaultConfig();

        Set<String> vars = new HashSet<>();
        rootNode.collectVariables(vars);
        this.variableNames = Collections.unmodifiableSet(vars);
    }

    /**
     * Returns the original expression string.
     *
     * @return the raw expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * Returns the root AST node of this compiled expression.
     *
     * @return the root {@link ExpressionNode}
     */
    public ExpressionNode getRootNode() {
        return rootNode;
    }

    /**
     * Returns an unmodifiable set of all variable names referenced in this expression.
     *
     * @return set of variable identifiers
     */
    public Set<String> getVariableNames() {
        return variableNames;
    }

    /**
     * Indicates whether this expression evaluates to a constant with no free variables.
     *
     * @return {@code true} if constant, {@code false} if dependent on variables
     */
    public boolean isConstant() {
        return rootNode.isConstant();
    }

    /**
     * Validates that all required variables are present in the provided map.
     *
     * @param variables map of variable bindings
     * @throws SyntaxException if any required variable is missing
     */
    public void validateVariables(Map<String, ?> variables) {
        for (String var : variableNames) {
            if (variables == null || !variables.containsKey(var)) {
                throw new SyntaxException("Undefined variable: " + var);
            }
        }
    }

    /**
     * Evaluates this expression directly to an {@link Operand}.
     *
     * @param config the active evaluation configuration
     * @return the evaluated {@link Operand}
     */
    public Operand evaluateToOperand(EvaluationConfig config) {
        if (config == null) {
            config = baseConfig;
        }
        return rootNode.evaluate(config);
    }

    // ==================== evaluate (String output) ====================

    /**
     * Evaluates this expression using its base configuration.
     *
     * @return the evaluated result formatted as a string
     */
    public String evaluate() {
        return evaluate(baseConfig);
    }

    /**
     * Evaluates this expression with variable bindings using its base configuration.
     *
     * @param variables map of variable names to values
     * @return the evaluated result formatted as a string
     */
    public String evaluate(Map<String, ?> variables) {
        return evaluate(baseConfig.withVariables(variables));
    }

    /**
     * Evaluates this expression with variable bindings and the specified {@link AngleMode}.
     *
     * @param variables map of variable names to values
     * @param angleMode the angle mode to use
     * @return the evaluated result formatted as a string
     */
    public String evaluate(Map<String, ?> variables, AngleMode angleMode) {
        return evaluate(baseConfig.withAngleMode(angleMode).withVariables(variables));
    }

    /**
     * Evaluates this expression using the provided {@link EvaluationConfig}.
     *
     * @param config the evaluation configuration
     * @return the evaluated result formatted as a string
     */
    public String evaluate(EvaluationConfig config) {
        Operand result = evaluateToOperand(config);
        String str = result.toString();
        if (str.equals("NaN")) {
            throw new DomainException("Undefined");
        }
        return str;
    }

    // ==================== evaluateToBigDecimal ====================

    /**
     * Evaluates this expression directly to a {@link BigDecimal} using its base configuration.
     *
     * @return the evaluated result as a {@link BigDecimal}
     */
    public BigDecimal evaluateToBigDecimal() {
        return evaluateToBigDecimal(baseConfig);
    }

    /**
     * Evaluates this expression with variables to a {@link BigDecimal}.
     *
     * @param variables map of variable names to values
     * @return the evaluated result as a {@link BigDecimal}
     */
    public BigDecimal evaluateToBigDecimal(Map<String, ?> variables) {
        return evaluateToBigDecimal(baseConfig.withVariables(variables));
    }

    /**
     * Evaluates this expression with variables and the specified {@link AngleMode} to a {@link BigDecimal}.
     *
     * @param variables map of variable names to values
     * @param angleMode the angle mode to use
     * @return the evaluated result as a {@link BigDecimal}
     */
    public BigDecimal evaluateToBigDecimal(Map<String, ?> variables, AngleMode angleMode) {
        return evaluateToBigDecimal(baseConfig.withAngleMode(angleMode).withVariables(variables));
    }

    /**
     * Evaluates this expression using the provided {@link EvaluationConfig} to a {@link BigDecimal}.
     *
     * @param config the evaluation configuration
     * @return the evaluated result as a {@link BigDecimal}
     */
    public BigDecimal evaluateToBigDecimal(EvaluationConfig config) {
        Operand operand = evaluateToOperand(config);
        if (operand.isSpecial()) {
            throw new DomainException("Result is non-finite (" + operand + ") and cannot be converted to BigDecimal");
        }
        return operand.getAsBigDecimal();
    }

    // ==================== evaluateToDouble ====================

    /**
     * Evaluates this expression directly to a primitive {@code double} using its base configuration.
     *
     * @return the evaluated result as a double
     */
    public double evaluateToDouble() {
        return evaluateToDouble(baseConfig);
    }

    /**
     * Evaluates this expression with variables to a primitive {@code double}.
     *
     * @param variables map of variable names to values
     * @return the evaluated result as a double
     */
    public double evaluateToDouble(Map<String, ?> variables) {
        return evaluateToDouble(baseConfig.withVariables(variables));
    }

    /**
     * Evaluates this expression with variables and the specified {@link AngleMode} to a primitive {@code double}.
     *
     * @param variables map of variable names to values
     * @param angleMode the angle mode to use
     * @return the evaluated result as a double
     */
    public double evaluateToDouble(Map<String, ?> variables, AngleMode angleMode) {
        return evaluateToDouble(baseConfig.withAngleMode(angleMode).withVariables(variables));
    }

    /**
     * Evaluates this expression using the provided {@link EvaluationConfig} to a primitive {@code double}.
     *
     * @param config the evaluation configuration
     * @return the evaluated result as a double
     */
    public double evaluateToDouble(EvaluationConfig config) {
        Operand operand = evaluateToOperand(config);
        double val = operand.getAsDouble();
        if (Double.isNaN(val)) {
            throw new DomainException("Undefined");
        }
        return val;
    }
}
