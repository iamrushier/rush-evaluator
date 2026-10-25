package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VariableEvaluationTest {

    @Test
    void testBasicVariableArithmetic() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("x", 10);
        vars.put("y", 5);

        assertEquals("15", RushEvaluator.evaluate("x + y", vars));
        assertEquals("5", RushEvaluator.evaluate("x - y", vars));
        assertEquals("50", RushEvaluator.evaluate("x * y", vars));
        assertEquals("2", RushEvaluator.evaluate("x / y", vars));
        assertEquals("100000", RushEvaluator.evaluate("x ^ y", vars));
    }

    @Test
    void testImplicitMultiplicationWithVariables() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("x", 4);
        vars.put("y", 3);

        // 2x -> 2 * x
        assertEquals("8", RushEvaluator.evaluate("2x", vars));
        // 2x + 3y -> 2*4 + 3*3 = 17
        assertEquals("17", RushEvaluator.evaluate("2x + 3y", vars));
        // x(y + 1) -> 4 * (3 + 1) = 16
        assertEquals("16", RushEvaluator.evaluate("x(y + 1)", vars));
        // (x)(y) -> 4 * 3 = 12
        assertEquals("12", RushEvaluator.evaluate("(x)(y)", vars));
        // (x + 1)(y - 1) -> (4 + 1) * (3 - 1) = 10
        assertEquals("10", RushEvaluator.evaluate("(x + 1)(y - 1)", vars));
        // x^2 + 2x + 1 -> 16 + 8 + 1 = 25
        assertEquals("25", RushEvaluator.evaluate("x^2 + 2x + 1", vars));
    }

    @Test
    void testMultiLetterAndUnderscoreVariables() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("width", 20);
        vars.put("height", 10);
        vars.put("tax_rate", "0.05");

        assertEquals("200", RushEvaluator.evaluate("width * height", vars));
        assertEquals("10", RushEvaluator.evaluate("width * height * tax_rate", vars));
    }

    @Test
    void testVariableTypes() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("intVal", 42);
        vars.put("longVal", 1000000000000L);
        vars.put("doubleVal", 2.5);
        vars.put("bigDecVal", new BigDecimal("100.55"));
        vars.put("strVal", "10");

        assertEquals("52", RushEvaluator.evaluate("intVal + strVal", vars));
        assertEquals("105", RushEvaluator.evaluate("doubleVal * intVal", vars));
        assertEquals("110.55", RushEvaluator.evaluate("bigDecVal + strVal", vars));
    }

    @Test
    void testVariablesWithTrigonometryAndAngleModes() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("theta", 30);

        // Degrees (default)
        assertEquals("0.5", RushEvaluator.evaluate("sin(theta)", vars));
        assertEquals("0.5", RushEvaluator.evaluate("sin(theta)", vars, AngleMode.DEGREES));

        // Radians
        Map<String, Object> radVars = new HashMap<>();
        radVars.put("theta", Math.PI / 6);
        assertEquals("0.5", RushEvaluator.evaluate("sin(theta)", radVars, AngleMode.RADIANS));
    }

    @Test
    void testVariablesWithNumericOutputs() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("a", new BigDecimal("12.5"));
        vars.put("b", new BigDecimal("2.5"));

        BigDecimal bigResult = RushEvaluator.evaluateToBigDecimal("a * b", vars);
        assertEquals(0, new BigDecimal("31.25").compareTo(bigResult));

        double doubleResult = RushEvaluator.evaluateToDouble("a / b", vars);
        assertEquals(5.0, doubleResult);
    }

    @Test
    void testEvaluationConfigWithVariables() {
        EvaluationConfig config = EvaluationConfig.degrees()
                .withVariable("x", 10)
                .withVariable("y", 20);

        assertTrue(config.hasVariable("x"));
        assertTrue(config.hasVariable("y"));
        assertFalse(config.hasVariable("z"));

        assertEquals("30", RushEvaluator.evaluate("x + y", config));
    }

    @Test
    void testUndefinedVariableThrowsSyntaxException() {
        Map<String, Object> vars = Collections.singletonMap("x", 5);

        SyntaxException ex = assertThrows(SyntaxException.class, () ->
                RushEvaluator.evaluate("x + y", vars));
        assertTrue(ex.getMessage().contains("Undefined variable: y"));
    }

    @Test
    void testNullVariableValueThrowsException() {
        Map<String, Object> vars = new HashMap<>();
        vars.put("x", null);

        assertThrows(IllegalArgumentException.class, () ->
                RushEvaluator.evaluate("x + 1", vars));
    }
}
