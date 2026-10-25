package io.github.iamrushier.evaluator.config;

import io.github.iamrushier.evaluator.util.Operand;
import java.math.MathContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * {@code EvaluationConfig} encapsulates runtime settings for mathematical expression evaluation.
 * Instances of this class are immutable and thread-safe.
 */
public final class EvaluationConfig {

    private static final EvaluationConfig DEFAULT = new EvaluationConfig(AngleMode.DEGREES, MathContext.DECIMAL128, Collections.emptyMap());
    private static final EvaluationConfig RADIANS = new EvaluationConfig(AngleMode.RADIANS, MathContext.DECIMAL128, Collections.emptyMap());

    private final AngleMode angleMode;
    private final MathContext mathContext;
    private final Map<String, Operand> variables;

    /**
     * Constructs an {@code EvaluationConfig} with the specified angle mode, default {@link MathContext#DECIMAL128},
     * and no variable bindings.
     *
     * @param angleMode the unit of measurement for angles (cannot be null)
     */
    public EvaluationConfig(AngleMode angleMode) {
        this(angleMode, MathContext.DECIMAL128, Collections.emptyMap());
    }

    /**
     * Constructs an {@code EvaluationConfig} with the specified angle mode, math context, and no variable bindings.
     *
     * @param angleMode the unit of measurement for angles (cannot be null)
     * @param mathContext the MathContext for precision and rounding (cannot be null)
     */
    public EvaluationConfig(AngleMode angleMode, MathContext mathContext) {
        this(angleMode, mathContext, Collections.emptyMap());
    }

    /**
     * Constructs an {@code EvaluationConfig} with the specified angle mode, math context, and variable bindings.
     *
     * @param angleMode the unit of measurement for angles (cannot be null)
     * @param mathContext the MathContext for precision and rounding (cannot be null)
     * @param variables map of variable names to their values (can be null or empty)
     */
    public EvaluationConfig(AngleMode angleMode, MathContext mathContext, Map<String, ?> variables) {
        this.angleMode = Objects.requireNonNull(angleMode, "angleMode must not be null");
        this.mathContext = Objects.requireNonNull(mathContext, "mathContext must not be null");
        this.variables = convertVariables(variables);
    }

    private static Map<String, Operand> convertVariables(Map<String, ?> variables) {
        if (variables == null || variables.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Operand> map = new HashMap<>(variables.size());
        for (Map.Entry<String, ?> entry : variables.entrySet()) {
            if (entry.getKey() != null) {
                map.put(entry.getKey(), Operand.of(entry.getValue()));
            }
        }
        return Collections.unmodifiableMap(map);
    }

    /**
     * Returns the default configuration using {@link AngleMode#DEGREES} and {@link MathContext#DECIMAL128}.
     *
     * @return the default EvaluationConfig instance
     */
    public static EvaluationConfig defaultConfig() {
        return DEFAULT;
    }

    /**
     * Returns a configuration preset using {@link AngleMode#DEGREES}.
     *
     * @return an EvaluationConfig instance with degrees angle mode
     */
    public static EvaluationConfig degrees() {
        return DEFAULT;
    }

    /**
     * Returns a configuration preset using {@link AngleMode#RADIANS}.
     *
     * @return an EvaluationConfig instance with radians angle mode
     */
    public static EvaluationConfig radians() {
        return RADIANS;
    }

    /**
     * Returns the active angle mode.
     *
     * @return the {@link AngleMode}
     */
    public AngleMode getAngleMode() {
        return angleMode;
    }

    /**
     * Returns the active math context.
     *
     * @return the {@link MathContext}
     */
    public MathContext getMathContext() {
        return mathContext;
    }

    /**
     * Returns an unmodifiable map of the variable bindings.
     *
     * @return map of variable names to their {@link Operand} values
     */
    public Map<String, Operand> getVariables() {
        return variables;
    }

    /**
     * Checks if a variable with the given name is bound in this configuration.
     *
     * @param name the variable name
     * @return {@code true} if bound, {@code false} otherwise
     */
    public boolean hasVariable(String name) {
        return name != null && variables.containsKey(name);
    }

    /**
     * Retrieves the {@link Operand} value of a bound variable.
     *
     * @param name the variable name
     * @return the {@link Operand} value, or {@code null} if undefined
     */
    public Operand getVariable(String name) {
        return variables.get(name);
    }

    /**
     * Returns a copy of this configuration with the specified angle mode.
     *
     * @param mode the new angle mode
     * @return a new EvaluationConfig instance
     */
    public EvaluationConfig withAngleMode(AngleMode mode) {
        return new EvaluationConfig(mode, this.mathContext, this.variables);
    }

    /**
     * Returns a copy of this configuration with the specified math context.
     *
     * @param context the new math context
     * @return a new EvaluationConfig instance
     */
    public EvaluationConfig withMathContext(MathContext context) {
        return new EvaluationConfig(this.angleMode, context, this.variables);
    }

    /**
     * Returns a copy of this configuration with an additional or replaced variable binding.
     *
     * @param name the variable name (cannot be null)
     * @param value the variable value (cannot be null)
     * @return a new EvaluationConfig instance
     */
    public EvaluationConfig withVariable(String name, Object value) {
        Objects.requireNonNull(name, "Variable name must not be null");
        Map<String, Operand> newVars = new HashMap<>(this.variables);
        newVars.put(name, Operand.of(value));
        return new EvaluationConfig(this.angleMode, this.mathContext, newVars);
    }

    /**
     * Returns a copy of this configuration with all specified variable bindings added or replaced.
     *
     * @param vars map of variable names to values
     * @return a new EvaluationConfig instance
     */
    public EvaluationConfig withVariables(Map<String, ?> vars) {
        if (vars == null || vars.isEmpty()) {
            return this;
        }
        Map<String, Operand> newVars = new HashMap<>(this.variables);
        for (Map.Entry<String, ?> entry : vars.entrySet()) {
            if (entry.getKey() != null) {
                newVars.put(entry.getKey(), Operand.of(entry.getValue()));
            }
        }
        return new EvaluationConfig(this.angleMode, this.mathContext, newVars);
    }
}
