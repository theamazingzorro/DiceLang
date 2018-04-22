package dice.program.statement;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.Return;
import dice.program.Scope;
import dice.program.condition.Condition;

public class IfElse implements Statement {

    private final Condition c;
    private final Block b1;
    private final Block b2;
    private final IfElse b3;

    public IfElse(Condition c, Block i, Block e) {
        this.c = c;
        this.b1 = i;
        this.b2 = e;
        this.b3 = null;
    }

    public IfElse(Condition c, Block i, IfElse e) {
        this.c = c;
        this.b1 = i;
        this.b2 = null;
        this.b3 = e;
    }

    @Override
    public void setParentScope(Scope p) {
        this.c.setScope(p);
        this.b1.setParentScope(p);
        if (this.b2 != null) {
            this.b2.setParentScope(p);
        } else {
            this.b3.setParentScope(p);
        }
    }

    @Override
    public void run() throws Return, UndefinedFunctionException, UndefinedVariableException {
        if (this.c.getResult()) {
            this.b1.run();
        } else if (this.b2 != null) {
            this.b2.run();
        } else {
            this.b3.run();
        }

    }

    @Override
    public String toString() {
        String result = "if (";

        result += this.c.toString() + ")" + this.b1.toString() + "else ";

        if (this.b2 != null) {
            result += this.b2.toString();
        } else {
            result += this.b3.toString();
        }

        return result;
    }
}
