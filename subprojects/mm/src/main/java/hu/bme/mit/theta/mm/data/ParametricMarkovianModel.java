package hu.bme.mit.theta.mm.data;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.mm.Parametric;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class ParametricMarkovianModel<ModellCommand extends Command> extends MarkovianModel<ModellCommand> implements Parametric {

    protected ParametricMarkovianModel(Collection collection, Collection variables, Collection parameters, Valuation variableInitalisations) {
        super(collection, variables, variableInitalisations);
        this.parameters=parameters;
        parameterNumber=parameters.size();

    }

    public final Collection<ParamDecl<?>> parameters;



    public final int parameterNumber;
}
