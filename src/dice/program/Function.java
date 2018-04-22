package dice.program;

import java.util.List;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.statement.Block;

public class Function implements Scope {

    private Scope parent;

    private String name;
    private Block body;

    private List<String> parameters;
    private int[] args;

    public Function(String name, List<String> parameters, Block body) {
        this.name = name;
        this.body = body;
        this.parameters = parameters;
        this.body.setParentScope(this);
    }

    public String getName() {
        return this.name;
    }

    public int getNumParams() {
        return this.parameters.size();
    }

    public int getResult(int... args) {
        assert args.length == this.parameters
                .size() : "Invalid length for args";
        this.args = args;

        try {
            this.body.run();
        } catch (Return e) {
            return e.getVal();
        }

        return 1;
    }

    @Override
    public int getVariable(String name, int line)
            throws UndefinedVariableException {
        if (this.parameters.contains(name)) {
            return this.args[this.parameters.indexOf(name)];
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
        } else if (this.parameters.contains(name)) {
            this.args[this.parameters.indexOf(name)] = val;
        } else {
            assert false : "var " + name + " not here " + this.toString();
        }

    }

    @Override
    public boolean hasVariable(String name) {
        return this.parameters.contains(name) || this.parent.hasVariable(name);
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
        String result = "";

        result += this.name + " (";

        String d = "";
        for (String p : this.parameters) {
            result += d + p;
            d = ", ";
        }

        result += ") " + this.body.toString();

        return result;
    }
}
