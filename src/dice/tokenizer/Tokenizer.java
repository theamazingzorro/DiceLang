package dice.tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dice.error.UnexpectedCharacterException;
import dice.tokenizer.Token.TokenType;

/**
 * Creates a list of tokens from an input file or string.
 *
 * @author Fox Noland
 */
public class Tokenizer {

    private static final Map<String, TokenType> keywords;
    static {
        keywords = new HashMap<>();
        keywords.put("and", TokenType.AND);
        keywords.put("or", TokenType.OR);
        keywords.put("main", TokenType.MAIN);
        keywords.put("func", TokenType.FUNC);
        keywords.put("print", TokenType.PRINT);
        keywords.put("return", TokenType.RETURN);
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("while", TokenType.WHILE);
    }

    private final String src;
    private List<Token> tokens;

    private int start;
    private int current;
    private int line;

    /**
     * Creates a new Tokenizer for the given String.
     *
     * @param src
     *            the String to tokenize
     */
    public Tokenizer(String src) {
        this.src = src;
    }

    /**
     * Separates a string into the {@link Token tokens} that comprise it.
     * 
     * @return a {@link List} of the tokens in the String
     * @throws UnexpectedCharacterException
     *             if the String contains an invalid sequence of characters
     */
    public List<Token> scanTokens() throws UnexpectedCharacterException {
        this.start = 0;
        this.current = 0;
        this.line = 1;
        this.tokens = new ArrayList<>();

        while (!this.isAtEnd()) {
            this.start = this.current;
            this.scanToken();
        }

        this.tokens.add(new Token("", TokenType.EOF, -1));
        return this.tokens;
    }

    private void scanToken() throws UnexpectedCharacterException {
        char c = this.advance();

        switch (c) {
            case '(':
                this.tokens.add(new Token("", TokenType.L_PAREN, this.line));
                break;
            case ')':
                this.tokens.add(new Token("", TokenType.R_PAREN, this.line));
                break;
            case '{':
                this.tokens.add(new Token("", TokenType.L_BRACE, this.line));
                break;
            case '}':
                this.tokens.add(new Token("", TokenType.R_BRACE, this.line));
                break;
            case ',':
                this.tokens.add(new Token("", TokenType.COMMA, this.line));
                break;
            case '-':
                this.tokens.add(new Token("", TokenType.MINUS, this.line));
                break;
            case '+':
                this.tokens.add(new Token("", TokenType.PLUS, this.line));
                break;
            case '*':
                this.tokens.add(new Token("", TokenType.STAR, this.line));
                break;
            case ';':
                this.tokens.add(new Token("", TokenType.SEMICOLON, this.line));
                break;
            case '=':
                this.tokens.add(new Token("", TokenType.EQUALS, this.line));
                break;
            case 'd':
                if (this.match('(')) {
                    this.tokens
                            .add(new Token("", TokenType.DICE_OP, this.line));
                    this.tokens
                            .add(new Token("", TokenType.L_PAREN, this.line));
                } else if (this.match(' ')) {
                    this.tokens
                            .add(new Token("", TokenType.DICE_OP, this.line));
                }
                break;
            case '!':
                if (this.match('=')) {
                    this.tokens.add(
                            new Token("", TokenType.NOT_EQUALS, this.line));
                } else {
                    this.tokens.add(new Token("", TokenType.NOT, this.line));
                }
                break;
            case '>':
                if (this.match('=')) {
                    this.tokens.add(
                            new Token("", TokenType.GREATER_EQUAL, this.line));
                } else {
                    this.tokens
                            .add(new Token("", TokenType.GREATER, this.line));
                }
                break;
            case '<':
                if (this.match('=')) {
                    this.tokens.add(
                            new Token("", TokenType.LESSER_EQUAL, this.line));
                } else if (this.match('-')) {
                    this.tokens.add(new Token("", TokenType.ARROW, this.line));
                } else {
                    this.tokens.add(new Token("", TokenType.LESSER, this.line));
                }
                break;
            case '/':
                if (this.match('/')) {
                    // A comment goes until the end of the line.
                    while (this.peek() != '\n' && !this.isAtEnd()) {
                        this.advance();
                        this.line++;
                    }
                } else {
                    this.tokens.add(new Token("", TokenType.SLASH, this.line));
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;
            case '\n':
                this.line++;
                break;
            default:
                if (this.isDigit(c)) {
                    this.number();
                } else if (this.isAlpha(c)) {
                    this.identifier();
                } else {
                    throw new UnexpectedCharacterException(this.line,
                            this.current - 1, this.src);
                }
                break;
        }

    }

    private void identifier() {
        while (this.isAlphaNumeric(this.peek())) {
            this.advance();
        }

        String text = this.src.substring(this.start, this.current);

        TokenType type = keywords.get(text);
        if (type == null) {
            type = TokenType.IDENTIFIER;
        }

        this.tokens.add(new Token(text, type, this.line));
    }

    private void number() {
        while (this.isDigit(this.peek())) {
            this.advance();
        }

        this.tokens.add(new Token(this.src.substring(this.start, this.current),
                TokenType.NUMBER, this.line));
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return this.isAlpha(c) || this.isDigit(c);
    }

    private char advance() {
        this.current++;
        return this.src.charAt(this.current - 1);
    }

    private boolean match(char expected) {
        if (this.isAtEnd()) {
            return false;
        }
        if (this.src.charAt(this.current) != expected) {
            return false;
        }

        this.current++;
        return true;
    }

    private char peek() {
        if (this.isAtEnd()) {
            return '\0';
        }
        return this.src.charAt(this.current);
    }

    private boolean isAtEnd() {
        return this.current >= this.src.length();
    }
}
