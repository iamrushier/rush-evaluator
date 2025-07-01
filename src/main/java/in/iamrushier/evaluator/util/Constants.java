package in.iamrushier.evaluator.util;

import java.math.BigDecimal;

/**
 * {@code Constants} provides commonly used mathematical constants.
 * This class cannot be instantiated.
 */
public class Constants {
    /**
     * Private constructor to prevent instantiation.
     */
    private Constants(){}
    /**
     * The mathematical constant PI (π) to a high degree of precision.
     */
    public static final BigDecimal PI = new BigDecimal("3.14159265358979323846264338327950288419716939937510");
    /**
     * The mathematical constant Euler's number (e) to a high degree of precision.
     */
    public static final BigDecimal EULER = new BigDecimal("2.71828182845904523536028747135266249775724709369995");
}
