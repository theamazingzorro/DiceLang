package dice.program.expression;

import dice.error.UndefinedFunctionException;
import dice.program.Function;
import dice.program.Scope;

public class FunctionCall implements Expression {

    private Scope parent;

    private final String identifier;
    private final int line;
    private final Expression[] args;

    public FunctionCall(String identifier, int line, Expression... args) {
        this.identifier = identifier;
        this.args = args;
        this.line = line;
    }

    @Override
    public int getResult() {
        int result = 0;
        try {
            Function f = this.parent.getFunction(this.identifier, this.line);
            int[] arr = new int[this.args.length];
            for (int i = 0; i < this.args.length; i++) {
                arr[i] = this.args[i].getResult();
            }
            result = f.getResult(arr);
        } catch (UndefinedFunctionException e) {
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
        String result = this.identifier + '(';

        String d = "";
        for (Expression e : this.args) {
            result += d + e.toString();
            d = ", ";
        }

        result += ')';

        return result;
    }
}
