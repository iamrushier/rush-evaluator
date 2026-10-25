package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.ast.NumberNode;
import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.expression.CompiledExpression;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CompiledExpressionTest {

    @Test
    void testCompiledExpressionReuseInLoop() {
        CompiledExpression expr = RushEvaluator.compile("2x + 3y");
        assertEquals("2x + 3y", expr.getExpression());
        assertFalse(expr.isConstant());

        Map<String, Object> vars = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            vars.put("x", i);
            vars.put("y", i * 2);
            // 2(i) + 3(2i) = 8i
            assertEquals(String.valueOf(8 * i), expr.evaluate(vars));
        }
    }

    @Test
    void testVariableIntrospection() {
        CompiledExpression expr = RushEvaluator.compile("a * x^2 + b * x + c");
        Set<String> vars = expr.getVariableNames();

        assertEquals(4, vars.size());
        assertTrue(vars.contains("a"));
        assertTrue(vars.contains("b"));
        assertTrue(vars.contains("c"));
        assertTrue(vars.contains("x"));
    }

    @Test
    void testIsConstant() {
        CompiledExpression constExpr = RushEvaluator.compile("2 * π + sin(30)");
        assertTrue(constExpr.isConstant());
        assertTrue(constExpr.getRootNode() instanceof NumberNode);

        CompiledExpression varExpr = RushEvaluator.compile("2 * π * radius");
        assertFalse(varExpr.isConstant());
    }

    @Test
    void testValidateVariables() {
        CompiledExpression expr = RushEvaluator.compile("width * height");

        Map<String, Object> validVars = new HashMap<>();
        validVars.put("width", 10);
        validVars.put("height", 20);
        assertDoesNotThrow(() -> expr.validateVariables(validVars));

        Map<String, Object> missingVars = new HashMap<>();
        missingVars.put("width", 10);
        SyntaxException ex = assertThrows(SyntaxException.class, () -> expr.validateVariables(missingVars));
        assertTrue(ex.getMessage().contains("Undefined variable: height"));
    }

    @Test
    void testDirectNumericOutputs() {
        CompiledExpression expr = RushEvaluator.compile("10x / y");
        Map<String, Object> vars = new HashMap<>();
        vars.put("x", 5);
        vars.put("y", 2);

        BigDecimal bigResult = expr.evaluateToBigDecimal(vars);
        assertEquals(new BigDecimal("25"), bigResult);

        double doubleResult = expr.evaluateToDouble(vars);
        assertEquals(25.0, doubleResult);
    }

    @Test
    void testAngleModeInCompiledExpression() {
        CompiledExpression expr = RushEvaluator.compile("sin(theta)");
        Map<String, Object> vars = new HashMap<>();

        vars.put("theta", 30);
        assertEquals("0.5", expr.evaluate(vars));

        vars.put("theta", Math.PI / 6);
        assertEquals("0.5", expr.evaluate(vars, AngleMode.RADIANS));
    }
}
