package hu.bme.mit.theta.mm.data;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.ImmutableValuation;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;
import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public abstract class MarkovianModel<ModelCommand extends Command> {

    public final int commandNumber;

    public final int variableNumber;

    public final int parameterNumber;

    public final Collection<Command> commands;

    public final Collection<VarDecl<?>> variables;

    public final Collection<ParamDecl<?>> parameters;

    public final Valuation variableInitalisations;



    protected MarkovianModel(Collection<Command> commands, Collection<VarDecl<?>> variables, Collection<ParamDecl<?>> parameters, Valuation variableInitalisations) {
        this.commands = commands;
        this.variables = variables;
        this.parameters = parameters;
        commandNumber=commands.size();
        variableNumber=variables.size();
        parameterNumber=parameters.size();
        this.variableInitalisations = variableInitalisations;
    }










}
