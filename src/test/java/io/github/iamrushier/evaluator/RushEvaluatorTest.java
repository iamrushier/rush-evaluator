package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.exception.DomainException;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.util.ExpressionValidator;
import org.junit.jupiter.api.Test;

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
        // Valid expressions
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
}
