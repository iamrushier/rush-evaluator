package io.github.iamrushier.evaluator.util;

import io.github.iamrushier.evaluator.exception.DomainException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class CalculationEngineTest {

    @Test
    void testBasicAddition() {
        assertEquals("5", CalculationEngine.calculate(Operand.of("2"), Operand.of("3"), '+').toString());
    }

    @Test
    void testDivisionWithPrecision() {
        // 1/3 in DECIMAL128 should be high precision
        String result = CalculationEngine.calculate(Operand.of("1"), Operand.of("3"), '/').toString();
        assertTrue(result.startsWith("0.3333333333333333"));
    }

    @Test
    void testLargeNumberResult() {
        // BigDecimal handles 10^308 * 10 without falling back to double
        String result = CalculationEngine.calculate(Operand.of("1E308"), Operand.of("10"), '*').toString();
        assertTrue(result.length() > 300);
        assertTrue(result.startsWith("1000"));
    }

    @Test
    void testPowerWithFractionalExponent() {
        // 4^0.5 = 2 (triggers double fallback because BigDecimal.pow only takes int, but Operand formats result)
        assertEquals("2", CalculationEngine.power(Operand.of("4"), Operand.of("0.5")).toString());
    }

    @Test
    void testInfinityHandling() {
        assertEquals("∞", CalculationEngine.calculate(Operand.of("1"), Operand.of("0"), '/').toString());
    }

    @Test
    void testOperandSpecialConversionToBigDecimal() {
        Operand inf = new Operand(Double.POSITIVE_INFINITY);
        assertTrue(inf.isSpecial());
        assertThrows(DomainException.class, inf::getAsBigDecimal);

        Operand nan = new Operand(Double.NaN);
        assertTrue(nan.isSpecial());
        assertThrows(DomainException.class, nan::getAsBigDecimal);

        Operand finite = new Operand(4.5);
        assertFalse(finite.isSpecial());
        assertEquals(0, new BigDecimal("4.5").compareTo(finite.getAsBigDecimal()));
    }
}
