package dice.program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.program.statement.Block;
import dice.program.statement.Command;
import dice.program.statement.Command.CommandType;

public class Program implements Scope {

    private Block body;
    private Map<String, Function> functions;
    private Map<String, Integer> vars;
    private List<Command> globals;

    public Program() {
        this.body = new Block(this);
        this.functions = new HashMap<>();
        this.vars = new HashMap<>();
        this.globals = new ArrayList<>();
    }

    public Block replaceBody(Block b) {
        Block temp = this.body;
        this.body = b;
        b = new Block(null);

        this.body.setParentScope(this);

        return temp;
    }

    public void addFunction(Function f) {
        f.setParentScope(this);
        this.functions.put(f.getName(), f);
    }

    public void addGlobal(Command c) {
        assert c.getType() == CommandType.ASSIGNMENT : "global statements must be assignment";

        c.setParentScope(this);
        this.globals.add(c);
    }

    @Override
    public int getVariable(String name, int line) throws UndefinedVariableException {
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
    public Function getFunction(String name, int line) throws UndefinedFunctionException {
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

    public int run() throws UndefinedFunctionException, UndefinedVariableException {
        for (Command c : this.globals) {
            try {
                c.run();
            } catch (Return e) {
                // intentionally left blank
            }
        }

        try {
            this.body.run();
        } catch (Return e) {
            return e.getVal();
        }

        return 0;
    }
}
