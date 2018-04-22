package dice.program.condition;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.Scope;

public interface Condition {

    boolean getResult() throws UndefinedFunctionException, UndefinedVariableException;

    void setScope(Scope p);
}
