package dice.program.condition;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.Scope;
import dice.program.expression.Expression;

public class Comparison implements Condition {

    public enum CompType {
        EQUAL, NOT_EQUAL, LESS, LESS_EQUAL, GREAT, GREAT_EQUAL
    };

    private final CompType type;
    private final Expression e1;
    private final Expression e2;

    public Comparison(CompType type, Expression e1, Expression e2) {
        this.type = type;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public boolean getResult() throws UndefinedFunctionException, UndefinedVariableException {
        int i1 = this.e1.getResult();
        int i2 = this.e2.getResult();

        switch (this.type) {
            case EQUAL:
                return i1 == i2;
            case GREAT:
                return i1 > i2;
            case GREAT_EQUAL:
                return i1 >= i2;
            case LESS:
                return i1 < i2;
            case LESS_EQUAL:
                return i1 <= i2;
            case NOT_EQUAL:
                return i1 != i2;
            default:
                assert false : "Invalid comparison type";
                return false;
        }
    }

    @Override
    public void setScope(Scope p) {
        this.e1.setScope(p);
        this.e2.setScope(p);
    }

    @Override
    public String toString() {
        String result = '(' + this.e1.toString();

        switch (this.type) {
            case EQUAL:
                result += " = ";
                break;
            case GREAT:
                result += " > ";
                break;
            case GREAT_EQUAL:
                result += " >= ";
                break;
            case LESS:
                result += " < ";
                break;
            case LESS_EQUAL:
                result += " <= ";
                break;
            case NOT_EQUAL:
                result += " != ";
                break;
        }

        result += this.e2.toString() + ')';

        return result;
    }
}
