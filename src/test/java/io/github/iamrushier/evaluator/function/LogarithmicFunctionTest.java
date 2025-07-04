package io.github.iamrushier.evaluator.function;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LogarithmicFunctionTest {

    @Test
    void handleLog_positiveValue_returnsCorrectResult() {
        assertEquals("1.0", LogarithmicFunction.handleLog(new BigDecimal("10")));
        assertEquals("0.0", LogarithmicFunction.handleLog(new BigDecimal("1")));
    }

    @Test
    void handleLog_zeroValue_throwsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            LogarithmicFunction.handleLog(BigDecimal.ZERO);
        });
        assertEquals("Domain error", thrown.getMessage());
    }

    @Test
    void handleLog_negativeValue_throwsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            LogarithmicFunction.handleLog(new BigDecimal("-5"));
        });
        assertEquals("Domain error", thrown.getMessage());
    }

    @Test
    void handleLn_positiveValue_returnsCorrectResult() {
        // Using a small delta for double comparisons due to precision
        assertEquals(String.valueOf(Math.log(10.0)), LogarithmicFunction.handleLn(new BigDecimal("10")));
        assertEquals("0.0", LogarithmicFunction.handleLn(new BigDecimal("1")));
    }

    @Test
    void handleLn_zeroValue_throwsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            LogarithmicFunction.handleLn(BigDecimal.ZERO);
        });
        assertEquals("Domain error", thrown.getMessage());
    }

    @Test
    void handleLn_negativeValue_throwsIllegalArgumentException() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            LogarithmicFunction.handleLn(new BigDecimal("-5"));
        });
        assertEquals("Domain error", thrown.getMessage());
    }
}
