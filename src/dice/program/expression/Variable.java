package dice.program.expression;

import dice.error.UndefinedVariableException;
import dice.program.Scope;

public class Variable implements Expression {

    private Scope parent;
    private final String identifier;
    private final int line;

    public Variable(String identifier, int line) {
        this.identifier = identifier;
        this.line = line;
    }

    @Override
    public int getResult() {
        int result = 0;
        try {
            result = this.parent.getVariable(this.identifier, this.line);
        } catch (UndefinedVariableException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        return result;
    }

    @Override
    public void setScope(Scope p) {
        this.parent = p;
    }

    @Override
    public String toString() {
        return this.identifier;
    }
}
