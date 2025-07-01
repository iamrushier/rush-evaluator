package in.iamrushier.evaluator.function;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TrigonometricFunctionTest {

    @Test
    void handleSin_specialAngles_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleSin(BigDecimal.ZERO));
        assertEquals("0.5", TrigonometricFunction.handleSin(new BigDecimal("30")));
        assertEquals("1", TrigonometricFunction.handleSin(new BigDecimal("90")));
        assertEquals("0.5", TrigonometricFunction.handleSin(new BigDecimal("150")));
        assertEquals("0", TrigonometricFunction.handleSin(new BigDecimal("180")));
        assertEquals("-0.5", TrigonometricFunction.handleSin(new BigDecimal("210")));
        assertEquals("-1", TrigonometricFunction.handleSin(new BigDecimal("270")));
        assertEquals("-0.5", TrigonometricFunction.handleSin(new BigDecimal("330")));
    }

    @Test
    void handleSin_normalizedAngles_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleSin(new BigDecimal("360")));
        assertEquals("1", TrigonometricFunction.handleSin(new BigDecimal("450")));
        assertEquals("-1", TrigonometricFunction.handleSin(new BigDecimal("-90")));
    }

    @Test
    void handleSin_generalAngles_returnsCorrectResult() {
        // Test with a general angle, comparing with Math.sin result
        BigDecimal angle = new BigDecimal("45");
        assertEquals(String.valueOf(Math.sin(Math.toRadians(angle.doubleValue()))), TrigonometricFunction.handleSin(angle));
    }

    @Test
    void handleCos_specialAngles_returnsCorrectResult() {
        assertEquals("1", TrigonometricFunction.handleCos(BigDecimal.ZERO));
        assertEquals("0.5", TrigonometricFunction.handleCos(new BigDecimal("60")));
        assertEquals("0", TrigonometricFunction.handleCos(new BigDecimal("90")));
        assertEquals("-0.5", TrigonometricFunction.handleCos(new BigDecimal("120")));
        assertEquals("-1", TrigonometricFunction.handleCos(new BigDecimal("180")));
        assertEquals("-0.5", TrigonometricFunction.handleCos(new BigDecimal("240")));
        assertEquals("0", TrigonometricFunction.handleCos(new BigDecimal("270")));
        assertEquals("0.5", TrigonometricFunction.handleCos(new BigDecimal("300")));
    }

    @Test
    void handleCos_normalizedAngles_returnsCorrectResult() {
        assertEquals("1", TrigonometricFunction.handleCos(new BigDecimal("360")));
        assertEquals("0", TrigonometricFunction.handleCos(new BigDecimal("450")));
        assertEquals("0", TrigonometricFunction.handleCos(new BigDecimal("-270")));
    }

    @Test
    void handleCos_generalAngles_returnsCorrectResult() {
        // Test with a general angle, comparing with Math.cos result
        BigDecimal angle = new BigDecimal("45");
        assertEquals(String.valueOf(Math.cos(Math.toRadians(angle.doubleValue()))), TrigonometricFunction.handleCos(angle));
    }

    @Test
    void handleTan_specialAngles_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleTan(BigDecimal.ZERO));
        assertEquals("1", TrigonometricFunction.handleTan(new BigDecimal("45")));
        assertEquals("Infinity", TrigonometricFunction.handleTan(new BigDecimal("90")));
        assertEquals("-1", TrigonometricFunction.handleTan(new BigDecimal("135")));
        assertEquals("0", TrigonometricFunction.handleTan(new BigDecimal("180")));
        assertEquals("1", TrigonometricFunction.handleTan(new BigDecimal("225")));
        assertEquals("-Infinity", TrigonometricFunction.handleTan(new BigDecimal("270")));
        assertEquals("-1", TrigonometricFunction.handleTan(new BigDecimal("315")));
    }

    @Test
    void handleTan_normalizedAngles_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleTan(new BigDecimal("360")));
        assertEquals("Infinity", TrigonometricFunction.handleTan(new BigDecimal("450")));
        assertEquals("-Infinity", TrigonometricFunction.handleTan(new BigDecimal("-90")));
    }

    @Test
    void handleTan_generalAngles_returnsCorrectResult() {
        // Test with a general angle, comparing with Math.tan result
        BigDecimal angle = new BigDecimal("30");
        assertEquals(String.valueOf(Math.tan(Math.toRadians(angle.doubleValue()))), TrigonometricFunction.handleTan(angle));
    }

    @Test
    void handleAsin_specialValues_returnsCorrectResult() {
        assertEquals("30", TrigonometricFunction.handleAsin(new BigDecimal("0.5")));
        assertEquals("90", TrigonometricFunction.handleAsin(BigDecimal.ONE));
        assertEquals("-30", TrigonometricFunction.handleAsin(new BigDecimal("-0.5")));
        assertEquals("-90", TrigonometricFunction.handleAsin(BigDecimal.valueOf(-1)));
    }

    @Test
    void handleAsin_outOfDomain_throwsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            TrigonometricFunction.handleAsin(new BigDecimal("1.1"));
        });
        assertEquals("Domain error", thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> {
            TrigonometricFunction.handleAsin(new BigDecimal("-1.1"));
        });
        assertEquals("Domain error", thrown.getMessage());
    }

    @Test
    void handleAsin_generalValues_returnsCorrectResult() {
        BigDecimal value = new BigDecimal("0.70710678118"); // sin(45 degrees)
        assertEquals(String.valueOf(Math.toDegrees(Math.asin(value.doubleValue()))), TrigonometricFunction.handleAsin(value));
    }

    @Test
    void handleAcos_specialValues_returnsCorrectResult() {
        assertEquals("60", TrigonometricFunction.handleAcos(new BigDecimal("0.5")));
        assertEquals("0", TrigonometricFunction.handleAcos(BigDecimal.ONE));
        assertEquals("120", TrigonometricFunction.handleAcos(new BigDecimal("-0.5")));
        assertEquals("180", TrigonometricFunction.handleAcos(BigDecimal.valueOf(-1)));
    }

    @Test
    void handleAcos_outOfDomain_throwsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            TrigonometricFunction.handleAcos(new BigDecimal("1.1"));
        });
        assertEquals("Domain error", thrown.getMessage());

        thrown = assertThrows(IllegalArgumentException.class, () -> {
            TrigonometricFunction.handleAcos(new BigDecimal("-1.1"));
        });
        assertEquals("Domain error", thrown.getMessage());
    }

    @Test
    void handleAcos_generalValues_returnsCorrectResult() {
        BigDecimal value = new BigDecimal("0.70710678118"); // cos(45 degrees)
        assertEquals(String.valueOf(Math.toDegrees(Math.acos(value.doubleValue()))), TrigonometricFunction.handleAcos(value));
    }

    @Test
    void handleAtan_specialValues_returnsCorrectResult() {
        assertEquals("90", TrigonometricFunction.handleAtan("Infinity"));
        assertEquals("-90", TrigonometricFunction.handleAtan("-Infinity"));
        assertEquals("45.0", TrigonometricFunction.handleAtan("1"));
        assertEquals("-45.0", TrigonometricFunction.handleAtan("-1"));
        assertEquals("0.0", TrigonometricFunction.handleAtan("0"));
    }

    @Test
    void handleAtan_generalValues_returnsCorrectResult() {
        BigDecimal value = new BigDecimal("0.57735026919"); // tan(30 degrees)
        assertEquals(String.valueOf(Math.toDegrees(Math.atan(value.doubleValue()))), TrigonometricFunction.handleAtan(value.toString()));
    }
}
