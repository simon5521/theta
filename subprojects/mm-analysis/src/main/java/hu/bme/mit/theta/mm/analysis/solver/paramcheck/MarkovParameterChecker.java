package hu.bme.mit.theta.mm.analysis.solver.paramcheck;

import hu.bme.mit.theta.mm.model.ParameterSpace;

import java.util.Queue;

public abstract class MarkovParameterChecker {
    protected Queue<ParameterSpace> fifo;
    private final int IterationLimit=10;
}
