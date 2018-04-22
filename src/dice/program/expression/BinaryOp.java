package dice.program.expression;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.Scope;

public class BinaryOp implements Expression {

    public enum BinaryType {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, MOD
    };

    private final BinaryType type;
    private final Expression e1;
    private final Expression e2;

    public BinaryOp(BinaryType type, Expression e1, Expression e2) {
        this.type = type;
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public int getResult() throws UndefinedFunctionException, UndefinedVariableException {
        int i1 = this.e1.getResult();
        int i2 = this.e2.getResult();

        switch (this.type) {
            case ADD:
                return i1 + i2;
            case DIVIDE:
                return i1 / i2;
            case MOD:
                return i1 % i2;
            case MULTIPLY:
                return i1 * i2;
            case SUBTRACT:
                return i1 - i2;
            default:
                assert false : "Invalid binary op type";
                return 0;

        }
    }

    @Override
    public void setScope(Scope p) {
        this.e1.setScope(p);
        this.e2.setScope(p);
    }

    @Override
    public String toString() {
        String result = "(" + this.e1.toString();

        switch (this.type) {
            case ADD:
                result += " + ";
                break;
            case DIVIDE:
                result += " / ";
                break;
            case MOD:
                result += " % ";
                break;
            case MULTIPLY:
                result += " * ";
                break;
            case SUBTRACT:
                result += " - ";
                break;
        }

        result += this.e2.toString() + ')';

        return result;
    }
}
