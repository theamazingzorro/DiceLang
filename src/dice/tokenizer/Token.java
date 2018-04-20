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
        SLASH,
        STAR,
        ARROW,
        SEMICOLON,
        EQUALS,
        NOT_EQUALS,
        GREATER,
        GREATER_EQUAL,
        LESSER,
        LESSER_EQUAL,
        AND,
        OR,
        NOT,
        L_PAREN,
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
        DICE_OP,
        EOF
    }

    private TokenType type;
    private String token;
    private int line;

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

    /**
     * Gets the line the Token was originally on.
     *
     * @return the line this Token was on
     */
    public int line() {
        return this.line;
    }

    @Override
    public String toString() {
        return "[" + this.line + ": " + this.type + " : " + this.token + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Token)) {
            return false;
        }

        Token t = (Token) o;

        return t.token.equals(this.token) && t.type == this.type;
    }

    /**
     * Creates a new Token based on the given {@link TokenType} and original
     * {@code String}.
     *
     * @param s
     *            the original String of this token
     * @param t
     *            the type of this token
     * @param l
     *            the line number of this token
     */
    public Token(String s, TokenType t, int l) {
        this.type = t;
        this.token = s;
        this.line = l;
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
        this.line = 0;
    }
}
