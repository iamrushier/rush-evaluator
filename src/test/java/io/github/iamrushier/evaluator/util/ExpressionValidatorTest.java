package io.github.iamrushier.evaluator.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionValidatorTest {

    @Test
    void validateExpression_removesWhitespace() {
        assertEquals("1+2", ExpressionValidator.validateExpression("1 + 2"));
        assertEquals("sin(x)", ExpressionValidator.validateExpression("sin ( x )"));
    }

    @Test
    void validateExpression_replacesMultiplicationAndDivisionSymbols() {
        assertEquals("2*3", ExpressionValidator.validateExpression("2×3"));
        assertEquals("10/2", ExpressionValidator.validateExpression("10÷2"));
    }

    @Test
    void validateExpression_replacesPercentageSymbol() {
        assertEquals("5*(1/100)", ExpressionValidator.validateExpression("5%"));
        assertEquals("10+(1/100)", ExpressionValidator.validateExpression("10+%"));
    }

    @Test
    void validateExpression_insertsMultiplicationBetweenParentheses() {
        assertEquals("(2)*(3)", ExpressionValidator.validateExpression("(2)(3)"));
        assertEquals("(a)*(b)", ExpressionValidator.validateExpression("(a)(b)"));
    }

    @Test
    void validateExpression_insertsMultiplicationAfterParenthesisBeforeNumberConstantOrFunction() {
        assertEquals("(2)*3", ExpressionValidator.validateExpression("(2)3"));
        assertEquals("(x)*π", ExpressionValidator.validateExpression("(x)π"));
        assertEquals("(x)*e", ExpressionValidator.validateExpression("(x)e"));
        assertEquals("(x)*sin(y)", ExpressionValidator.validateExpression("(x)sin(y)"));
        assertEquals("(x)*cos(y)", ExpressionValidator.validateExpression("(x)cos(y)"));
        assertEquals("(x)*tan(y)", ExpressionValidator.validateExpression("(x)tan(y)"));
        assertEquals("(x)*asin(y)", ExpressionValidator.validateExpression("(x)asin(y)"));
        assertEquals("(x)*acos(y)", ExpressionValidator.validateExpression("(x)acos(y)"));
        assertEquals("(x)*atan(y)", ExpressionValidator.validateExpression("(x)atan(y)"));
        assertEquals("(x)*log(y)", ExpressionValidator.validateExpression("(x)log(y)"));
        assertEquals("(x)*ln(y)", ExpressionValidator.validateExpression("(x)ln(y)"));
    }

    @Test
    void validateExpression_insertsMultiplicationAfterNumberBeforeNonOperatorNonParenthesisNonNumber() {
        assertEquals("2*x", ExpressionValidator.validateExpression("2x"));
        assertEquals("2*sin(x)", ExpressionValidator.validateExpression("2sin(x)"));
        assertEquals("2*π", ExpressionValidator.validateExpression("2π"));
        assertEquals("2*(", ExpressionValidator.validateExpression("2("));
        assertEquals("2+3", ExpressionValidator.validateExpression("2+3")); // Should not insert
        assertEquals("2.5", ExpressionValidator.validateExpression("2.5")); // Should not insert
        assertEquals("2E3", ExpressionValidator.validateExpression("2E3")); // Should not insert
    }

    @Test
    void validateExpression_insertsMultiplicationAfterConstantBeforeNonOperatorNonParenthesis() {
        assertEquals("π*2", ExpressionValidator.validateExpression("π2"));
        assertEquals("e*(", ExpressionValidator.validateExpression("e("));
        assertEquals("π*sin(x)", ExpressionValidator.validateExpression("πsin(x)"));
        assertEquals("π+2", ExpressionValidator.validateExpression("π+2")); // Should not insert
    }

    @Test
    void validateExpression_complexExpression() {
        String raw = "2(sin(30) + 5)π + 10÷2% - (4)(5)";
        String expected = "2*(sin(30)+5)*π+10/2*(1/100)-(4)*(5)";
        assertEquals(expected, ExpressionValidator.validateExpression(raw));
    }

    @Test
    void validateExpression_noChangeNeeded() {
        String raw = "1+2-3*4/5^6";
        assertEquals(raw, ExpressionValidator.validateExpression(raw));
    }
}
