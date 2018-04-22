package dice.error;

@SuppressWarnings("serial")
public class UnexpectedCharacterException extends DiceError {

    private final int line;
    private final String src;

    public UnexpectedCharacterException(int line, int position, String src) {
        this.line = line;
        this.src = src.substring(position);
    }

    @Override
    public String getMessage() {
        return "line " + this.line + ": unexpected character [ " + this.src + "]";
    }

    public int getLine() {
        return this.line;
    }
}
