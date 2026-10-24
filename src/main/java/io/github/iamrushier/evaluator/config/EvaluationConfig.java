package io.github.iamrushier.evaluator.config;

import java.math.MathContext;
import java.util.Objects;

/**
 * {@code EvaluationConfig} encapsulates runtime settings for mathematical expression evaluation.
 * Instances of this class are immutable and thread-safe.
 */
public final class EvaluationConfig {

    private static final EvaluationConfig DEFAULT = new EvaluationConfig(AngleMode.DEGREES, MathContext.DECIMAL128);
    private static final EvaluationConfig RADIANS = new EvaluationConfig(AngleMode.RADIANS, MathContext.DECIMAL128);

    private final AngleMode angleMode;
    private final MathContext mathContext;

    /**
     * Constructs an {@code EvaluationConfig} with the specified angle mode and default {@link MathContext#DECIMAL128}.
     *
     * @param angleMode the unit of measurement for angles (cannot be null)
     */
    public EvaluationConfig(AngleMode angleMode) {
        this(angleMode, MathContext.DECIMAL128);
    }

    /**
     * Constructs an {@code EvaluationConfig} with the specified angle mode and math context.
     *
     * @param angleMode the unit of measurement for angles (cannot be null)
     * @param mathContext the MathContext for precision and rounding (cannot be null)
     */
    public EvaluationConfig(AngleMode angleMode, MathContext mathContext) {
        this.angleMode = Objects.requireNonNull(angleMode, "angleMode must not be null");
        this.mathContext = Objects.requireNonNull(mathContext, "mathContext must not be null");
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
     * Returns a copy of this configuration with the specified angle mode.
     *
     * @param mode the new angle mode
     * @return a new EvaluationConfig instance
     */
    public EvaluationConfig withAngleMode(AngleMode mode) {
        return new EvaluationConfig(mode, this.mathContext);
    }

    /**
     * Returns a copy of this configuration with the specified math context.
     *
     * @param context the new math context
     * @return a new EvaluationConfig instance
     */
    public EvaluationConfig withMathContext(MathContext context) {
        return new EvaluationConfig(this.angleMode, context);
    }
}
