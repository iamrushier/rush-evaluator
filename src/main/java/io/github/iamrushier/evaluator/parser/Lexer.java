package io.github.iamrushier.evaluator.parser;

import io.github.iamrushier.evaluator.exception.SyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * {@code Lexer} converts a mathematical expression string into a list of tokens.
 */
public class Lexer {
    private final String input;
    private int index = 0;

    public Lexer(String input) {
        this.input = input;
    }

    /**
     * Tokenizes the input string.
     * @return A list of tokens.
     */
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (index < input.length()) {
            char currentChar = input.charAt(index);

            if (Character.isDigit(currentChar) || currentChar == '.') {
                tokens.add(new Token(TokenType.NUMBER, parseNumber()));
            } else if (isOperator(currentChar)) {
                tokens.add(new Token(TokenType.OPERATOR, String.valueOf(currentChar)));
                index++;
            } else if (currentChar == '(') {
                tokens.add(new Token(TokenType.PAREN_OPEN, "("));
                index++;
            } else if (currentChar == ')') {
                tokens.add(new Token(TokenType.PAREN_CLOSE, ")"));
                index++;
            } else if (currentChar == '\u221a' || currentChar == '\u221b') { // √ or ∛
                tokens.add(new Token(TokenType.ROOT, String.valueOf(currentChar)));
                index++;
            } else if (currentChar == '\u03c0' || currentChar == '\u221e') { // π or ∞
                tokens.add(new Token(TokenType.CONSTANT, String.valueOf(currentChar)));
                index++;
            } else if (Character.isLetter(currentChar)) {
                String word = parseWord();
                if (isConstant(word)) {
                    tokens.add(new Token(TokenType.CONSTANT, word));
                } else {
                    tokens.add(new Token(TokenType.FUNCTION, word));
                }
            } else {
                // Ignore whitespace if any remained, otherwise throw
                if (Character.isWhitespace(currentChar)) {
                    index++;
                } else {
                    throw new SyntaxException("Invalid character: " + currentChar);
                }
            }
        }
        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private String parseNumber() {
        StringBuilder sb = new StringBuilder();
        boolean hasDecimal = false;
        boolean hasExponent = false;

        while (index < input.length()) {
            char c = input.charAt(index);
            if (Character.isDigit(c)) {
                sb.append(c);
            } else if (c == '.' && !hasDecimal) {
                hasDecimal = true;
                sb.append(c);
            } else if ((c == 'e' || c == 'E') && !hasExponent) {
                hasExponent = true;
                sb.append(c);
                if (index + 1 < input.length() && (input.charAt(index + 1) == '+' || input.charAt(index + 1) == '-')) {
                    index++;
                    sb.append(input.charAt(index));
                }
            } else {
                break;
            }
            index++;
        }
        return sb.toString();
    }

    private String parseWord() {
        StringBuilder sb = new StringBuilder();
        while (index < input.length() && Character.isLetter(input.charAt(index))) {
            sb.append(input.charAt(index));
            index++;
        }
        return sb.toString();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private boolean isConstant(String word) {
        return word.equals("e") || word.equals("PI") || word.equals("Infinity");
    }
}
