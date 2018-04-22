package dice.program.condition;

import dice.program.Scope;

public interface Condition {

    boolean getResult();

    void setScope(Scope p);
}
