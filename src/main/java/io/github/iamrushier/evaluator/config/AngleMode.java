package io.github.iamrushier.evaluator.config;

/**
 * {@code AngleMode} defines the unit of measurement used for angles in
 * trigonometric and inverse trigonometric calculations.
 */
public enum AngleMode {
    /**
     * Angles are measured in degrees (0° to 360° for a full circle).
     * This is the default angle mode in RushEvaluator.
     */
    DEGREES,

    /**
     * Angles are measured in radians (0 to 2π for a full circle).
     * Standard unit for mathematical and scientific applications.
     */
    RADIANS
}
