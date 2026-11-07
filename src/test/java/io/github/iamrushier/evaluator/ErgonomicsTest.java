package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.exception.SyntaxException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ErgonomicsTest {

    @Test
    void testIsValidBasicExpressions() {
        assertTrue(RushEvaluator.isValid("2 + 3"));
        assertTrue(RushEvaluator.isValid("sin(30) + 2^3"));
        assertTrue(RushEvaluator.isValid("min(10, 20) + round(3.14159, 2)"));

        assertFalse(RushEvaluator.isValid("2 +"));
        assertFalse(RushEvaluator.isValid("2++3"));
        assertFalse(RushEvaluator.isValid("sin("));
        assertFalse(RushEvaluator.isValid("0 / 0")); // Undefined domain error
        assertFalse(RushEvaluator.isValid(null));
        assertFalse(RushEvaluator.isValid("   "));
    }

    @Test
    void testIsValidWithVariables() {
        // Without variables, an expression containing free variables is not evaluable
        assertFalse(RushEvaluator.isValid("x + y"));

        Map<String, Object> vars = new HashMap<>();
        vars.put("x", 10);
        vars.put("y", 20);
        assertTrue(RushEvaluator.isValid("x + y", vars));

        Map<String, Object> partialVars = Collections.singletonMap("x", 10);
        assertFalse(RushEvaluator.isValid("x + y", partialVars));
    }

    @Test
    void testIsSyntaxValid() {
        // isSyntaxValid only checks syntax structure, regardless of variable presence
        assertTrue(RushEvaluator.isSyntaxValid("x + y"));
        assertTrue(RushEvaluator.isSyntaxValid("2x + 3y^2"));
        assertTrue(RushEvaluator.isSyntaxValid("2 + 3"));

        assertFalse(RushEvaluator.isSyntaxValid("x + "));
        assertFalse(RushEvaluator.isSyntaxValid("2++3"));
        assertFalse(RushEvaluator.isSyntaxValid("(x + y"));
        assertFalse(RushEvaluator.isSyntaxValid(null));
        assertFalse(RushEvaluator.isSyntaxValid(""));
    }

    @Test
    void testSyntaxExceptionPositionTracking() {
        SyntaxException ex1 = assertThrows(SyntaxException.class, () -> RushEvaluator.evaluate("2 +"));
        assertTrue(ex1.getPosition() >= 0);
        assertTrue(ex1.getMessage().contains("at position"));

        SyntaxException ex2 = assertThrows(SyntaxException.class, () -> RushEvaluator.evaluate("2++3"));
        assertTrue(ex2.getPosition() >= 0);
        assertTrue(ex2.getMessage().contains("at position"));

        SyntaxException ex3 = assertThrows(SyntaxException.class, () -> RushEvaluator.evaluate("(1+2"));
        assertTrue(ex3.getPosition() >= 0);
        assertTrue(ex3.getMessage().contains("at position"));
    }
}
