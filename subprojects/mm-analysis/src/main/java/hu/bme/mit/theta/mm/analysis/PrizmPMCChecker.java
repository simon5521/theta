package hu.bme.mit.theta.mm.analysis;

import java.io.IOException;

import hu.bme.mit.theta.mm.dsl.ContinuousTimeParametricMarkovChain;
import hu.bme.mit.theta.mm.dsl.ContinuousTimeParametricMarkovDecisionProcess;
import hu.bme.mit.theta.mm.dsl.NondeterministicCommand;
import hu.bme.mit.theta.mm.dsl.Update;

public class PrizmPMCChecker {
    private PrizmModellChecker prizmModellChecker;
    private ContinuousTimeParametricMarkovChain continuousTimeParametricMarkovChain;
    private Double keyProperty=null;



    public PrizmPMCChecker(
            ContinuousTimeParametricMarkovChain continuousTimeParametricMarkovChain,
            Double keyProperty
    ) throws IOException {
        prizmModellChecker=new PrizmModellChecker();
        this.continuousTimeParametricMarkovChain = continuousTimeParametricMarkovChain;
        setKeyProperty(keyProperty);
    }

    public void setContinuousTimeParametricMarkovChain(ContinuousTimeParametricMarkovChain continuousTimeParametricMarkovChain) {
        this.continuousTimeParametricMarkovChain = continuousTimeParametricMarkovChain;
    }

    public void setKeyProperty(Double keyProperty) throws IOException {
        if(keyProperty<=1.0 && keyProperty>=0.0)
            this.keyProperty = keyProperty;
        setPropertyFile();
    }

    private void setPropertyFile() throws IOException {
        prizmModellChecker.setPropertyFileHigh(" Rmax=? [ F l = "+ continuousTimeParametricMarkovChain.targetLocation.toString()+" ] ");
        prizmModellChecker.setPropertyFileLow(" Rmin=? [ F l = "+ continuousTimeParametricMarkovChain.targetLocation.toString()+" ] ");
    }

    //uniformisation relaxation and substitution together (3 in 1)
    //most complex part of the program
    private String generatePrizmModell(){
        //relaxation
        ContinuousTimeParametricMarkovDecisionProcess continuousTimeParametricMarkovDecisionProcess=ContinouosTimeParametricMarkovChainRelaxator.getInstance().relaxAndEvaluate(continuousTimeParametricMarkovChain);
        //todo: updating the whole stuff
        StringBuilder prizmModell=new StringBuilder();
        prizmModell.append("mdp\n");
        prizmModell.append("module m1\n");
        prizmModell.append(" l : [0.."+ Integer.toString(continuousTimeParametricMarkovChain.locationNumber-1)+"] init "+ continuousTimeParametricMarkovChain.startingLocation.toString()+" ; \n");
        //todo: gyilkos triplaloop feloldása
        for(NondeterministicCommand nondeterministicCommand:continuousTimeParametricMarkovDecisionProcess.commands){
            if(sourceLocation== continuousTimeParametricMarkovChain.targetLocation){
                prizmModell.append("[] l = "+ Integer.toString(continuousTimeParametricMarkovChain.targetLocation)+" -> ( l'="+ Integer.toString(continuousTimeParametricMarkovChain.targetLocation)+"); \n");
            }else {
                for(Integer action = 0; action< nondeterministicCommand.actionNumber; action++){
                    prizmModell.append("[]"+nondeterministicCommand.guard+" -> ");
                    double localExitRate=0.0;
                    Update[] updates=nondeterministicCommand.updates.toArray(new Update[nondeterministicCommand.updates.size()]);
                    for (Integer targetLocation = 0; targetLocation< nondeterministicCommand.updates.size(); targetLocation++){
                        if(nondeterministicCommand.rates[targetLocation][action]!=0.0){
                            prizmModell.append(Double.toString((nondeterministicCommand.rates[targetLocation][action])/ continuousTimeParametricMarkovChain.uniformExitRate)+" : ("+updates[targetLocation].target+") + ");
                            localExitRate+=nondeterministicCommand.rates[targetLocation][action];
                        }
                    }
                    //adding uniformisation criteria
                    prizmModell.append(Double.toString((continuousTimeParametricMarkovChain.uniformExitRate-localExitRate)/ continuousTimeParametricMarkovChain.uniformExitRate)+" :(l'="+ Integer.toString(sourceLocation)+");\n");//todo semmi sem valtozik megvalosítása


                }
            }
        }
        prizmModell.append("endmodule \n");

        //adding rewards to the modell
        prizmModell.append("rewards \"expTime\"\n");
        prizmModell.append("[] l<"+ continuousTimeParametricMarkovChain.locationNumber+": "+ Double.toString(continuousTimeParametricMarkovChain.uniformExitExpectedTime)+";\n");
        prizmModell.append("endrewards\n");
        return prizmModell.toString();
    }

    public boolean checkHigh() throws Exception {
        return prizmModellChecker.checkModellHigh(generatePrizmModell());
    }

    public boolean checkLow() throws Exception {
        return prizmModellChecker.checkModellLow(generatePrizmModell());
    }

    public double calculateMinProbability() throws IOException {
        return prizmModellChecker.calculateMinProbability(generatePrizmModell());
    }

    public double calculateMaxProbability() throws IOException {
        return prizmModellChecker.calculateMaxProbability(generatePrizmModell());
    }

}


