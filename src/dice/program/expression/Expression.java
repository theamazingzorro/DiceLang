package dice.program.expression;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.Scope;

public interface Expression {

    int getResult() throws UndefinedFunctionException, UndefinedVariableException;

    void setScope(Scope p);
}
