package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.Valuation;

import java.util.Collection;

public abstract class MarkovDecisionProcess<ModelCommand extends Command>  extends MarkovianModel<ModelCommand>{
    protected MarkovDecisionProcess(Collection<ModelCommand> modelCommands, Collection<VarDecl<?>> variables, Valuation lowerVarBound, Valuation upperVarBound, Valuation variableInitalisations) {
        super(modelCommands, variables, lowerVarBound, upperVarBound, variableInitalisations);
    }
}
