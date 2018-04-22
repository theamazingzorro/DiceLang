package dice.program.statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.Function;
import dice.program.Return;
import dice.program.Scope;

public class Block implements Scope {

    Scope parent;

    List<Statement> statements;
    Map<String, Integer> vars;

    public Block(Scope parent) {
        this.statements = new ArrayList<>();
        this.vars = new HashMap<>();
        this.parent = parent;
    }

    public void addStatement(Statement s) {
        s.setParentScope(this);
        this.statements.add(s);
    }

    public void run() throws Return {
        for (Statement s : this.statements) {
            s.run();
        }
    }

    public int getReturnValue() {
        int returnVal = 0;

        try {
            this.run();
        } catch (Return e) {
            returnVal = e.getVal();
        }

        return returnVal;
    }

    @Override
    public int getVariable(String name, int line)
            throws UndefinedVariableException {
        if (this.vars.containsKey(name)) {
            return this.vars.get(name);
        } else if (this.parent != null) {
            return this.parent.getVariable(name, line);
        } else {
            throw new UndefinedVariableException(name, line);
        }
    }

    @Override
    public void setVariable(String name, int val) {
        if (this.parent.hasVariable(name)) {
            this.parent.setVariable(name, val);
        } else {
            this.vars.put(name, val);
        }
    }

    @Override
    public boolean hasVariable(String name) {
        return this.vars.containsKey(name) || this.parent.hasVariable(name);
    }

    @Override
    public Function getFunction(String name, int line)
            throws UndefinedFunctionException {
        return this.parent.getFunction(name, line);
    }

    @Override
    public void setParentScope(Scope parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        String result = "{\n";

        for (Statement s : this.statements) {
            result += s.toString() + '\n';
        }

        result += "}\n";

        return result;
    }
}
