package io.github.iamrushier.evaluator;

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
        assertEquals("5.0", RushEvaluator.evaluate("2+3"));
        assertEquals("5.0", RushEvaluator.evaluate("10-5"));
        assertEquals("6.0", RushEvaluator.evaluate("2*3"));
        assertEquals("5.0", RushEvaluator.evaluate("10/2"));
        assertEquals("8.0", RushEvaluator.evaluate("2^3"));
        assertEquals("0.5", RushEvaluator.evaluate("sin(30)"));
        assertEquals("0.5", RushEvaluator.evaluate("cos(60)"));
        assertEquals("1.0", RushEvaluator.evaluate("tan(45)"));
        assertEquals("2.0", RushEvaluator.evaluate("log(100)"));
        assertEquals("1.0", RushEvaluator.evaluate("ln(e)"));
        assertEquals("30.0", RushEvaluator.evaluate("asin(0.5)"));
        assertEquals("60.0", RushEvaluator.evaluate("acos(0.5)"));
        assertEquals("45.0", RushEvaluator.evaluate("atan(1)"));
        assertEquals("3.141592653589793", RushEvaluator.evaluate("π"));
        assertEquals("2.718281828459045", RushEvaluator.evaluate("e"));
        assertEquals("1.0E+100", RushEvaluator.evaluate("10^100"));
        assertEquals("∞", RushEvaluator.evaluate("1/0"));
        assertEquals("0.0", RushEvaluator.evaluate("1/tan(90)"));
        // Invalid expressions
        assertThrows(NumberFormatException.class, () -> RushEvaluator.evaluate("0/0"));
        assertThrows(StringIndexOutOfBoundsException.class, () -> RushEvaluator.evaluate("2+"));
    }
}
