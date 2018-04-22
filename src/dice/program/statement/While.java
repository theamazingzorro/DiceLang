package dice.program.statement;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.Return;
import dice.program.Scope;
import dice.program.condition.Condition;

public class While implements Statement {

    private final Condition c;
    private final Block b;

    public While(Condition c, Block b) {
        this.b = b;
        this.c = c;
    }

    @Override
    public void run() throws Return, UndefinedFunctionException, UndefinedVariableException {
        while (this.c.getResult()) {
            this.b.run();
        }

    }

    @Override
    public void setParentScope(Scope p) {
        this.c.setScope(p);
        this.b.setParentScope(p);
    }

    @Override
    public String toString() {
        String result = "while (";

        result += this.c.toString() + ") ";
        result += this.b.toString();

        return result;
    }
}
