package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.exception.SyntaxException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MultiArgumentFunctionTest {

    @Test
    void testAbsFunction() {
        assertEquals("42", RushEvaluator.evaluate("abs(-42)"));
        assertEquals("42", RushEvaluator.evaluate("abs(42)"));
        assertEquals("0", RushEvaluator.evaluate("abs(0)"));
        assertEquals("3.14", RushEvaluator.evaluate("abs(-3.14)"));
    }

    @Test
    void testFloorAndCeilFunctions() {
        assertEquals("3", RushEvaluator.evaluate("floor(3.9)"));
        assertEquals("3", RushEvaluator.evaluate("floor(3.1)"));
        assertEquals("-4", RushEvaluator.evaluate("floor(-3.1)"));

        assertEquals("4", RushEvaluator.evaluate("ceil(3.1)"));
        assertEquals("4", RushEvaluator.evaluate("ceil(3.9)"));
        assertEquals("-3", RushEvaluator.evaluate("ceil(-3.9)"));
    }

    @Test
    void testRoundFunction() {
        // 1-arg round: to integer
        assertEquals("3", RushEvaluator.evaluate("round(3.14)"));
        assertEquals("4", RushEvaluator.evaluate("round(3.6)"));
        assertEquals("-3", RushEvaluator.evaluate("round(-3.14)"));

        // 2-arg round: with scale
        assertEquals("3.14", RushEvaluator.evaluate("round(3.14159, 2)"));
        assertEquals("3.142", RushEvaluator.evaluate("round(3.14159, 3)"));
        assertEquals("100", RushEvaluator.evaluate("round(100.456, 0)"));
    }

    @Test
    void testMinAndMaxFunctions() {
        assertEquals("10", RushEvaluator.evaluate("min(10, 20)"));
        assertEquals("20", RushEvaluator.evaluate("max(10, 20)"));

        // Multi-arg (3 or more)
        assertEquals("1", RushEvaluator.evaluate("min(5, 8, 2, 1, 9)"));
        assertEquals("9", RushEvaluator.evaluate("max(5, 8, 2, 1, 9)"));

        // Nested calls
        assertEquals("5", RushEvaluator.evaluate("min(max(1, 5), min(10, 8))"));
    }

    @Test
    void testMultiArgumentWithVariables() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("x", 15);
        vars.put("y", 25);
        vars.put("precision", 2);

        assertEquals("15", RushEvaluator.evaluate("min(x, y)", vars));
        assertEquals("25", RushEvaluator.evaluate("max(x, y)", vars));
        assertEquals("0.6", RushEvaluator.evaluate("round(x / y, precision)", vars));
    }

    @Test
    void testInvalidFunctionArityThrowsException() {
        assertThrows(SyntaxException.class, () -> RushEvaluator.evaluate("min(5)"));
        assertThrows(SyntaxException.class, () -> RushEvaluator.evaluate("max(5)"));
        assertThrows(SyntaxException.class, () -> RushEvaluator.evaluate("abs()"));
    }
}
