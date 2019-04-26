package hu.bme.mit.theta.mm.analysis.solver.paramcheck;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.type.realtype.RealLitExpr;
import hu.bme.mit.theta.mm.analysis.solver.discrete.Discretisation;
import hu.bme.mit.theta.mm.analysis.solver.external.ExternalSolver;
import hu.bme.mit.theta.mm.analysis.solver.relax.RelaxSubstitute;
import hu.bme.mit.theta.mm.analysis.solver.uniform.Uniformisation;
import hu.bme.mit.theta.mm.model.*;
import hu.bme.mit.theta.mm.prop.Property;

import java.util.*;

public abstract class MarkovParameterChecker {
    protected Queue<ParameterSpace> fifo;
    private final int IterationLimit=60;
    private final double RelativeJordanMeasureLimit=0.9;
    private final ExternalSolver solver;
    private Map<ParamDecl<?>, Integer> cutNum;
    protected final Rate uniformRate=new Rate(RealLitExpr.of(60.1));


    protected MarkovParameterChecker(ExternalSolver solver){
        this.solver = solver;

        fifo=new LinkedList<>();

        cutNum =new HashMap<>();

    }


    protected abstract Property getSatisfyingProperty(Property property);
    protected abstract Property getUnsatisfyingProperty(Property property);

    protected abstract Collection<Reward<?>> getRewards();
    
    
    public Map<ParameterSpace, ParameterSpaceState> check(ParametricMarkovianModel mm,ParameterSpace parameterSpace,Property property){
        return check(mm,parameterSpace,getRewards(), getSatisfyingProperty(property), getUnsatisfyingProperty(property));
    }

    private Map<ParameterSpace,ParameterSpaceState> check(ParametricMarkovianModel mm,ParameterSpace parameterSpace,Collection<Reward<?>> rewards, Property satProperty, Property unsatProperty){

        Map<ParameterSpace,ParameterSpaceState> result=new HashMap<>();

        fifo.clear();
        fifo.add(parameterSpace);


        //initalising the "cutNum"
        cutNum.clear();
        for(ParamDecl<?> param:parameterSpace.parameters){
            cutNum.put(param,0);
        }


        Uniformisation uniformisation=Uniformisation.getInstance();
        RelaxSubstitute substitute=RelaxSubstitute.getInstance();
        Discretisation discretisation=Discretisation.getInstance();


        ParametricContinousTimeMarkovChain pctmc;
        ParametricMarkovianModel MM;//preprocessed Markovian Model


        final double AllJordanMeasure=parameterSpace.getJordanMeasure();
        double RelativeCoveredJordanMeasure=0;

        if(mm instanceof ParametricContinousTimeMarkovChain){
            pctmc=(ParametricContinousTimeMarkovChain) mm;
            ParametricContinousTimeMarkovChain upctmc=uniformisation.uniformisate(pctmc,uniformRate);
            ParametricDiscreteTimeMarkovChain dtmc=discretisation.discretisate(upctmc,uniformRate);
            MM=dtmc;
        }else {
            throw new UnsupportedOperationException("This kind of Markovian Model is not supported yet.");
        }

        Integer iterationDepth=0;
        do{

            //just for debugging
            System.out.println("Iteration: "+iterationDepth.toString());


            ParameterSpace localSpace=fifo.remove();

            //Markov Decision Process after all model transformations
            MarkovDecisionProcess mdp=substitute.relaxandsubstitute(MM,localSpace);

            if(solver.solveBinSingle(mdp,rewards,satProperty)){
                result.put(localSpace,ParameterSpaceState.SATISFYING);
                RelativeCoveredJordanMeasure+=(localSpace.getJordanMeasure()/AllJordanMeasure);
            }else if (solver.solveBinSingle(mdp,rewards,unsatProperty)) {
                result.put(localSpace,ParameterSpaceState.UNSATISFYING);
                RelativeCoveredJordanMeasure+=(localSpace.getJordanMeasure()/AllJordanMeasure);
            }else {
                ParamDecl<?> cutParam=searchCutParameter(cutNum);
                ParameterSpace lowSpace=localSpace.cutAndGetLowHalf(cutParam);
                ParameterSpace upSpace=localSpace.cutAndGetUpHalf(cutParam);
                fifo.add(lowSpace);
                fifo.add(upSpace);
                iterationDepth=searchIteration(cutNum);
                cutNum.put(cutParam,iterationDepth+1);
            }


        }while ( (!fifo.isEmpty()) &&
                    (RelativeCoveredJordanMeasure<RelativeJordanMeasureLimit) &&
                    (iterationDepth<IterationLimit)
        );

        while (!fifo.isEmpty()){
            result.put(fifo.remove(),ParameterSpaceState.NEUTRAL);
        }

        return result;
    }


    private ParamDecl<?> searchCutParameter(Map<ParamDecl<?>,Integer> cut){
        int minCut=Integer.MAX_VALUE;
        ParamDecl<?> maxParam=null;

        for (Map.Entry<ParamDecl<?>,Integer> e:cut.entrySet()){
            if (e.getValue()<minCut){
                minCut=e.getValue();
                maxParam=e.getKey();
            }
        }

        return maxParam;
    }

    private Integer searchIteration(Map<ParamDecl<?>,Integer> cut){
        Integer min=Integer.MAX_VALUE;


        for (Map.Entry<ParamDecl<?>,Integer> e:cut.entrySet()){
            if (e.getValue()<min){
                min=e.getValue();
            }
        }

        return min;
    }


}
