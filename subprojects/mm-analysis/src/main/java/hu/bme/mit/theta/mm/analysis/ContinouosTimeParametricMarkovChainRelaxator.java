package hu.bme.mit.theta.mm.analysis;

import hu.bme.mit.theta.mm.dsl.*;

import java.util.ArrayList;
import java.util.List;

public class ContinouosTimeParametricMarkovChainRelaxator {

    public final double uniformExitRate=25;

    private static ContinouosTimeParametricMarkovChainRelaxator ourInstance = new ContinouosTimeParametricMarkovChainRelaxator();

    public static ContinouosTimeParametricMarkovChainRelaxator getInstance() {
        return ourInstance;
    }
/*
    private ContinouosTimeParametricMarkovChainRelaxator() {
    }

    public ContinuousTimeParametricMarkovDecisionProcess relax(MarkovianModel markovianModel){
        ContinuousTimeParametricMarkovDecisionProcess continuousTimeParametricMarkovDecisionProcess=new ContinuousTimeParametricMarkovDecisionProcess(
                uniformExitRate,
                markovianModel.startingLocation,
                markovianModel.locationNumber,
                markovianModel.targetLocation);
        return continuousTimeParametricMarkovDecisionProcess;
    }


    public ContinuousTimeParametricMarkovDecisionProcess evaluate(ContinuousTimeParametricMarkovDecisionProcess continuousTimeParametricMarkovDecisionProcess){
        continuousTimeParametricMarkovDecisionProcess.commands.forEach(nondeterministicCommand -> generateAndGetActions(nondeterministicCommand));
        return continuousTimeParametricMarkovDecisionProcess;
    }

    public ContinuousTimeParametricMarkovDecisionProcess relaxAndEvaluate(MarkovianModel markovianModel){
        return evaluate(relax(markovianModel));
    }

    public void generateAndGetActions(NondeterministicCommand nondeterministicCommand){
        List<Parameter> parameterList = new ArrayList<>();
        int i=0;
        nondeterministicCommand.localParameters.map.forEach((key, value) -> {
           parameterList.add(value);
        });
        Parameter[] parameterArray=parameterList.toArray(new Parameter[parameterList.size()]);
        Update[] transitions=nondeterministicCommand.updates.toArray(new Update[nondeterministicCommand.updates.size()]);
        for(int actionBits=0;actionBits<nondeterministicCommand.actionNumber;actionBits++){
            for (int parameterCounter=0;parameterCounter<nondeterministicCommand.parameterNumber;parameterCounter++){ //beállítom az összes paramétert az alsó vagy felső határára
                int mask=1<<parameterCounter;
                int maskedBits=(mask&actionBits)>>parameterCounter;
                boolean actualBit=maskedBits==1;
                if(actualBit){
                    parameterArray[parameterCounter].limit= ParameterDirection.UP;
                }else {
                    parameterArray[parameterCounter].limit=ParameterDirection.LOW;
                }
            }
            for (int targetLocation = 0; targetLocation<nondeterministicCommand.updateNumber; targetLocation++){
                if(transitions[targetLocation]!=null){
                    nondeterministicCommand.rates[targetLocation][actionBits]=transitions[targetLocation].getRate().value;
                }
            }
        }
    }

*/

}
