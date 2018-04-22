package dice.program.expression;

import dice.program.Scope;

public interface Expression {

    int getResult();

    void setScope(Scope p);
}
