package hu.bme.mit.theta.mm.analysis.solver;

import hu.bme.mit.theta.mm.analysis.solver.external.PRISMSolverLinux;
import hu.bme.mit.theta.mm.analysis.solver.external.StormSolver;
import hu.bme.mit.theta.mm.generator.MMPRISMWriter;
import hu.bme.mit.theta.mm.model.DiscreteTimeMarkovDecisionProcess;
import hu.bme.mit.theta.mm.parser.MarkovianModelParser;
import hu.bme.mit.theta.mm.parser.PropertyParser;
import hu.bme.mit.theta.mm.prop.Property;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class MarkovSolverTest {


    @Parameterized.Parameter(0)
    public String mmfilepath;

    @Parameterized.Parameter(1)
    public String propfilepath;


    private Reader mmreader;
    private Reader propreader;
    private MarkovianModelParser mmparser;
    private PropertyParser propparser;
    private MMPRISMWriter writer;
    private PRISMSolverLinux prismSolverLinux;
    private StormSolver stormSolver;


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {

                //{ "src/test/resources/pctmc_test.lisp.mm"},
                { "src/test/resources/mdp_solve_test.lisp.mm", "src/test/resources/mdp_solve_test.lisp.prop"},

        });
    }

    @Before
    public void before() throws FileNotFoundException {
        mmreader = new FileReader(mmfilepath);
        propreader = new FileReader(propfilepath);
        mmparser = new MarkovianModelParser(mmreader);
        writer = MMPRISMWriter.instance();
        prismSolverLinux = new PRISMSolverLinux();
        stormSolver = new StormSolver();
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
        System.out.println("Solving the MDP with external PRISM modelchecker:");
        System.out.println("_______________________________________");
        Double minProbPrism=prismSolverLinux.solveDoubleSingle(dtmdp,prop);
        System.out.println("Result is: "+minProbPrism.toString());
        System.out.println("_______________________________________");
        System.out.println("Solving the MDP with external Storm modelchecker:");
        System.out.println("_______________________________________");
        Double minProbStorm=stormSolver.solveDoubleSingle(dtmdp,prop);
        System.out.println("Result is: "+minProbStorm.toString());
    }



}
