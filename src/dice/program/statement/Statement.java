package dice.program.statement;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.Return;
import dice.program.Scope;

public interface Statement {

    void run() throws Return, UndefinedFunctionException, UndefinedVariableException;

    void setParentScope(Scope parent);
}
