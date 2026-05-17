package io.github.iamrushier.evaluator.parser;

/**
 * Enumeration of token types in a mathematical expression.
 */
public enum TokenType {
    /** A numerical value (integer, decimal, or scientific notation). */
    NUMBER,
    /** A binary operator (+, -, *, /, ^). */
    OPERATOR,
    /** A root operator (√, ∛). */
    ROOT,
    /** A mathematical function (sin, cos, log, etc.). */
    FUNCTION,
    /** A mathematical constant (π, e, ∞). */
    CONSTANT,
    /** An opening parenthesis '('. */
    PAREN_OPEN,
    /** A closing parenthesis ')'. */
    PAREN_CLOSE,
    /** End of file/expression. */
    EOF
}
