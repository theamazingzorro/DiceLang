package dice.error;

@SuppressWarnings("serial")
public class UndefinedFunctionException extends DiceError {
    private final String func;
    private final int line;

    public UndefinedFunctionException(String func, int line) {
        this.func = func;
        this.line = line;
    }

    @Override
    public String getMessage() {
        return "line " + this.line + ": function " + this.func + " was not declared.";
    }

    public String getVariableName() {
        return this.func;
    }

    public int getLine() {
        return this.line;
    }
}
