package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.mm.prop.Property;

public class MarkovProblem {
    public final ParametricContinousTimeMarkovChain pctmc;
    public final ParameterSpace parameterSpace;
    public final Property property;

    public MarkovProblem(ParametricContinousTimeMarkovChain pctmc, ParameterSpace parameterSpace, Property property) {
        this.pctmc = pctmc;
        this.parameterSpace = parameterSpace;
        this.property = property;
    }
}
