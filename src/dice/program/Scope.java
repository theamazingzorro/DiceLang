package dice.program;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;

public interface Scope {

    /**
     * Gets the value of the specified variable in the current scope. If it is
     * not in the current scope, then it checks the parent scope.
     *
     * @param name
     *            the name of the variable
     * @param line
     *            the current line of this program (only needed in case of
     *            error)
     * @return the value of the named variable
     * @throws UndefinedVariableException
     *             if the variable is not defined in this scope, or any parent
     *             scope
     */
    int getVariable(String name, int line) throws UndefinedVariableException;

    /**
     * Changes the value of the named variable. This first checks for the
     * nearest scope that already contains the variable, then changes its value
     * there. If the variable doesn't exist in this scope, then it is created
     * locally.
     *
     * @param name
     *            the name of the variable
     * @param val
     *            the new value for the variable
     */
    void setVariable(String name, int val);

    /**
     * Checks if this scope, or the parent scopes contain this variable.
     *
     * @param name
     *            the name of the variable
     * @return true if the variable exists in scope
     */
    boolean hasVariable(String name);

    /**
     * Gets the named function in scope.
     *
     * @param name
     *            the name of the function
     * @param line
     *            the current line of this program (only needed in case of
     *            error)
     * @return the named {@link Function}
     * @throws UndefinedFunctionException
     *             if the function was not declared in scope
     */
    Function getFunction(String name, int line)
            throws UndefinedFunctionException;

    /**
     * Sets the given {@code Scope} as the parent of {@code this}.
     * 
     * @param parent
     *            the new parent of {@code this}
     */
    void setParentScope(Scope parent);
}
