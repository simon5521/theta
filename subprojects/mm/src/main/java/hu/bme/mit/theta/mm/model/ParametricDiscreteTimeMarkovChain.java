package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.ImmutableValuation;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class ParametricDiscreteTimeMarkovChain extends ParametricMarkovianModel<DiscreteCommand> {
    protected ParametricDiscreteTimeMarkovChain(Collection collection, Collection variables, Collection parameters, Valuation variableInitalisations) {
        super(collection, variables, parameters, variableInitalisations);
    }



    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {


        private boolean built;

        private Collection<DiscreteCommand> commands;

        private Collection<VarDecl<?>> variables;

        private Collection<ParamDecl<?>> parameters;

        private Valuation variableInitalisations;

        private ImmutableValuation.Builder initialisationBuilder;



        private Builder(){
            commands=new HashSet<DiscreteCommand>();
            variables=new HashSet<>();
            parameters=new HashSet<>();
            variableInitalisations=null;
            initialisationBuilder=ImmutableValuation.builder();
        }


        public DiscreteCommand createCommand(DiscreteCommand.Builder builder){
            checkNotBuilt();
            DiscreteCommand command=builder.build();
            commands.add(command);
            return command;
        }


        public void addCommand(DiscreteCommand command){
            checkNotBuilt();
            this.commands.add(command);
        }

        public void addCommands(Collection<DiscreteCommand> commands){
            checkNotBuilt();
            this.commands.addAll(commands);
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


        public ParametricDiscreteTimeMarkovChain build() {
            checkNotBuilt();
            built = true;
            //collecting the parameters from the Commands
            for(DiscreteCommand command:commands){
                command.collectParams(parameters);
            }
            variableInitalisations=initialisationBuilder.build();
            return new ParametricDiscreteTimeMarkovChain(this.commands, this.variables, this.parameters, this.variableInitalisations);
        }


        private void checkNotBuilt() {
            checkState(!built, "A Markov Chain was already built.");
        }

    }


}
