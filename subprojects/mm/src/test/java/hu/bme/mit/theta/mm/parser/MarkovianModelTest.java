package hu.bme.mit.theta.mm.parser;



import hu.bme.mit.theta.mm.data.ParametricContinousTimeMarkovChain;
import hu.bme.mit.theta.mm.dsl.MarkovianModel;
import hu.bme.mit.theta.mm.generator.MMPRISMWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;



@RunWith(Parameterized.class)
public final class MarkovianModelTest {


    @Parameter(0)
    public String filepath;


    private Reader reader;
    private MarkovianModelParser parser;
    private MMPRISMWriter writer;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {

                { "src/test/resources/mm_test.lisp.mm"},

        });
    }

    @Before
    public void before() throws FileNotFoundException {
        reader = new FileReader(filepath);
        parser = new MarkovianModelParser(reader);
        writer = MMPRISMWriter.instance();
    }

    @After
    public void after() throws IOException {
        reader.close();
    }


    @Test
    public void test(){
        ParametricContinousTimeMarkovChain pCTMC=parser.markovianModel();
        System.out.println(writer.PCTMC2PRISM(pCTMC));
    }



}
