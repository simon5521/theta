package hu.bme.mit.theta.mm.analysis.solver;

import hu.bme.mit.theta.mm.model.MarkovianModel;
import hu.bme.mit.theta.mm.prop.Property;

public interface MarkovSolver {

    boolean solveBinSingle(MarkovianModel markovianModel, Property property);
    double  solveDoubleSingle(MarkovianModel   markovianModel, Property property);

}
