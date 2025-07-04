package io.github.iamrushier.evaluator.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionParserTest {

    private final ExpressionParser parser = new ExpressionParser();

    @Test
    void parse_basicAddition() {
        assertEquals("5", parser.parse("2+3"));
    }

    @Test
    void parse_basicSubtraction() {
        assertEquals("-1", parser.parse("2-3"));
    }

    @Test
    void parse_basicMultiplication() {
        assertEquals("6", parser.parse("2*3"));
    }

    @Test
    void parse_basicDivision() {
        assertEquals("2", parser.parse("6/3"));
    }

    @Test
    void parse_orderOfOperations() {
        assertEquals("14", parser.parse("2+3*4")); // 2 + 12 = 14
        assertEquals("20", parser.parse("(2+3)*4")); // 5 * 4 = 20
    }

    @Test
    void parse_parentheses() {
        assertEquals("9", parser.parse("(1+2)*3"));
        assertEquals("5", parser.parse("10/(5-3)"));
    }

    @Test
    void parse_powerOperator() {
        assertEquals("8", parser.parse("2^3"));
        assertEquals("2.0", parser.parse("4^0.5")); // sqrt(4)
    }

    @Test
    void parse_constants() {
        // Using approximate values for PI and EULER for comparison due to BigDecimal precision
        // The actual values are handled by Constants class, so we just check if they are used.
        // For simplicity, we'll check the result of a simple operation involving them.
        assertEquals("6.28318530717958647692528676655900576839433879875020", parser.parse("2*π"));
        assertEquals("3.71828182845904523536028747135266249775724709369995", parser.parse("e+1"));
        assertEquals("Infinity", parser.parse("∞-5"));
    }

    @Test
    void parse_functions() {
        assertEquals("1.0", parser.parse("sin(90)"));
        assertEquals("2.0", parser.parse("log(100)"));
        assertEquals("1.0", parser.parse("ln(e)"));
        assertEquals("0.5", parser.parse("cos(60)"));
        assertEquals("1.0", parser.parse("tan(45)"));
        assertEquals("30.0", parser.parse("asin(0.5)"));
        assertEquals("60.0", parser.parse("acos(0.5)"));
        assertEquals("45.0", parser.parse("atan(1)"));
    }

    @Test
    void parse_combinedExpressions() {
        // 2 * sin(30) + log(100)^2 = 2 * 0.5 + 2^2 = 1 + 4 = 5
        assertEquals("5", parser.parse("2*sin(30)+log(100)^2"));
        // (1+2)*3^2/sin(90) = 3 * 9 / 1 = 27
        assertEquals("27", parser.parse("(1+2)*3^2/sin(90)"));
    }

//    @Test
//    void parse_unaryMinus() {
//        assertEquals("-5", parser.parse("-5"));
//        assertEquals("-6", parser.parse("2*-3"));
//        assertEquals("-3", parser.parse("-(1+2)"));
//    }

    @Test
    void parse_scientificNotation() {
        assertEquals("100.2", parser.parse("1e2+2e-1"));
        assertEquals("1E3", parser.parse("1E3"));
        assertEquals("1E-3", parser.parse("1E-3"));
    }

    @Test
    void parse_invalidExpression_missingOperand() {
        assertThrows(Exception.class, () -> parser.parse("1+"));
    }

    @Test
    void parse_invalidExpression_emptyFunctionArgument() {
        assertThrows(Exception.class, () -> parser.parse("sin()"));
    }

    @Test
    void parse_invalidExpression_unclosedParenthesis() {
        assertEquals("3", parser.parse("(1+2"));
    }

    @Test
    void parse_invalidExpression_doubleOperator() {
        assertThrows(Exception.class, () -> parser.parse("2**3"));
    }

    @Test
    void parse_invalidExpression_unknownFunction() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("xyz(1)"));
    }
}
