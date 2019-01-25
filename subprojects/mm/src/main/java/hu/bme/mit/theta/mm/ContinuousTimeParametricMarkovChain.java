package hu.bme.mit.theta.mm;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.type.realtype.RealType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContinuousTimeParametricMarkovChain {

    public final int commandNumber;

    public final int variableNumber;

    public final int parameterNumber;

    public final Collection<Command> commands;

    public final Collection<VarDecl<?>> variables;

    public final Collection<ParamDecl<RealType>> parameters;

    public ContinuousTimeParametricMarkovChain(Collection<Command> commands, Collection<VarDecl<?>> variables, Collection<ParamDecl<RealType>> parameters) {
        this.commands = commands;
        this.variables = variables;
        this.parameters = parameters;
        commandNumber=commands.size();
        variableNumber=variables.size();
        parameterNumber=parameters.size();
    }



}
