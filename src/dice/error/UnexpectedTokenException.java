package dice.error;

import dice.tokenizer.Token;
import dice.tokenizer.Token.TokenType;

@SuppressWarnings("serial")
public class UnexpectedTokenException extends DiceError {

    private final int line;
    private final String src;

    public UnexpectedTokenException(Token t) {
        this.line = t.line();
        if (t.type() == TokenType.IDENTIFIER || t.type() == TokenType.NUMBER) {
            this.src = t.token();
        } else {
            this.src = t.type().toString().toLowerCase();
        }
    }

    @Override
    public String getMessage() {
        return "line " + this.line + ": unexpected token [ " + this.src + "]";
    }

    public int getLine() {
        return this.line;
    }
}
