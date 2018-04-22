package dice.program.condition;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.Scope;

public class Logic implements Condition {

    public enum LogicType {
        AND, OR, NOT
    };

    private final LogicType type;
    private final Condition c1;
    private final Condition c2;

    public Logic(LogicType type, Condition c1, Condition c2) {
        this.type = type;
        this.c1 = c1;
        this.c2 = c2;
    }

    public Logic(LogicType type, Condition c1) {
        assert type == LogicType.NOT : "not is the only valid unary logical operator";
        this.type = type;
        this.c1 = c1;
        this.c2 = null;
    }

    @Override
    public boolean getResult() throws UndefinedFunctionException, UndefinedVariableException {
        switch (this.type) {
            case AND:
                return this.c1.getResult() && this.c2.getResult();
            case NOT:
                return !this.c1.getResult();
            case OR:
                return this.c1.getResult() || this.c2.getResult();
            default:
                assert false : "Invalid logic type";
                return false;

        }
    }

    @Override
    public void setScope(Scope p) {
        this.c1.setScope(p);
        if (this.c2 != null) {
            this.c2.setScope(p);
        }
    }

    @Override
    public String toString() {
        String result = "(";

        switch (this.type) {
            case AND:
                result += this.c1.toString() + " and " + this.c2.toString();
                break;
            case NOT:
                result += "not " + this.c1.toString();
                break;
            case OR:
                result += this.c1.toString() + " or " + this.c2.toString();
                break;
        }

        result += ')';

        return result;
    }
}
