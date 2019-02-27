package hu.bme.mit.theta.mm.analysis.solver;

import hu.bme.mit.theta.core.type.realtype.RealExprs;
import hu.bme.mit.theta.core.type.realtype.RealLitExpr;
import hu.bme.mit.theta.mm.analysis.solver.discrete.Discretisation;
import hu.bme.mit.theta.mm.analysis.solver.external.PRISMSolverLinux;
import hu.bme.mit.theta.mm.analysis.solver.external.StormSolver;
import hu.bme.mit.theta.mm.analysis.solver.paramcheck.ExpectedTimeChecker;
import hu.bme.mit.theta.mm.analysis.solver.relax.RelaxSubstitute;
import hu.bme.mit.theta.mm.analysis.solver.uniform.Uniformisation;
import hu.bme.mit.theta.mm.generator.MMPRISMWriter;
import hu.bme.mit.theta.mm.model.*;
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
import java.util.Map;

@RunWith(Parameterized.class)
public class MarkovSolverTest {


    @Parameterized.Parameter(0)
    public String mdpfilepath;

    @Parameterized.Parameter(1)
    public String propfilepath;

    @Parameterized.Parameter(3)
    public String prop2filepath;

    @Parameterized.Parameter(2)
    public String pctmcfilepath;


    private Reader mdpreader;
    private Reader pctmcreader;
    private Reader propreader;
    private Reader prop2reader;
    private MarkovianModelParser mdpparser;
    private MarkovianModelParser pctmcparser;
    private PropertyParser propparser;
    private PropertyParser prop2parser;
    private MMPRISMWriter writer;
    private PRISMSolverLinux prismSolverLinux;
    private StormSolver stormSolver;


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {

                { "src/test/resources/mdp_solve_test.lisp.mm",
                        "src/test/resources/mdp_solve_test.lisp.prop",
                        "src/test/resources/pctmc_solve_test.lisp.mm",
                        "src/test/resources/pctmc_solve_test.prop"},

        });
    }

    @Before
    public void before() throws FileNotFoundException {
        mdpreader = new FileReader(mdpfilepath);
        pctmcreader = new FileReader(pctmcfilepath);
        propreader = new FileReader(propfilepath);
        prop2reader = new FileReader(prop2filepath);
        mdpparser = new MarkovianModelParser(mdpreader);
        pctmcparser = new MarkovianModelParser(pctmcreader);
        writer = MMPRISMWriter.instance();
        prismSolverLinux = new PRISMSolverLinux();
        stormSolver = new StormSolver();
    }

    @After
    public void after() throws IOException {
        mdpreader.close();
    }


    @Test
    public void test(){
        /*DiscreteTimeMarkovDecisionProcess dtmdp= mdpparser.DTMDP();
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
        System.out.println("_______________________________________");
        */
        System.out.println("Reading the pmctmc");
        System.out.println("_______________________________________");
        ParametricContinousTimeMarkovChain pCTMC=pctmcparser.pCTMC();
        System.out.println(writer.PCTMC2PRISM(pCTMC));
        ParameterSpace parameterSpace=pctmcparser.parameterspace();
        prop2parser=new PropertyParser(prop2reader,pCTMC.variables);
        Property prop2=prop2parser.property();
        System.out.println(writer.Property2PRISM(prop2));
        ExpectedTimeChecker expectedTimeChecker=new ExpectedTimeChecker(new StormSolver());
        Map<ParameterSpace,ParameterSpaceState> result=expectedTimeChecker.check(pCTMC,parameterSpace,prop2);

        for (Map.Entry<ParameterSpace,ParameterSpaceState> entry:result.entrySet()){
            if (entry.getValue()==ParameterSpaceState.SATISFYING){
                System.out.println("Satisfying area found:");
                System.out.println(writer.ParameterSpace2PRISM(entry.getKey()));
            } else if (entry.getValue()==ParameterSpaceState.UNSATISFYING){
                System.out.println("Unsatisfying area found:");
                System.out.println(writer.ParameterSpace2PRISM(entry.getKey()));
            } else if (entry.getValue()==ParameterSpaceState.NEUTRAL){
                System.out.println("Neutral area:");
                System.out.println(writer.ParameterSpace2PRISM(entry.getKey()));
            }
        }

        System.out.println("_______________________________________");
        System.out.println("Uniformisating the pmctmc");
        System.out.println("_______________________________________");
        Uniformisation uniformisation=Uniformisation.getInstance();
        Rate uniformRate=new Rate(RealLitExpr.of(50));
        pCTMC=uniformisation.uniformisate(pCTMC,uniformRate);
        System.out.println(writer.PCTMC2PRISM(pCTMC));
        System.out.println("_______________________________________");
        System.out.println("Discetisating the upctmc");
        System.out.println("_______________________________________");
        Discretisation discretisation=Discretisation.getInstance();
        ParametricDiscreteTimeMarkovChain pdtmc=discretisation.discretisate(pCTMC,uniformRate);
        System.out.println(writer.PDTMC2PRISM(pdtmc));
        System.out.println("_______________________________________");
        System.out.println("Relaxing and substituting the pmctmc");
        System.out.println("_______________________________________");
        RelaxSubstitute relaxSubstitute=RelaxSubstitute.getInstance();

        DiscreteTimeMarkovDecisionProcess dtmdp=relaxSubstitute.relaxsubstitute(pdtmc,parameterSpace);
        System.out.println(writer.DTMDP2PRISM(dtmdp));


    }



}
