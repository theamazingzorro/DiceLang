package dice.program.statement;

import dice.program.Return;
import dice.program.Scope;

public interface Statement {

    void run() throws Return;

    void setParentScope(Scope parent);
}
