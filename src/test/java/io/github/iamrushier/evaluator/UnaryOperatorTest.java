package io.github.iamrushier.evaluator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnaryOperatorTest {

    @Test
    void testSimpleNegativeNumber() {
        assertEquals("-2", RushEvaluator.evaluate("-2"));
    }

    @Test
    void testNegativeNumberInExpression() {
        assertEquals("2", RushEvaluator.evaluate("5+(-3)"));
        assertEquals("8", RushEvaluator.evaluate("5 - (-3)"));
    }

    @Test
    void testNegativeFunction() {
        assertEquals("-0.5", RushEvaluator.evaluate("-sin(30)"));
    }

    @Test
    void testNegativeConstant() {
        String pi = "3.14159265358979323846264338327950288419716939937510";
        assertEquals("-" + pi, RushEvaluator.evaluate("-π"));
    }

    @Test
    void testInvalidDoubleNegative_ThrowsException() {
        // These should be rejected as syntax errors in a standard calculator
        assertThrows(RuntimeException.class, () -> RushEvaluator.evaluate("--2"));
        assertThrows(RuntimeException.class, () -> RushEvaluator.evaluate("5 - -3"));
        assertThrows(RuntimeException.class, () -> RushEvaluator.evaluate("5 * -3"));
    }
}
