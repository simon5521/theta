package hu.bme.mit.theta.mm.analysis.solver;

import hu.bme.mit.theta.core.model.Substitution;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.realtype.RealExprs;
import hu.bme.mit.theta.core.type.realtype.RealLitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.analysis.solver.discrete.Discretisation;
import hu.bme.mit.theta.mm.analysis.solver.external.PRISMSolverLinux;
import hu.bme.mit.theta.mm.analysis.solver.external.StormSolver;
import hu.bme.mit.theta.mm.analysis.solver.locationarea.AddInitLocations;
import hu.bme.mit.theta.mm.analysis.solver.paramcheck.ExpectedTimeChecker;
import hu.bme.mit.theta.mm.analysis.solver.relax.RelaxSubstitute;
import hu.bme.mit.theta.mm.analysis.solver.uniform.Uniformisation;
import hu.bme.mit.theta.mm.generator.MMPRISMWriter;
import hu.bme.mit.theta.mm.model.*;
import hu.bme.mit.theta.mm.parser.MarkovianModelParser;
import hu.bme.mit.theta.mm.parser.NondetInitStateParser;
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
public class StrategyFinderTest {


    @Parameterized.Parameter(0)
    public String pctmcfilepath;

    @Parameterized.Parameter(1)
    public String propfilepath;

    @Parameterized.Parameter(2)
    public String initstatesfilepath;

    @Parameterized.Parameter(3)
    public String abstractmodelfilepath;


    private Reader PCTMCReader;
    private Reader PropReader;
    private Reader InitStatesReader;
    private Reader AbstractModelReader;


    private MarkovianModelParser pctmcparser;
    private NondetInitStateParser nondetinitparser;
    private PropertyParser propparser;
    private MMPRISMWriter writer;
    private PRISMSolverLinux prismSolverLinux;
    private StormSolver stormSolver;
    ExpectedTimeChecker expectedTimeChecker;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {

                {
                        "src/test/resources/server_queue_1.lisp.mm",
                        "src/test/resources/server_prop_1.lisp.prop",
                        "src/test/resources/server_init_states.lisp.ini",
                        "src/test/resources/server_config.lisp.mm"
                },

        });
    }


    @Before
    public void before() throws FileNotFoundException {
        PCTMCReader=new FileReader(pctmcfilepath);
        PropReader=new FileReader(propfilepath);
        InitStatesReader=new FileReader(initstatesfilepath);
        AbstractModelReader=new FileReader(abstractmodelfilepath);

        pctmcparser = new MarkovianModelParser(PCTMCReader);
        writer = MMPRISMWriter.instance();
        prismSolverLinux = new PRISMSolverLinux();
        stormSolver = new StormSolver();
        expectedTimeChecker=new ExpectedTimeChecker(new StormSolver());

    }


    @After
    public void after() throws IOException {
        PCTMCReader.close();
        PropReader.close();
        InitStatesReader.close();
        AbstractModelReader.close();
    }


    @Test
    public void test(){


        System.out.println("Reading the model of the server");
        System.out.println("-----------------------------------------------------");
        ParametricContinousTimeMarkovChain serverModel=pctmcparser.pCTMC();
        ParametricContinousTimeMarkovChain pCTMC=serverModel;
        System.out.println(writer.PCTMC2PRISM(serverModel));

        ParameterSpace parameterSpace=pctmcparser.parameterspace();

        Rate uniRate=new Rate(RealExprs.Real(2));
        Uniformisation uniformisation=Uniformisation.getInstance();
        RelaxSubstitute relaxSubstitute=RelaxSubstitute.getInstance();
        Discretisation discretisation=Discretisation.getInstance();
        AddInitLocations addInitLocations=AddInitLocations.getInstance();

        serverModel=uniformisation.uniformisate(serverModel,uniRate);
        System.out.println(writer.PCTMC2PRISM(serverModel));
        ParametricDiscreteTimeMarkovChain dserverMode=discretisation.discretisate(serverModel,uniRate);
        DiscreteTimeMarkovDecisionProcess serverMDP=relaxSubstitute.relaxsubstitute(dserverMode,parameterSpace);
        System.out.println("Relaxing the model and sustitute");
        System.out.println("-----------------------------------------------------");
        System.out.println(writer.DTMDP2PRISM(serverMDP));


        System.out.println("Adding multiple initialisation states");
        System.out.println("-----------------------------------------------------");
        nondetinitparser=new NondetInitStateParser(InitStatesReader,serverModel.variables);
        Collection<Valuation> nondetInitStates=nondetinitparser.NondetInitialStates();
        serverMDP=addInitLocations.addInitLocations(serverMDP,nondetInitStates);
        System.out.println(writer.DTMDP2PRISM(serverMDP));


        System.out.println("Adding multiple initialisation states and do parametric analysis");
        System.out.println("-----------------------------------------------------");
        propparser=new PropertyParser(PropReader,pCTMC.variables);
        Property property=propparser.property();


        Map<ParameterSpace,ParameterSpaceState> result=expectedTimeChecker.check(pCTMC,parameterSpace,property);

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


    }

}
