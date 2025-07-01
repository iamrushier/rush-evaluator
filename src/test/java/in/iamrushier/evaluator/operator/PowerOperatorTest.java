package in.iamrushier.evaluator.operator;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PowerOperatorTest {

    @Test
    void power_positiveIntegerExponent_returnsCorrectResult() {
        assertEquals("8", PowerOperator.power("2", "3"));
        assertEquals("1", PowerOperator.power("5", "0"));
        assertEquals("100", PowerOperator.power("10", "2"));
    }

    @Test
    void power_negativeIntegerExponent_returnsCorrectResult() {
        assertEquals("0.125", PowerOperator.power("2", "-3"));
        assertEquals("0.01", PowerOperator.power("10", "-2"));
    }

    @Test
    void power_zeroBaseZeroExponent_throwsNumberFormatException() {
        NumberFormatException thrown = assertThrows(NumberFormatException.class, () -> {
            PowerOperator.power("0", "0");
        });
        assertEquals("Undefined", thrown.getMessage());
    }

    @Test
    void power_fractionalExponent_returnsCorrectResult() {
        // These will likely trigger the double fallback
        assertEquals(String.valueOf(Math.pow(4.0, 0.5)), PowerOperator.power("4", "0.5")); // sqrt(4)
        assertEquals(String.valueOf(Math.pow(8.0, 0.3333333333333333)), PowerOperator.power("8", "0.3333333333333333")); // cube root of 8
    }

    @Test
    void power_negativeBaseIntegerExponent_returnsCorrectResult() {
        assertEquals("-8", PowerOperator.power("-2", "3"));
        assertEquals("4", PowerOperator.power("-2", "2"));
    }

    @Test
    void power_largeNumbers_returnsCorrectResult() {
        assertEquals(new BigDecimal("2").pow(30).toPlainString(), PowerOperator.power("2", "30"));
    }

    @Test
    void power_doubleFallback_returnsCorrectResult() {
        // Test a scenario that would definitely go to double fallback
        assertEquals(String.valueOf(Math.pow(2.5, 3.1)), PowerOperator.power("2.5", "3.1"));
    }
}
