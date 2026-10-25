package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.config.EvaluationConfig;
import io.github.iamrushier.evaluator.exception.SyntaxException;
import io.github.iamrushier.evaluator.util.Operand;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class CustomFunctionTest {

    @Test
    void testSingleArgumentCustomFunction() {
        EvaluationConfig config = EvaluationConfig.degrees()
                .withFunction("doubleIt", (args, cfg) -> {
                    BigDecimal val = args.get(0).getAsBigDecimal();
                    return new Operand(val.multiply(BigDecimal.valueOf(2)));
                });

        assertEquals("20", RushEvaluator.evaluate("doubleIt(10)", config));
        assertEquals("50", RushEvaluator.evaluate("doubleIt(20) + 10", config));
    }

    @Test
    void testMultiArgumentCustomFunction() {
        EvaluationConfig config = EvaluationConfig.degrees()
                .withFunction("hypot", (args, cfg) -> {
                    double a = args.get(0).getAsDouble();
                    double b = args.get(1).getAsDouble();
                    return new Operand(Math.hypot(a, b));
                });

        assertEquals("5", RushEvaluator.evaluate("hypot(3, 4)", config));
        assertEquals("13", RushEvaluator.evaluate("hypot(5, 12)", config));
    }

    @Test
    void testCustomFunctionWithVariables() {
        EvaluationConfig config = EvaluationConfig.degrees()
                .withFunction("clamp", (args, cfg) -> {
                    double val = args.get(0).getAsDouble();
                    double min = args.get(1).getAsDouble();
                    double max = args.get(2).getAsDouble();
                    return new Operand(Math.max(min, Math.min(max, val)));
                })
                .withVariable("val", 120);

        assertEquals("100", RushEvaluator.evaluate("clamp(val, 0, 100)", config));
    }

    @Test
    void testCustomFunctionIsIsolatedToConfig() {
        EvaluationConfig customConfig = EvaluationConfig.degrees()
                .withFunction("cube", (args, cfg) -> new Operand(Math.pow(args.get(0).getAsDouble(), 3)));

        assertEquals("27", RushEvaluator.evaluate("cube(3)", customConfig));

        // Default config should not have 'cube'
        assertThrows(SyntaxException.class, () -> RushEvaluator.evaluate("cube(3)"));
    }
}
