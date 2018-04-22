package dice.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

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
    void test() {

        try {
            String src = readFile("data/roll.d", StandardCharsets.UTF_8);

            Tokenizer t = new Tokenizer(src);

            Parser p = new Parser(t.scanTokens());

            Program prog = p.parse();

            System.out.println(prog);

            System.out.println("The result: " + prog.run());

        } catch (UnexpectedCharacterException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (UnexpectedTokenException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

}
