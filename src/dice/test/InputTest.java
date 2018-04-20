package dice.test;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import dice.tokenizer.Input;

class InputTest {

    private final String WHITESPACE = " \n\r\t";

    @Test
    void testString() {
        Input i = new Input("hello; h: ne\nxt", this.WHITESPACE);

        try {
            assertEquals("hello;", i.nextWord());
            assertEquals("h:", i.nextWord());
            assertEquals("ne", i.nextWord());
            assertEquals("xt", i.nextWord());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void testFile() {
        Input i = new Input(new File("test_data/input1.txt"), this.WHITESPACE);

        try {
            assertEquals("a", i.nextWord());
            assertEquals("sample", i.nextWord());
            assertEquals("txt.", i.nextWord());
            assertEquals("document", i.nextWord());
            assertEquals("ok;", i.nextWord());
            assertEquals("", i.nextWord());
        } catch (IOException e) {
            fail("failed to load file");
        }
    }

}
