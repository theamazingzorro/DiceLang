package dice.tokenizer;

/**
 * Representation of a Token, which has a {@link TokenType} and a {@code String}
 * representation.
 *
 * @author Fox Noland
 */
public class Token {
    public enum TokenType {
        IDENTIFIER,
        NUMBER,
        PLUS,
        MINUS,
        DIVIDE,
        MULTIPLY,
        ASSIGN,
        EQUALS,
        NOT_EQUALS,
        GREATER,
        GREATER_EQUAL,
        LESSER,
        LESSER_EQUAL,
        AND,
        OR,
        NOT,
        l_PAREN,
        R_PAREN,
        L_BRACE,
        R_BRACE,
        COMMA,
        MAIN,
        FUNC,
        PRINT,
        RETURN,
        IF,
        ELSE,
        WHILE,
        DICE_OP
    }

    private TokenType type;
    private String token;

    /**
     * Gets the {@link TokenType} of this token.
     *
     * @return the type of this Token
     */
    public TokenType type() {
        return this.type;
    }

    /**
     * Gets the String this token was parsed from.
     *
     * @return the original String of this Token
     */
    public String token() {
        return this.token;
    }

    @Override
    public String toString() {
        return "[" + this.type + " : " + this.token + "]";
    }

    /**
     * Creates a new Token based on the given {@link TokenType} and original
     * {@code String}.
     *
     * @param s
     *            the original String of this token
     * @param t
     *            the type of this token
     */
    public Token(String s, TokenType t) {
        this.type = t;
        this.token = s;
    }
}
