package dice.test;

import org.junit.jupiter.api.Test;

import dice.program.Program;
import dice.program.condition.Comparison;
import dice.program.condition.Comparison.CompType;
import dice.program.condition.Condition;
import dice.program.expression.Constant;
import dice.program.expression.Variable;
import dice.program.statement.Block;
import dice.program.statement.Command;
import dice.program.statement.Command.CommandType;
import dice.program.statement.IfElse;

class ProgramTest {

    @Test
    void test() {
        Program p = new Program();
        Block b = new Block(p);
        p.replaceBody(b);

        b.addStatement(
                new Command(CommandType.ASSIGNMENT, "var", new Constant(12)));

        Block b1 = new Block(b);
        Block b2 = new Block(b);
        Condition c = new Comparison(CompType.EQUAL, new Constant(12),
                new Variable("var", 2));
        IfElse i = new IfElse(c, b1, b2);
        b.addStatement(i);

        b1.addStatement(new Command(CommandType.PRINT, new Variable("var", 3)));
        b1.addStatement(
                new Command(CommandType.RETURN, new Variable("var", 3)));
        b2.addStatement(new Command(CommandType.PRINT, new Constant(22)));

        System.out.println(p);
        System.out.println(p.run());
    }

}
