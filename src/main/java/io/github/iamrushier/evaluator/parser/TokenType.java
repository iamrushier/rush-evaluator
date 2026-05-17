package io.github.iamrushier.evaluator.parser;

/**
 * Enumeration of token types in a mathematical expression.
 */
public enum TokenType {
    NUMBER,
    OPERATOR,
    ROOT,
    FUNCTION,
    CONSTANT,
    PAREN_OPEN,
    PAREN_CLOSE,
    EOF
}
