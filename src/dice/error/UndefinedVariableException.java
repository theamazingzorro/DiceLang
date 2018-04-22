package dice.error;

@SuppressWarnings("serial")
public class UndefinedVariableException extends Exception {
    private final String var;
    private final int line;

    public UndefinedVariableException(String var, int line) {
        this.var = var;
        this.line = line;
    }

    @Override
    public String getMessage() {
        return "line " + this.line + ": variable " + this.var
                + " was never declared.";
    }

    public String getVariableName() {
        return this.var;
    }

    public int getLine() {
        return this.line;
    }
}
