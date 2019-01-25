package hu.bme.mit.theta.mm;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.type.realtype.RealType;

import java.util.*;

public class ContinuousTimeParametricMarkovDecisionProcess{

    public final int actionNumber;

    public final int commandNumber;

    public final int variableNumber;

    public final int parameterNumber;

    public final Collection<NondeterministicCommand> commands;

    public final Collection<VarDecl<?>> variables;

    public final Collection<ParamDecl<RealType>> parameters;

    public ContinuousTimeParametricMarkovDecisionProcess(Collection<NondeterministicCommand> commands, Collection<VarDecl<?>> variables, Collection<ParamDecl<RealType>> parameters) {
        this.commands = commands;
        this.variables = variables;
        this.parameters = parameters;
        commandNumber=commands.size();
        variableNumber=variables.size();
        parameterNumber=parameters.size();
        actionNumber=calculateActionNumber();
    }


    private int  calculateActionNumber(){
        int maxActionNumber=0;
        for (NondeterministicCommand command:commands){

            if(command.actionNumber>maxActionNumber){
                maxActionNumber= command.actionNumber;
            }

        }
        return maxActionNumber;
    }


}
