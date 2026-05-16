package io.github.iamrushier.evaluator.util;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class CalculationEngineTest {

    @Test
    void testBasicAddition() {
        assertEquals("5", CalculationEngine.calculate("2", "3", '+'));
    }

    @Test
    void testDivisionWithPrecision() {
        // 1/3 in DECIMAL128 should be high precision
        String result = CalculationEngine.calculate("1", "3", '/');
        assertTrue(result.startsWith("0.3333333333333333"));
    }

    @Test
    void testLargeNumberResult() {
        // BigDecimal handles 10^308 * 10 without falling back to double
        String result = CalculationEngine.calculate("1E308", "10", '*');
        assertTrue(result.length() > 300);
        assertTrue(result.startsWith("1000"));
    }

    @Test
    void testPowerWithFractionalExponent() {
        // 4^0.5 = 2.0 (triggers double fallback because BigDecimal.pow only takes int)
        assertEquals("2.0", CalculationEngine.power("4", "0.5"));
    }

    @Test
    void testInfinityHandling() {
        assertEquals("Infinity", CalculationEngine.calculate("1", "0", '/'));
    }
}
