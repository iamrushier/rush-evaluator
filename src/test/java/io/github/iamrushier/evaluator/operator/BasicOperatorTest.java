package io.github.iamrushier.evaluator.operator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BasicOperatorTest {

    @Test
    void evaluateOperation_addition_bigDecimal() {
        assertEquals("5", BasicOperator.evaluateOperation("2", "3", '+'));
        assertEquals("0", BasicOperator.evaluateOperation("2", "-2", '+'));
        assertEquals("0.5", BasicOperator.evaluateOperation("0.2", "0.3", '+'));
    }

    @Test
    void evaluateOperation_subtraction_bigDecimal() {
        assertEquals("-1", BasicOperator.evaluateOperation("2", "3", '-'));
        assertEquals("4", BasicOperator.evaluateOperation("2", "-2", '-'));
        assertEquals("-0.1", BasicOperator.evaluateOperation("0.2", "0.3", '-'));
    }

    @Test
    void evaluateOperation_multiplication_bigDecimal() {
        assertEquals("6", BasicOperator.evaluateOperation("2", "3", '*'));
        assertEquals("-4", BasicOperator.evaluateOperation("2", "-2", '*'));
        assertEquals("0.06", BasicOperator.evaluateOperation("0.2", "0.3", '*'));
    }

    @Test
    void evaluateOperation_division_bigDecimal() {
        assertEquals("2", BasicOperator.evaluateOperation("6", "3", '/'));
        assertEquals("-3", BasicOperator.evaluateOperation("6", "-2", '/'));
        assertEquals("0.6666666666666666666666666666666667", BasicOperator.evaluateOperation("0.2", "0.3", '/'));
        assertEquals("Infinity",  BasicOperator.evaluateOperation("1", "0", '/'));
    }

    @Test
    void evaluateOperation_invalidOperator_throwsArithmeticException() {
        ArithmeticException thrown = assertThrows(ArithmeticException.class, () -> {
            BasicOperator.evaluateOperation("2", "3", '%');
        });
        assertEquals("Invalid expression", thrown.getMessage());
    }

    @Test
    void evaluateOperation_addition_doubleFallback() {
        // These values will likely trigger the double fallback due to precision issues with BigDecimal
        assertEquals("0.3", BasicOperator.evaluateOperation("0.1", "0.2", '+'));
    }

    @Test
    void evaluateOperation_division_doubleFallback() {
        // Division by zero for double should result in "Infinity" or "-Infinity"
        assertEquals("Infinity", BasicOperator.evaluateOperation("1", "0", '/'));
        assertEquals("-Infinity", BasicOperator.evaluateOperation("-1", "0", '/'));
    }
}
