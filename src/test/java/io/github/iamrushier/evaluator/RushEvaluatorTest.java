package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.DomainException;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.util.ExpressionValidator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class RushEvaluatorTest {
    @Test
    void testValidateExpression() {
        assertEquals("2+3", ExpressionValidator.validateExpression("2 + 3"));
        assertEquals("(2)*(2)*(2)*((2))", ExpressionValidator.validateExpression("(2)(2)(2)((2))"));
        assertEquals("2*3", ExpressionValidator.validateExpression("2×3"));
        assertEquals("10*(1/100)+5", ExpressionValidator.validateExpression("10% + 5"));
        assertEquals("(2)*(3)", ExpressionValidator.validateExpression("(2)(3)"));
        assertEquals("2*π", ExpressionValidator.validateExpression("2π"));
        assertEquals("sin(30)*2", ExpressionValidator.validateExpression("sin(30)2"));
    }

    @Test
    void testEvaluate() {
        // Valid expressions (defaults to DEGREES)
        assertEquals("5", RushEvaluator.evaluate("2+3"));
        assertEquals("5", RushEvaluator.evaluate("10-5"));
        assertEquals("6", RushEvaluator.evaluate("2*3"));
        assertEquals("5", RushEvaluator.evaluate("10/2"));
        assertEquals("8", RushEvaluator.evaluate("2^3"));
        assertEquals("0.5", RushEvaluator.evaluate("sin(30)"));
        assertEquals("-0.5", RushEvaluator.evaluate("cos(120)"));
        assertEquals("1", RushEvaluator.evaluate("tan(45)"));
        assertEquals("2", RushEvaluator.evaluate("log(100)"));
        assertEquals("1", RushEvaluator.evaluate("ln(e)"));
        assertEquals("30", RushEvaluator.evaluate("asin(0.5)"));
        assertEquals("60", RushEvaluator.evaluate("acos(0.5)"));
        assertEquals("45", RushEvaluator.evaluate("atan(1)"));
        assertEquals("3.14159265358979323846264338327950288419716939937510", RushEvaluator.evaluate("π"));
        assertEquals("2.71828182845904523536028747135266249775724709369995", RushEvaluator.evaluate("e"));
        assertEquals("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000", RushEvaluator.evaluate("10^100"));
        assertEquals("∞", RushEvaluator.evaluate("1/0"));
        assertEquals("0", RushEvaluator.evaluate("1/tan(90)"));
        // Invalid expressions
        assertThrows(DomainException.class, () -> RushEvaluator.evaluate("0/0"));
        assertThrows(SyntaxException.class, () -> RushEvaluator.evaluate("2+"));
    }

    @Test
    void testEvaluate_radiansMode() {
        assertEquals("0", RushEvaluator.evaluate("sin(π)", AngleMode.RADIANS));
        assertEquals("-1", RushEvaluator.evaluate("cos(π)", AngleMode.RADIANS));
        assertEquals("1", RushEvaluator.evaluate("tan(π / 4)", AngleMode.RADIANS));
        assertEquals("0.5", RushEvaluator.evaluate("sin(π / 6)", AngleMode.RADIANS));
    }

    @Test
    void testEvaluate_customConfig() {
        EvaluationConfig config = EvaluationConfig.radians();
        assertEquals("0", RushEvaluator.evaluate("sin(π)", config));
        assertEquals("-1", RushEvaluator.evaluate("cos(π)", config));
    }

    @Test
    void testEvaluateToBigDecimal() {
        assertEquals(new BigDecimal("5"), RushEvaluator.evaluateToBigDecimal("2 + 3"));
        assertEquals(new BigDecimal("6"), RushEvaluator.evaluateToBigDecimal("2 * 3"));
        assertEquals(0, new BigDecimal("8").compareTo(RushEvaluator.evaluateToBigDecimal("2^3")));
        assertEquals(0, new BigDecimal("0.5").compareTo(RushEvaluator.evaluateToBigDecimal("sin(30)")));
        assertEquals(0, new BigDecimal("0").compareTo(RushEvaluator.evaluateToBigDecimal("sin(π)", AngleMode.RADIANS)));

        // Non-finite results throw DomainException
        assertThrows(DomainException.class, () -> RushEvaluator.evaluateToBigDecimal("1 / 0"));
    }

    @Test
    void testEvaluateToDouble() {
        assertEquals(5.0, RushEvaluator.evaluateToDouble("2 + 3"));
        assertEquals(0.5, RushEvaluator.evaluateToDouble("sin(30)"));
        assertEquals(0.0, RushEvaluator.evaluateToDouble("sin(π)", AngleMode.RADIANS), 1e-9);
        assertEquals(Double.POSITIVE_INFINITY, RushEvaluator.evaluateToDouble("1 / 0"));

        assertThrows(DomainException.class, () -> RushEvaluator.evaluateToDouble("0 / 0"));
    }
}
