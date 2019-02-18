package hu.bme.mit.theta.mm.parser;



import hu.bme.mit.theta.mm.model.DiscreteTimeMarkovDecisionProcess;
import hu.bme.mit.theta.mm.generator.MMPRISMWriter;
import hu.bme.mit.theta.mm.prop.Property;
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
    public String mmfilepath;

    @Parameter(1)
    public String propfilepath;


    private Reader mmreader;
    private Reader propreader;
    private MarkovianModelParser mmparser;
    private PropertyParser propparser;
    private MMPRISMWriter writer;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {

                //{ "src/test/resources/pctmc_test.lisp.mm"},
                { "src/test/resources/dtmdp_test.lisp.mm", "/home/simon5521/theta/subprojects/mm/src/test/resources/property.lisp.prop"},


        });
    }

    @Before
    public void before() throws FileNotFoundException {
        mmreader = new FileReader(mmfilepath);
        propreader = new FileReader(propfilepath);
        mmparser = new MarkovianModelParser(mmreader);
        writer = MMPRISMWriter.instance();
    }

    @After
    public void after() throws IOException {
        mmreader.close();
    }


    @Test
    public void test(){
        //ParametricContinousTimeMarkovChain pCTMC=parser.pCTMC();
        //System.out.println(writer.PCTMC2PRISM(pCTMC));
        DiscreteTimeMarkovDecisionProcess dtmdp=mmparser.DTMDP();
        propparser=new PropertyParser(propreader,dtmdp.variables);
        Property prop=propparser.property();
        System.out.println(writer.DTMDP2PRISM(dtmdp));
        System.out.println("_______________________________________");
        System.out.println(writer.Property2PRISM(prop));
    }



}
