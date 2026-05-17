package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.exception.DomainException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RootOperatorTest {

    @Test
    void testSquareRoot() {
        assertEquals("3", RushEvaluator.evaluate("√9"));
        assertEquals("2", RushEvaluator.evaluate("√√16"));
    }

    @Test
    void testCubeRoot() {
        assertEquals("2", RushEvaluator.evaluate("∛8"));
        assertEquals("3", RushEvaluator.evaluate("∛27"));
    }

    @Test
    void testRootPrecedence() {
        // √3+4 should be (√3)+4
        String result = RushEvaluator.evaluate("√9+4");
        assertEquals("7", result);
        
        // √(9+16) should be √25 = 5
        assertEquals("5", RushEvaluator.evaluate("√(9+16)"));
        
        // √9^2 should be √(9^2) = √81 = 9 or (√9)^2 = 3^2 = 9
        assertEquals("9", RushEvaluator.evaluate("√9^2"));
    }

    @Test
    void testRootWithFunctions() {
        // √sin(90) = √1 = 1
        assertEquals("1", RushEvaluator.evaluate("√sin(90)"));
    }

    @Test
    void testImplicitMultiplicationWithRoots() {
        assertEquals("6", RushEvaluator.evaluate("2√9"));
        assertEquals("2", RushEvaluator.evaluate("√4√1"));
    }

    @Test
    void testNegativeRoots() {
        assertThrows(DomainException.class, () -> RushEvaluator.evaluate("√-1"));
        // Cube root of negative IS allowed in math
        assertEquals("-2", RushEvaluator.evaluate("∛-8"));
    }
}
