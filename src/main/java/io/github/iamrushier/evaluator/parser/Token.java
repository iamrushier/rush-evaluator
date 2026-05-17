package io.github.iamrushier.evaluator.parser;

/**
 * {@code Token} represents a single lexical unit in a mathematical expression.
 */
public class Token {
    private final TokenType type;
    private final String value;

    /**
     * Constructs a {@code Token} with the specified type and value.
     *
     * @param type the type of the token
     * @param value the string value of the token
     */
    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
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

    @Override
    public String toString() {
        return String.format("Token(%s, %s)", type, value);
    }
}
