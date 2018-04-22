package dice.program.expression;

import dice.program.Scope;

public class Constant implements Expression {

    private final int val;

    public Constant(int val) {
        this.val = val;
    }

    @Override
    public int getResult() {
        return this.val;
    }

    @Override
    public void setScope(Scope p) {
        // unnecessary
    }

    @Override
    public String toString() {
        return "" + this.val;
    }
}
