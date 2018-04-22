package dice.program;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;

public interface Scope {

    int getVariable(String name, int line) throws UndefinedVariableException;

    void setVariable(String name, int val);

    boolean hasVariable(String name);

    Function getFunction(String name, int line)
            throws UndefinedFunctionException;

    void setParentScope(Scope parent);
}
