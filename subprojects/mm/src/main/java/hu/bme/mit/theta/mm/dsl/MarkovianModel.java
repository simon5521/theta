package hu.bme.mit.theta.mm.dsl;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.ImmutableValuation;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class MarkovianModel {

    public final int commandNumber;

    public final int variableNumber;

    public final int parameterNumber;

    public final Collection<Command> commands;

    public final Collection<VarDecl<?>> variables;

    public final Collection<ParamDecl<?>> parameters;

    public final Valuation variableInitalisations;

    public final Type type;

    public MarkovianModel(Collection<Command> commands, Collection<VarDecl<?>> variables, Collection<ParamDecl<?>> parameters, Valuation variableInitalisations, Type type) {
        this.commands = commands;
        this.variables = variables;
        this.parameters = parameters;
        commandNumber=commands.size();
        variableNumber=variables.size();
        parameterNumber=parameters.size();
        this.variableInitalisations = variableInitalisations;
        this.type = type;
    }



    public static Builder builder() {
        return new Builder();
    }



    public enum Type{
        ContinuousTimeParametricMarkovChain, ContinuousTimeParametricMarkovDecisionProcess, ContinousTimeMarkovDecisionProcess, DiscreteTimeMarkovDecisionProcess
    }



    public static final class Builder {


        private boolean built;

        private Collection<Command> commands;

        private Collection<VarDecl<?>> variables;

        private Collection<ParamDecl<?>> parameters;

        private Valuation variableInitalisations;

        private ImmutableValuation.Builder initialisationBuilder;

        private Type type;


        private Builder(){
            commands=new HashSet<>();
            variables=new HashSet<>();
            parameters=new HashSet<>();
            variableInitalisations=null;
            initialisationBuilder=ImmutableValuation.builder();
        }


        public Command createCommand(Command.Builder builder){
            checkNotBuilt();
            Command command=builder.build();
            commands.add(command);
            return command;
        }

        public void addParameter(ParamDecl<?> paramDecl){
            checkNotBuilt();
            parameters.add(paramDecl);
        }

        public VarDecl<?> createVariable(VarDecl<?> variable, LitExpr<?> initialValue){
            checkNotBuilt();
            checkArgument(variable!=null,"Variable must not be null!");
            checkArgument(initialValue!=null,"Initial value must not be null!");
            checkArgument(!variables.contains(variable),"Variable must be declared only once!");
            variables.add(variable);
            initialisationBuilder.put(variable,initialValue);
            return  variable;
        }


        public MarkovianModel build() {
            checkNotBuilt();
            built = true;
            //collecting the parameters from the Commands
            for(Command command:commands){
                command.collectParams(parameters);
            }
            variableInitalisations=initialisationBuilder.build();
            return new MarkovianModel(this.commands, this.variables, this.parameters, this.variableInitalisations, type);
        }


        private void checkNotBuilt() {
            checkState(!built, "A Markov Chain was already built.");
        }


        public void setType(Type type) {
            this.type = type;
        }
    }

}
