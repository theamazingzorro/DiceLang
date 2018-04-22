package dice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import dice.error.UndefinedFunctionException;
import dice.error.UndefinedVariableException;
import dice.error.UnexpectedCharacterException;
import dice.error.UnexpectedTokenException;
import dice.parser.Parser;
import dice.program.Program;
import dice.tokenizer.Tokenizer;

class ParserTest {

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    @Test
    void test1() {
        try {
            String src = readFile("data/roll.d", StandardCharsets.UTF_8);

            Tokenizer t = new Tokenizer(src);

            Parser p = new Parser(t.scanTokens());

            Program prog = p.parse();

            System.out.println("\n===Roll===");
            //System.out.println(prog);
            System.out.println("The result: " + prog.run());

        } catch (UnexpectedCharacterException e) {
            fail(e.getMessage());
            e.printStackTrace();
        } catch (UnexpectedTokenException e) {
            fail(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (UndefinedFunctionException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UndefinedVariableException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void test2() {
        try {
            String src = readFile("data/fibonacci.d", StandardCharsets.UTF_8);

            Tokenizer t = new Tokenizer(src);

            Parser p = new Parser(t.scanTokens());

            Program prog = p.parse();

            System.out.println("\n===Fibonacci===");

            //System.out.println(prog);

            int val = prog.run();
            System.out.println("The result: " + val);

            assertEquals(144, val);

        } catch (UnexpectedCharacterException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UnexpectedTokenException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (UndefinedFunctionException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UndefinedVariableException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void test3() {
        try {
            String src = readFile("data/math.d", StandardCharsets.UTF_8);

            Tokenizer t = new Tokenizer(src);

            Parser p = new Parser(t.scanTokens());

            Program prog = p.parse();

            System.out.println("\n===Math===");
            //System.out.println(prog);

            int val = prog.run();
            System.out.println("The result: " + val);

            assertEquals(-1, val);

        } catch (UnexpectedCharacterException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UnexpectedTokenException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (UndefinedFunctionException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UndefinedVariableException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void test4() {
        try {
            String src = readFile("data/recursion.d", StandardCharsets.UTF_8);

            Tokenizer t = new Tokenizer(src);

            Parser p = new Parser(t.scanTokens());

            Program prog = p.parse();

            System.out.println("\n===Factorial===");
            //System.out.println(prog);

            int val = prog.run();
            System.out.println("The result: " + val);

            assertEquals(6, val);

        } catch (UnexpectedCharacterException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UnexpectedTokenException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (UndefinedFunctionException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UndefinedVariableException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    void test5() {
        try {
            String src = readFile("data/advantage.d", StandardCharsets.UTF_8);

            Tokenizer t = new Tokenizer(src);

            Parser p = new Parser(t.scanTokens());

            Program prog = p.parse();

            System.out.println("\n===Advantage===");
            //System.out.println(prog);

            int val = prog.run();
            System.out.println("The result: " + val);

        } catch (UnexpectedCharacterException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UnexpectedTokenException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (IOException e) {
            fail(e.getMessage());
        } catch (UndefinedFunctionException e) {
            e.printStackTrace();
            fail(e.getMessage());
        } catch (UndefinedVariableException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

}
