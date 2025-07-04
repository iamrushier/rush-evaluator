package io.github.iamrushier.evaluator.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OutputFormatterTest {

    @Test
    void consistentScientific_noE_returnsSameNumber() {
        assertEquals("123.45", OutputFormatter.consistentScientific("123.45"));
        assertEquals("-0.001", OutputFormatter.consistentScientific("-0.001"));
        assertEquals("0", OutputFormatter.consistentScientific("0"));
    }

    @Test
    void consistentScientific_positiveExponent_formatsCorrectly() {
        assertEquals("1.2345E+2", OutputFormatter.consistentScientific("1.2345E2"));
        assertEquals("1.2345E+3", OutputFormatter.consistentScientific("12.345E2"));
        assertEquals("1.2345E+4", OutputFormatter.consistentScientific("123.45E2"));
    }

    @Test
    void consistentScientific_negativeExponent_formatsCorrectly() {
        assertEquals("1.2345E-2", OutputFormatter.consistentScientific("1.2345E-2"));
        assertEquals("1.2345E-1", OutputFormatter.consistentScientific("12.345E-2"));
        assertEquals("1.2345E+0", OutputFormatter.consistentScientific("123.45E-2"));
    }

    @Test
    void consistentScientific_zeroExponent_formatsCorrectly() {
        assertEquals("1.2345E+0", OutputFormatter.consistentScientific("1.2345E0"));
    }

    @Test
    void consistentScientific_negativeBase_formatsCorrectly() {
        assertEquals("-1.2345E+2", OutputFormatter.consistentScientific("-1.2345E2"));
        assertEquals("-1.2345E-2", OutputFormatter.consistentScientific("-1.2345E-2"));
    }

    @Test
    void consistentScientific_differentPrecision() {
        assertEquals("1.0E+0", OutputFormatter.consistentScientific("1E0"));
        assertEquals("9.87654321E+5", OutputFormatter.consistentScientific("987654.321E0"));
    }

    @Test
    void consistentScientific_alreadyFormatted_noChange() {
        assertEquals("1.2345E+2", OutputFormatter.consistentScientific("1.234500E+2"));
    }

    @Test
    void consistentScientific_largeExponent() {
        assertEquals("1.0E+100", OutputFormatter.consistentScientific("1E100"));
        assertEquals("1.0E-100", OutputFormatter.consistentScientific("1E-100"));
    }
}
