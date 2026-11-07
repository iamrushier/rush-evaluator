package io.github.iamrushier.evaluator.parser;

/**
 * {@code Token} represents a single lexical unit in a mathematical expression with its character position.
 */
public class Token {
    private final TokenType type;
    private final String value;
    private final int position;

    /**
     * Constructs a {@code Token} with the specified type and value, with unknown position (-1).
     *
     * @param type the type of the token
     * @param value the string value of the token
     */
    public Token(TokenType type, String value) {
        this(type, value, -1);
    }

    /**
     * Constructs a {@code Token} with the specified type, value, and position in the source expression.
     *
     * @param type the type of the token
     * @param value the string value of the token
     * @param position the zero-based character index where the token begins
     */
    public Token(TokenType type, String value, int position) {
        this.type = type;
        this.value = value;
        this.position = position;
    }

    /**
     * Returns the type of the token.
     *
     * @return the token type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Returns the string value of the token.
     *
     * @return the token value
     */
    public String getValue() {
        return value;
    }

    /**
     * Returns the zero-based character index of the token in the expression, or -1 if unspecified.
     *
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return String.format("Token(%s, %s, pos=%d)", type, value, position);
    }
}
