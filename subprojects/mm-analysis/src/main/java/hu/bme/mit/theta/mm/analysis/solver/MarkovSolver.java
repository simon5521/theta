package hu.bme.mit.theta.mm.analysis.solver;

import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.dsl.MarkovianModel;
import hu.bme.mit.theta.mm.prop.arithmetic.OperatorArithmetic;

public interface MarkovSolver {

    public abstract boolean solveSingleDiscrete(MarkovianModel markovianModel, OperatorArithmetic<BoolType> singleProperty);

    public abstract double  solveSingleDouble(MarkovianModel   markovianModel, OperatorArithmetic<RealType> singleProperty);

}
