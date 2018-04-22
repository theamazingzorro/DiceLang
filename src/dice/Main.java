package dice;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import dice.error.DiceError;
import dice.parser.Parser;
import dice.program.Program;
import dice.tokenizer.Tokenizer;

public class Main {

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    static void runFile(String loc) {
        try {
            String src = readFile(loc, StandardCharsets.UTF_8);

            Tokenizer t = new Tokenizer(src);

            Parser p = new Parser(t.scanTokens());

            Program prog = p.parse();

            System.out.println("Result > " + prog.run());

        } catch (IOException e) {
            System.err.println("Invalid file path: " + loc);
        } catch (DiceError e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        if (args.length >= 1) {
            runFile(args[0]);
        } else {
            System.out.print("Enter the file location: ");
            Scanner scan = new Scanner(System.in);
            runFile(scan.nextLine().trim());
            scan.close();
        }

    }
}
