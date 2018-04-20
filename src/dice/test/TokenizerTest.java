package dice.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import dice.error.UnexpectedCharacterException;
import dice.tokenizer.Token;
import dice.tokenizer.Token.TokenType;
import dice.tokenizer.Tokenizer;

class TokenizerTest {

    @Test
    void test1() {
        Tokenizer t = new Tokenizer("print 5 * 7");

        List<Token> tokens = null;
        try {
            tokens = t.scanTokens();
        } catch (UnexpectedCharacterException e) {
            fail(e.getMessage());
        }

        List<Token> expectedTokens = new ArrayList<>();
        expectedTokens.add(new Token("print", TokenType.PRINT));
        expectedTokens.add(new Token("5", TokenType.NUMBER));
        expectedTokens.add(new Token("", TokenType.STAR));
        expectedTokens.add(new Token("7", TokenType.NUMBER));
        expectedTokens.add(new Token("", TokenType.EOF));

        assertEquals(expectedTokens, tokens);
    }

    @Test
    void test2() {
        Tokenizer t = new Tokenizer("if (ifVariable9){\n return 3*d(3); \n}");

        List<Token> tokens = null;
        try {
            tokens = t.scanTokens();
        } catch (UnexpectedCharacterException e) {
            fail(e.getMessage());
        }

        List<Token> expectedTokens = new ArrayList<>();
        expectedTokens.add(new Token("if", TokenType.IF));
        expectedTokens.add(new Token("", TokenType.L_PAREN));
        expectedTokens.add(new Token("ifVariable9", TokenType.IDENTIFIER));
        expectedTokens.add(new Token("", TokenType.R_PAREN));
        expectedTokens.add(new Token("", TokenType.L_BRACE));
        expectedTokens.add(new Token("return", TokenType.RETURN));
        expectedTokens.add(new Token("3", TokenType.NUMBER));
        expectedTokens.add(new Token("", TokenType.STAR));
        expectedTokens.add(new Token("", TokenType.DICE_OP));
        expectedTokens.add(new Token("", TokenType.L_PAREN));
        expectedTokens.add(new Token("3", TokenType.NUMBER));
        expectedTokens.add(new Token("", TokenType.R_PAREN));
        expectedTokens.add(new Token("", TokenType.SEMICOLON));
        expectedTokens.add(new Token("", TokenType.R_BRACE));
        expectedTokens.add(new Token("", TokenType.EOF));

        assertEquals(expectedTokens, tokens);
        assertEquals(1, tokens.get(0).line());
        assertEquals(1, tokens.get(4).line());
        assertEquals(2, tokens.get(5).line());
        assertEquals(2, tokens.get(12).line());
        assertEquals(3, tokens.get(13).line());
    }

    @Test
    void test3() {
        Tokenizer t = new Tokenizer("d(3) = d ( 3 )");

        List<Token> tokens = null;
        try {
            tokens = t.scanTokens();
        } catch (UnexpectedCharacterException e) {
            fail(e.getMessage());
        }

        List<Token> expectedTokens = new ArrayList<>();
        expectedTokens.add(new Token("", TokenType.DICE_OP));
        expectedTokens.add(new Token("", TokenType.L_PAREN));
        expectedTokens.add(new Token("3", TokenType.NUMBER));
        expectedTokens.add(new Token("", TokenType.R_PAREN));
        expectedTokens.add(new Token("", TokenType.EQUALS));
        expectedTokens.add(new Token("", TokenType.DICE_OP));
        expectedTokens.add(new Token("", TokenType.L_PAREN));
        expectedTokens.add(new Token("3", TokenType.NUMBER));
        expectedTokens.add(new Token("", TokenType.R_PAREN));
        expectedTokens.add(new Token("", TokenType.EOF));

        assertEquals(expectedTokens, tokens);
    }

    @Test
    void test4() {
        Tokenizer t = new Tokenizer("variable <- d ( 3 )");

        List<Token> tokens = null;
        try {
            tokens = t.scanTokens();
        } catch (UnexpectedCharacterException e) {
            fail(e.getMessage());
        }

        List<Token> expectedTokens = new ArrayList<>();
        expectedTokens.add(new Token("variable", TokenType.IDENTIFIER));
        expectedTokens.add(new Token("", TokenType.ARROW));
        expectedTokens.add(new Token("", TokenType.DICE_OP));
        expectedTokens.add(new Token("", TokenType.L_PAREN));
        expectedTokens.add(new Token("3", TokenType.NUMBER));
        expectedTokens.add(new Token("", TokenType.R_PAREN));
        expectedTokens.add(new Token("", TokenType.EOF));

        assertEquals(expectedTokens, tokens);
    }

    @Test
    void test5() {
        Tokenizer t = new Tokenizer("= <= >= > < <- ! !=");

        List<Token> tokens = null;
        try {
            tokens = t.scanTokens();
        } catch (UnexpectedCharacterException e) {
            fail(e.getMessage());
        }

        List<Token> expectedTokens = new ArrayList<>();
        expectedTokens.add(new Token("", TokenType.EQUALS));
        expectedTokens.add(new Token("", TokenType.LESSER_EQUAL));
        expectedTokens.add(new Token("", TokenType.GREATER_EQUAL));
        expectedTokens.add(new Token("", TokenType.GREATER));
        expectedTokens.add(new Token("", TokenType.LESSER));
        expectedTokens.add(new Token("", TokenType.ARROW));
        expectedTokens.add(new Token("", TokenType.NOT));
        expectedTokens.add(new Token("", TokenType.NOT_EQUALS));
        expectedTokens.add(new Token("", TokenType.EOF));

        assertEquals(expectedTokens, tokens);
    }

    @Test
    void test6() {
        Tokenizer t = new Tokenizer(
                "and or main func if while else andormainfunc");

        List<Token> tokens = null;
        try {
            tokens = t.scanTokens();
        } catch (UnexpectedCharacterException e) {
            fail(e.getMessage());
        }

        List<Token> expectedTokens = new ArrayList<>();
        expectedTokens.add(new Token("and", TokenType.AND));
        expectedTokens.add(new Token("or", TokenType.OR));
        expectedTokens.add(new Token("main", TokenType.MAIN));
        expectedTokens.add(new Token("func", TokenType.FUNC));
        expectedTokens.add(new Token("if", TokenType.IF));
        expectedTokens.add(new Token("while", TokenType.WHILE));
        expectedTokens.add(new Token("else", TokenType.ELSE));
        expectedTokens.add(new Token("andormainfunc", TokenType.IDENTIFIER));
        expectedTokens.add(new Token("", TokenType.EOF));

        assertEquals(expectedTokens, tokens);
    }

    @Test
    void test7() {
        Tokenizer t = new Tokenizer("++\n test;\n .");

        List<Token> tokens = null;
        try {
            tokens = t.scanTokens();
            fail("Expected error");
        } catch (UnexpectedCharacterException e) {
            assertEquals(3, e.getLine());
        }
    }
}
