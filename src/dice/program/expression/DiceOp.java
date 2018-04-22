package dice.program.expression;

import java.util.Random;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.Scope;

public class DiceOp implements Expression {

    private static final Random rand = new Random();

    private final Expression e;

    public DiceOp(Expression e) {
        this.e = e;
    }

    @Override
    public int getResult() throws UndefinedFunctionException, UndefinedVariableException {
        int i = this.e.getResult();

        return rand.nextInt(i - 1) + 1;

    }

    @Override
    public void setScope(Scope p) {
        this.e.setScope(p);
    }

    @Override
    public String toString() {
        return "d(" + this.e.toString() + ")";
    }
}
