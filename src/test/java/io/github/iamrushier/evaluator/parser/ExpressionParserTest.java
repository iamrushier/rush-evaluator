package io.github.iamrushier.evaluator.parser;

import io.github.iamrushier.evaluator.exception.SyntaxException;
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
        assertEquals("2", parser.parse("4^0.5")); // sqrt(4)
    }

    @Test
    void parse_constants() {
        // Using approximate values for PI and EULER for comparison due to BigDecimal precision
        assertEquals("6.28318530717958647692528676655900576839433879875020", parser.parse("2*π"));
        assertEquals("3.71828182845904523536028747135266249775724709369995", parser.parse("e+1"));
        assertEquals("∞", parser.parse("∞-5"));
    }

    @Test
    void parse_functions() {
        assertEquals("1", parser.parse("sin(90)"));
        assertEquals("2", parser.parse("log(100)"));
        assertEquals("1", parser.parse("ln(e)"));
        assertEquals("0.5", parser.parse("cos(60)"));
        assertEquals("1", parser.parse("tan(45)"));
        assertEquals("30", parser.parse("asin(0.5)"));
        assertEquals("60", parser.parse("acos(0.5)"));
        assertEquals("45", parser.parse("atan(1)"));
    }

    @Test
    void parse_combinedExpressions() {
        // 2 * sin(30) + log(100)^2 = 2 * 0.5 + 2^2 = 1 + 4 = 5
        assertEquals("5", parser.parse("2*sin(30)+log(100)^2"));
        // (1+2)*3^2/sin(90) = 3 * 9 / 1 = 27
        assertEquals("27", parser.parse("(1+2)*3^2/sin(90)"));
    }

    @Test
    void parse_scientificNotation() {
        assertEquals("100.2", parser.parse("1e2+2e-1"));
        assertEquals("1000", parser.parse("1E3"));
        assertEquals("0.001", parser.parse("1E-3"));
    }

    @Test
    void parse_invalidExpression_missingOperand() {
        assertThrows(SyntaxException.class, () -> parser.parse("1+"));
    }

    @Test
    void parse_invalidExpression_emptyFunctionArgument() {
        assertThrows(SyntaxException.class, () -> parser.parse("sin()"));
    }

    @Test
    void parse_invalidExpression_unclosedParenthesis() {
        assertThrows(SyntaxException.class, () -> parser.parse("(1+2"));
    }

    @Test
    void parse_invalidExpression_doubleOperator() {
        assertThrows(SyntaxException.class, () -> parser.parse("2**3"));
    }

    @Test
    void parse_invalidExpression_unknownFunction() {
        assertThrows(SyntaxException.class, () -> parser.parse("xyz(1)"));
    }
}
