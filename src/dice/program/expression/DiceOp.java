package dice.program.expression;

import dice.program.Scope;

public class DiceOp implements Expression {

    private final Expression e;

    public DiceOp(Expression e) {
        this.e = e;
    }

    @Override
    public int getResult() {
        int i = this.e.getResult();

        return (int) (Math.random() * i + 1);

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
