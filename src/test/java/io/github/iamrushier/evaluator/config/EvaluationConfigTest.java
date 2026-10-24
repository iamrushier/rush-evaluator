package io.github.iamrushier.evaluator.config;

import org.junit.jupiter.api.Test;

import java.math.MathContext;

import static org.junit.jupiter.api.Assertions.*;

class EvaluationConfigTest {

    @Test
    void defaultConfig_usesDegreesAndDecimal128() {
        EvaluationConfig config = EvaluationConfig.defaultConfig();
        assertEquals(AngleMode.DEGREES, config.getAngleMode());
        assertEquals(MathContext.DECIMAL128, config.getMathContext());
    }

    @Test
    void degreesPreset_usesDegrees() {
        EvaluationConfig config = EvaluationConfig.degrees();
        assertEquals(AngleMode.DEGREES, config.getAngleMode());
    }

    @Test
    void radiansPreset_usesRadians() {
        EvaluationConfig config = EvaluationConfig.radians();
        assertEquals(AngleMode.RADIANS, config.getAngleMode());
    }

    @Test
    void withAngleMode_returnsNewInstanceWithUpdatedMode() {
        EvaluationConfig config = EvaluationConfig.defaultConfig();
        EvaluationConfig radConfig = config.withAngleMode(AngleMode.RADIANS);
        
        assertEquals(AngleMode.RADIANS, radConfig.getAngleMode());
        assertEquals(AngleMode.DEGREES, config.getAngleMode()); // Immutability check
    }

    @Test
    void withMathContext_returnsNewInstanceWithUpdatedContext() {
        EvaluationConfig config = EvaluationConfig.defaultConfig();
        EvaluationConfig updated = config.withMathContext(MathContext.DECIMAL64);

        assertEquals(MathContext.DECIMAL64, updated.getMathContext());
        assertEquals(MathContext.DECIMAL128, config.getMathContext());
    }

    @Test
    void constructor_rejectsNullArguments() {
        assertThrows(NullPointerException.class, () -> new EvaluationConfig(null));
        assertThrows(NullPointerException.class, () -> new EvaluationConfig(AngleMode.DEGREES, null));
    }
}
