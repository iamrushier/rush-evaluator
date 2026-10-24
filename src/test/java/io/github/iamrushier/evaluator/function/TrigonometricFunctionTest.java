package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.config.AngleMode;
import io.github.iamrushier.evaluator.exception.DomainException;
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
        BigDecimal angle = new BigDecimal("45");
        assertEquals("0.7071067812", TrigonometricFunction.handleSin(angle));
    }

    @Test
    void handleSin_radiansMode_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleSin(BigDecimal.ZERO, AngleMode.RADIANS));
        assertEquals("1", TrigonometricFunction.handleSin(BigDecimal.valueOf(Math.PI / 2), AngleMode.RADIANS));
        assertEquals("0", TrigonometricFunction.handleSin(BigDecimal.valueOf(Math.PI), AngleMode.RADIANS));
        assertEquals("-1", TrigonometricFunction.handleSin(BigDecimal.valueOf(3 * Math.PI / 2), AngleMode.RADIANS));
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
        BigDecimal angle = new BigDecimal("45");
        assertEquals("0.7071067812", TrigonometricFunction.handleCos(angle));
    }

    @Test
    void handleCos_radiansMode_returnsCorrectResult() {
        assertEquals("1", TrigonometricFunction.handleCos(BigDecimal.ZERO, AngleMode.RADIANS));
        assertEquals("0", TrigonometricFunction.handleCos(BigDecimal.valueOf(Math.PI / 2), AngleMode.RADIANS));
        assertEquals("-1", TrigonometricFunction.handleCos(BigDecimal.valueOf(Math.PI), AngleMode.RADIANS));
    }

    @Test
    void handleTan_specialAngles_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleTan(BigDecimal.ZERO));
        assertEquals("1", TrigonometricFunction.handleTan(new BigDecimal("45")));
        assertEquals("Infinity", TrigonometricFunction.handleTan(new BigDecimal("90")));
        assertEquals("-1", TrigonometricFunction.handleTan(new BigDecimal("135")));
        assertEquals("0", TrigonometricFunction.handleTan(new BigDecimal("180")));
        assertEquals("1", TrigonometricFunction.handleTan(new BigDecimal("225")));
        assertEquals("Infinity", TrigonometricFunction.handleTan(new BigDecimal("270")));
        assertEquals("-1", TrigonometricFunction.handleTan(new BigDecimal("315")));
    }

    @Test
    void handleTan_radiansMode_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleTan(BigDecimal.ZERO, AngleMode.RADIANS));
        assertEquals("1", TrigonometricFunction.handleTan(BigDecimal.valueOf(Math.PI / 4), AngleMode.RADIANS));
    }

    @Test
    void handleAsin_specialValues_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleAsin(BigDecimal.ZERO));
        assertEquals("30", TrigonometricFunction.handleAsin(new BigDecimal("0.5")));
        assertEquals("90", TrigonometricFunction.handleAsin(BigDecimal.ONE));
        assertEquals("-30", TrigonometricFunction.handleAsin(new BigDecimal("-0.5")));
        assertEquals("-90", TrigonometricFunction.handleAsin(new BigDecimal("-1")));
    }

    @Test
    void handleAsin_radiansMode_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleAsin(BigDecimal.ZERO, AngleMode.RADIANS));
        assertEquals("1.5707963268", TrigonometricFunction.handleAsin(BigDecimal.ONE, AngleMode.RADIANS));
    }

    @Test
    void handleAsin_outOfRange_throwsDomainException() {
        assertThrows(DomainException.class, () -> TrigonometricFunction.handleAsin(new BigDecimal("1.1")));
        assertThrows(DomainException.class, () -> TrigonometricFunction.handleAsin(new BigDecimal("-1.1")));
        assertThrows(DomainException.class, () -> TrigonometricFunction.handleAsin(new BigDecimal("1.1"), AngleMode.RADIANS));
    }

    @Test
    void handleAcos_specialValues_returnsCorrectResult() {
        assertEquals("90", TrigonometricFunction.handleAcos(BigDecimal.ZERO));
        assertEquals("60", TrigonometricFunction.handleAcos(new BigDecimal("0.5")));
        assertEquals("0", TrigonometricFunction.handleAcos(BigDecimal.ONE));
        assertEquals("120", TrigonometricFunction.handleAcos(new BigDecimal("-0.5")));
        assertEquals("180", TrigonometricFunction.handleAcos(new BigDecimal("-1")));
    }

    @Test
    void handleAcos_radiansMode_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleAcos(BigDecimal.ONE, AngleMode.RADIANS));
        assertEquals("1.5707963268", TrigonometricFunction.handleAcos(BigDecimal.ZERO, AngleMode.RADIANS));
    }

    @Test
    void handleAcos_outOfRange_throwsDomainException() {
        assertThrows(DomainException.class, () -> TrigonometricFunction.handleAcos(new BigDecimal("1.1")));
        assertThrows(DomainException.class, () -> TrigonometricFunction.handleAcos(new BigDecimal("-1.1")));
        assertThrows(DomainException.class, () -> TrigonometricFunction.handleAcos(new BigDecimal("1.1"), AngleMode.RADIANS));
    }

    @Test
    void handleAtan_specialValues_returnsCorrectResult() {
        assertEquals("90", TrigonometricFunction.handleAtan("Infinity"));
        assertEquals("-90", TrigonometricFunction.handleAtan("-Infinity"));
        assertEquals("45", TrigonometricFunction.handleAtan("1"));
        assertEquals("-45", TrigonometricFunction.handleAtan("-1"));
        assertEquals("0", TrigonometricFunction.handleAtan("0"));
    }

    @Test
    void handleAtan_radiansMode_returnsCorrectResult() {
        assertEquals("0", TrigonometricFunction.handleAtan("0", AngleMode.RADIANS));
        assertEquals("0.7853981634", TrigonometricFunction.handleAtan("1", AngleMode.RADIANS));
        assertEquals("1.5707963268", TrigonometricFunction.handleAtan("Infinity", AngleMode.RADIANS));
        assertEquals("-1.5707963268", TrigonometricFunction.handleAtan("-Infinity", AngleMode.RADIANS));
    }

    @Test
    void handleSin_veryLargeAngle_returnsCorrectResult() {
        BigDecimal largeAngle = new BigDecimal("1000000000000000000");
        assertTimeoutPreemptively(java.time.Duration.ofSeconds(1), () -> {
            TrigonometricFunction.handleSin(largeAngle);
        });
    }

    @Test
    void handleSin_veryLargeNegativeAngle_returnsCorrectResult() {
        BigDecimal largeNegativeAngle = new BigDecimal("-1000000000000000000");
        assertTimeoutPreemptively(java.time.Duration.ofSeconds(1), () -> {
            TrigonometricFunction.handleSin(largeNegativeAngle);
        });
    }
}
