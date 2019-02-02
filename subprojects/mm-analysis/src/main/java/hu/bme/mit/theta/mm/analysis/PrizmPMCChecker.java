package hu.bme.mit.theta.mm.analysis;

import java.io.IOException;

import hu.bme.mit.theta.mm.dsl.MarkovianModel;
import hu.bme.mit.theta.mm.dsl.ContinuousTimeParametricMarkovDecisionProcess;
import hu.bme.mit.theta.mm.dsl.NondeterministicCommand;
import hu.bme.mit.theta.mm.dsl.Update;

public class PrizmPMCChecker {
    private PrizmModellChecker prizmModellChecker;
    private MarkovianModel markovianModel;
    private Double keyProperty=null;


/*
    public PrizmPMCChecker(
            MarkovianModel markovianModel,
            Double keyProperty
    ) throws IOException {
        prizmModellChecker=new PrizmModellChecker();
        this.markovianModel = markovianModel;
        setKeyProperty(keyProperty);
    }
*/
    public void setMarkovianModel(MarkovianModel markovianModel) {
        this.markovianModel = markovianModel;
    }
/*
    public void setKeyProperty(Double keyProperty) throws IOException {
        if(keyProperty<=1.0 && keyProperty>=0.0)
            this.keyProperty = keyProperty;
        setPropertyFile();
    }
/*
    private void setPropertyFile() throws IOException {
        prizmModellChecker.setPropertyFileHigh(" Rmax=? [ F l = "+ markovianModel.targetLocation.toString()+" ] ");
        prizmModellChecker.setPropertyFileLow(" Rmin=? [ F l = "+ markovianModel.targetLocation.toString()+" ] ");
    }

    //uniformisation relaxation and substitution together (3 in 1)
    //most complex part of the program
    private String generatePrizmModell(){
        //relaxation
        ContinuousTimeParametricMarkovDecisionProcess continuousTimeParametricMarkovDecisionProcess=ContinouosTimeParametricMarkovChainRelaxator.getInstance().relaxAndEvaluate(markovianModel);
        //todo: updating the whole stuff
        StringBuilder prizmModell=new StringBuilder();
        prizmModell.append("mdp\n");
        prizmModell.append("module m1\n");
        prizmModell.append(" l : [0.."+ Integer.toString(markovianModel.locationNumber-1)+"] init "+ markovianModel.startingLocation.toString()+" ; \n");
        //todo: gyilkos triplaloop feloldása
        for(NondeterministicCommand nondeterministicCommand:continuousTimeParametricMarkovDecisionProcess.commands){
            if(sourceLocation== markovianModel.targetLocation){
                prizmModell.append("[] l = "+ Integer.toString(markovianModel.targetLocation)+" -> ( l'="+ Integer.toString(markovianModel.targetLocation)+"); \n");
            }else {
                for(Integer action = 0; action< nondeterministicCommand.actionNumber; action++){
                    prizmModell.append("[]"+nondeterministicCommand.guard+" -> ");
                    double localExitRate=0.0;
                    Update[] updates=nondeterministicCommand.updates.toArray(new Update[nondeterministicCommand.updates.size()]);
                    for (Integer targetLocation = 0; targetLocation< nondeterministicCommand.updates.size(); targetLocation++){
                        if(nondeterministicCommand.rates[targetLocation][action]!=0.0){
                            prizmModell.append(Double.toString((nondeterministicCommand.rates[targetLocation][action])/ markovianModel.uniformExitRate)+" : ("+updates[targetLocation].target+") + ");
                            localExitRate+=nondeterministicCommand.rates[targetLocation][action];
                        }
                    }
                    //adding uniformisation criteria
                    prizmModell.append(Double.toString((markovianModel.uniformExitRate-localExitRate)/ markovianModel.uniformExitRate)+" :(l'="+ Integer.toString(sourceLocation)+");\n");//todo semmi sem valtozik megvalosítása


                }
            }
        }
        prizmModell.append("endmodule \n");

        //adding rewards to the modell
        prizmModell.append("rewards \"expTime\"\n");
        prizmModell.append("[] l<"+ markovianModel.locationNumber+": "+ Double.toString(markovianModel.uniformExitExpectedTime)+";\n");
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
*/
}


