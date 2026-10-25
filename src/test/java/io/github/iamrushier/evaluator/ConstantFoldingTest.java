package io.github.iamrushier.evaluator;

import io.github.iamrushier.evaluator.ast.BinaryOpNode;
import io.github.iamrushier.evaluator.ast.NumberNode;
import io.github.iamrushier.evaluator.ast.VariableNode;
import io.github.iamrushier.evaluator.expression.CompiledExpression;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ConstantFoldingTest {

    @Test
    void testPureConstantExpressionsFoldToNumberNode() {
        CompiledExpression expr1 = RushEvaluator.compile("2 + 3 * 4");
        assertTrue(expr1.getRootNode() instanceof NumberNode);
        assertEquals("14", expr1.evaluate());

        CompiledExpression expr2 = RushEvaluator.compile("sin(30) + 1");
        assertTrue(expr2.getRootNode() instanceof NumberNode);
        assertEquals("1.5", expr2.evaluate());

        CompiledExpression expr3 = RushEvaluator.compile("√16 + 2^3");
        assertTrue(expr3.getRootNode() instanceof NumberNode);
        assertEquals("12", expr3.evaluate());
    }

    @Test
    void testPartialSubtreeConstantFolding() {
        // In "2 * 3 * x", "2 * 3" folds to 6, leaving BinaryOpNode(6 * x)
        CompiledExpression expr = RushEvaluator.compile("2 * 3 * x");
        assertFalse(expr.isConstant());
        assertTrue(expr.getRootNode() instanceof BinaryOpNode);

        BinaryOpNode root = (BinaryOpNode) expr.getRootNode();
        assertEquals('*', root.getOperator());
        assertTrue(root.getLeft() instanceof NumberNode);
        assertEquals("6", ((NumberNode) root.getLeft()).getValue().toString());
        assertTrue(root.getRight() instanceof VariableNode);

        assertEquals("60", expr.evaluate(Collections.singletonMap("x", 10)));
    }
}
