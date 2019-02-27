package hu.bme.mit.theta.mm.analysis.solver;

import hu.bme.mit.theta.mm.model.MarkovianModel;
import hu.bme.mit.theta.mm.model.Reward;
import hu.bme.mit.theta.mm.prop.Property;

import java.util.Collection;

public interface MarkovSolver {

    boolean solveBinSingle(MarkovianModel markovianModel, Property property);
    double  solveDoubleSingle(MarkovianModel   markovianModel, Property property);

    boolean solveBinSingle(MarkovianModel markovianModel, Collection<Reward<?>> rewards, Property property);
    double  solveDoubleSingle(MarkovianModel   markovianModel, Collection<Reward<?>> rewards, Property property);

}
