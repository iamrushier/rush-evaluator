package io.github.iamrushier.evaluator.function;

import io.github.iamrushier.evaluator.exception.DomainException;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LogarithmicFunctionTest {

    @Test
    void handleLog_positiveValue_returnsCorrectResult() {
        assertEquals("1", LogarithmicFunction.handleLog(new BigDecimal("10")));
        assertEquals("0", LogarithmicFunction.handleLog(new BigDecimal("1")));
    }

    @Test
    void handleLog_zeroValue_throwsDomainException() {
        assertThrows(DomainException.class, () -> {
            LogarithmicFunction.handleLog(BigDecimal.ZERO);
        });
    }

    @Test
    void handleLog_negativeValue_throwsDomainException() {
        assertThrows(DomainException.class, () -> {
            LogarithmicFunction.handleLog(new BigDecimal("-5"));
        });
    }

    @Test
    void handleLn_positiveValue_returnsCorrectResult() {
        assertEquals("2.302585093", LogarithmicFunction.handleLn(new BigDecimal("10")));
        assertEquals("0", LogarithmicFunction.handleLn(new BigDecimal("1")));
    }

    @Test
    void handleLn_zeroValue_throwsDomainException() {
        assertThrows(DomainException.class, () -> {
            LogarithmicFunction.handleLn(BigDecimal.ZERO);
        });
    }

    @Test
    void handleLn_negativeValue_throwsDomainException() {
        assertThrows(DomainException.class, () -> {
            LogarithmicFunction.handleLn(new BigDecimal("-5"));
        });
    }
}
