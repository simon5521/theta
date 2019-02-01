package hu.bme.mit.theta.mm.analysis;


import hu.bme.mit.theta.mm.dsl.ContinuousTimeParametricMarkovChain;

public class ContinuousTimeParametricMarkovChainLinearisator {
    private static ContinuousTimeParametricMarkovChainLinearisator ourInstance = new ContinuousTimeParametricMarkovChainLinearisator();

    public static ContinuousTimeParametricMarkovChainLinearisator getInstance() {
        return ourInstance;
    }

    private ContinuousTimeParametricMarkovChainLinearisator() {
    }


    //there are the important stuff
    public static ContinuousTimeParametricMarkovChain linearisate(ContinuousTimeParametricMarkovChain continuousTimeParametricMarkovChain) {
        //todo: add linearisation
        return continuousTimeParametricMarkovChain;
    }
}