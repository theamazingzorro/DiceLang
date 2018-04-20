package dice.tokenizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Provides an enhanced interface for an InputStream.
 *
 * @author Fox Noland
 */
public class Input {

    /**
     * The file input stream.
     */
    private Reader in;

    /**
     * A string of the characters that can separate words.
     */
    private String delimiters;

    /**
     * Creates a new Input, which will read from the filepath given, and
     * separate words based on the given delimiter String.
     *
     * @param f
     *            the file to be read
     * @param delimiters
     *            a String of the valid word separating characters
     */
    public Input(File f, String delimiters) {

        try {
            this.in = new FileReader(f);
        } catch (FileNotFoundException e) {
            System.err.println("Invalid input file: " + f.getAbsoluteFile());
            e.printStackTrace();
        }

        this.delimiters = delimiters;
    }

    /**
     * Creates a new Input, which will read from the given String, and separate
     * words based on the given delimiter String.
     *
     * @param s
     *            the contents to be read
     * @param delimiters
     *            a String of the valid word separating characters
     */
    public Input(String s, String delimiters) {

        this.in = new StringReader(s);

        this.delimiters = delimiters;
    }

    /**
     * Reads from the start of the input stream, until it finds the first word,
     * where a word is a sequence of characters not in the delimiter String, but
     * surrounded by characters from that delimiter String on both sides.
     *
     * @return the next word from the input stream
     * @throws IOException
     */
    public String nextWord() throws IOException {
        String word = "";

        int next = this.in.read();
        if (next != -1) {
            char c = (char) next;

            // burn off initial delimiter chars
            while (next != -1 && this.delimiters.indexOf((char) next) >= 0) {
                next = this.in.read();
            }

            if (next != -1) {
                c = (char) next;

                // continue adding chars to word until EOS or delimiter char
                while (this.delimiters.indexOf(c) < 0) {
                    word += c;

                    next = this.in.read();

                    if (next == -1) {
                        c = '\n';
                    } else {
                        c = (char) next;
                    }
                }
            }

        }

        return word;
    }
}
