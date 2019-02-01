import hu.bme.mit.theta.common.parser.LispParser;
import hu.bme.mit.theta.core.parser.Env;
import hu.bme.mit.theta.mm.parser.MarkovianModelInterpreter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public final class MarkovianModelInterpreterTest {

    public static String filepath="src/test/resources/mm_test-lips.mm";

    private static Reader reader;
    private static MarkovianModelInterpreter interpreter;
    private static LispParser lispParser;

    public static void main() throws FileNotFoundException {

        reader = new FileReader(filepath);
        interpreter = new MarkovianModelInterpreter(new Env());

    }

}
