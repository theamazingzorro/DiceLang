package dice.program.statement;

import dice.program.Return;
import dice.program.Scope;
import dice.program.expression.Expression;

public class Command implements Statement {

    public enum CommandType {
        PRINT, ASSIGNMENT, RETURN, EXPR
    };

    private Scope parent;
    private final CommandType type;
    private String identifier;
    private Expression expression;

    public Command(CommandType type, String identifier, Expression e) {
        assert type == CommandType.ASSIGNMENT : "identifier not required for non assignment commands";
        this.expression = e;
        this.identifier = identifier;
        this.type = type;
    }

    public Command(CommandType type, Expression e) {
        this.type = type;
        this.expression = e;
    }

    @Override
    public void setParentScope(Scope p) {
        this.parent = p;
        this.expression.setScope(p);
    }

    @Override
    public void run() throws Return {
        int i = this.expression.getResult();

        switch (this.type) {
            case ASSIGNMENT: {
                this.parent.setVariable(this.identifier, i);
                break;
            }
            case PRINT: {
                System.out.println(i);
                break;
            }
            case RETURN: {
                throw new Return(i);
            }
            case EXPR: {
                // intentionally left blank
                break;
            }
        }
    }

    @Override
    public String toString() {
        String result = "";

        switch (this.type) {
            case ASSIGNMENT: {
                result = this.identifier + " <- " + this.expression.toString()
                        + ';';
                break;
            }
            case PRINT: {
                result = "print " + this.expression.toString() + ';';
                break;
            }
            case RETURN: {
                result = "return " + this.expression.toString() + ';';
                break;
            }
            case EXPR: {
                result = this.expression.toString() + ';';
                break;
            }
        }

        return result;
    }
}
