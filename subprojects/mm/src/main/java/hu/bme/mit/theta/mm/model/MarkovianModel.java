package hu.bme.mit.theta.mm.model;


import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.Valuation;

import java.util.Collection;


public abstract class MarkovianModel<ModelCommand extends Command> {

    public final int commandNumber;

    public final int variableNumber;


    public final Collection<ModelCommand> commands;

    public final Collection<VarDecl<?>> variables;

    public final Valuation lowerVarBound;

    public final Valuation upperVarBound;

    public final Valuation variableInitalisations;


    protected MarkovianModel(Collection<ModelCommand> commands, Collection<VarDecl<?>> variables, Valuation lowerVarBound, Valuation upperVarBound, Valuation variableInitalisations) {
        this.commands = commands;
        this.variables = variables;
        commandNumber=commands.size();
        variableNumber=variables.size();
        this.lowerVarBound = lowerVarBound;
        this.upperVarBound = upperVarBound;
        this.variableInitalisations = variableInitalisations;
    }










}
