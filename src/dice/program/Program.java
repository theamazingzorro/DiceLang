package dice.program;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.statement.Block;

public class Program implements Scope {

    private Block body;
    private Map<String, Function> functions;
    private Map<String, Integer> vars;

    public Program() {
        this.body = new Block(this);
        this.functions = new HashMap<>();
        this.vars = new HashMap<>();
    }

    public Block replaceBody(Block b) {
        Block temp = this.body;
        this.body = b;
        b = new Block(this);
        return temp;
    }

    public void addFunction(Function f) {
        this.functions.put(f.getName(), f);
    }

    @Override
    public int getVariable(String name, int line)
            throws UndefinedVariableException {
        if (this.vars.containsKey(name)) {
            return this.vars.get(name);
        } else {
            throw new UndefinedVariableException(name, line);
        }
    }

    @Override
    public void setVariable(String name, int val) {
        this.vars.put(name, val);
    }

    @Override
    public boolean hasVariable(String name) {
        return this.vars.containsKey(name);
    }

    @Override
    public Function getFunction(String name, int line)
            throws UndefinedFunctionException {
        if (this.functions.containsKey(name)) {
            return this.functions.get(name);
        } else {
            throw new UndefinedFunctionException(name, line);
        }
    }

    @Override
    public void setParentScope(Scope parent) {
        // intentionally left blank
        // program is top level scope
    }

    @Override
    public String toString() {
        String result = "";

        for (Entry<String, Integer> e : this.vars.entrySet()) {
            result += e.getKey() + " <- " + e.getValue() + '\n';
        }

        result += '\n';

        for (Entry<String, Function> e : this.functions.entrySet()) {
            result += e.getValue().toString() + '\n';
        }

        result += "\nmain " + this.body.toString();

        return result;
    }

    public int run() {
        try {
            this.body.run();
        } catch (Return e) {
            return e.getVal();
        }

        return 0;
    }
}
