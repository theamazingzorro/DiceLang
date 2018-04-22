package dice.test;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dice.program.Function;
import dice.program.Program;
import dice.program.condition.Comparison;
import dice.program.condition.Comparison.CompType;
import dice.program.condition.Condition;
import dice.program.expression.BinaryOp;
import dice.program.expression.BinaryOp.BinaryType;
import dice.program.expression.Constant;
import dice.program.expression.DiceOp;
import dice.program.expression.FunctionCall;
import dice.program.expression.Variable;
import dice.program.statement.Block;
import dice.program.statement.Command;
import dice.program.statement.Command.CommandType;
import dice.program.statement.IfElse;
import dice.program.statement.While;

class ProgramTest {

    @Test
    void test1() {
        Program p = new Program();
        Block b = new Block(p);
        p.replaceBody(b);

        b.addStatement(new Command(CommandType.ASSIGNMENT, "var", new Constant(12)));

        Block b1 = new Block(b);
        Block b2 = new Block(b);
        Condition c = new Comparison(CompType.EQUAL, new Constant(12), new Variable("var", 2));
        IfElse i = new IfElse(c, b1, b2);
        b.addStatement(i);

        b1.addStatement(new Command(CommandType.PRINT, new Variable("var", 3)));
        b1.addStatement(new Command(CommandType.RETURN, new Variable("var", 3)));
        b2.addStatement(new Command(CommandType.PRINT, new Constant(22)));

        //System.out.println(p);
        //System.out.println(p.run());
    }

    @Test
    void test2() {
        Program p = new Program();
        Block b = new Block(p);
        p.replaceBody(b);

        b.addStatement(new Command(CommandType.ASSIGNMENT, "var", new Constant(0)));

        Condition c = new Comparison(CompType.NOT_EQUAL, new Variable("var", 2), new Constant(10));

        Block bw = new Block(b);
        b.addStatement(new While(c, bw));

        bw.addStatement(new Command(CommandType.EXPR, new FunctionCall("foo", 3, new Variable("var", 3))));
        bw.addStatement(new Command(CommandType.ASSIGNMENT, "var", new BinaryOp(BinaryType.ADD, new Variable("var", 4), new Constant(1))));

        Block bf = new Block(null);
        Function foo = new Function("foo", Arrays.asList("x"), bf);
        bf.addStatement(new Command(CommandType.PRINT, new Variable("x", 5)));

        p.addFunction(foo);

        //System.out.println(p);
        //p.run();
    }

    @Test
    void test3() {
        Program p = new Program();
        Block b = new Block(p);
        p.replaceBody(b);

        DiceOp d6 = new DiceOp(new Constant(6));

        b.addStatement(new Command(CommandType.ASSIGNMENT, "var", d6));

        Condition c = new Comparison(CompType.NOT_EQUAL, new Variable("var", 2), new Constant(3));

        Block bw = new Block(b);
        b.addStatement(new While(c, bw));

        bw.addStatement(new Command(CommandType.EXPR, new FunctionCall("foo", 3, new Variable("var", 3))));
        bw.addStatement(new Command(CommandType.ASSIGNMENT, "var", d6));

        Block bf = new Block(null);
        Function foo = new Function("foo", Arrays.asList("x"), bf);
        bf.addStatement(new Command(CommandType.PRINT, new Variable("x", 5)));

        p.addFunction(foo);

        //System.out.println(p);
        //p.run();
    }
}
