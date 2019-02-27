package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.ImmutableValuation;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class DiscreteTimeMarkovDecisionProcess extends MarkovDecisionProcess<DiscreteCommand> {
    protected DiscreteTimeMarkovDecisionProcess(Collection<DiscreteCommand> discreteCommands, Collection<VarDecl<?>> variables, Valuation variableInitalisations) {
        super(discreteCommands, variables, variableInitalisations);
    }


    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {


        private boolean built;

        private Collection<DiscreteCommand> commands;

        private Collection<VarDecl<?>> variables;

        private Valuation variableInitalisations;

        private ImmutableValuation.Builder initialisationBuilder;



        private Builder(){
            commands=new HashSet<DiscreteCommand>();
            variables=new HashSet<>();
            variableInitalisations=null;
            initialisationBuilder= ImmutableValuation.builder();
        }


        public DiscreteCommand createCommand(DiscreteCommand.Builder builder){
            checkNotBuilt();
            DiscreteCommand command=builder.build();
            commands.add(command);
            return command;
        }

        public DiscreteCommand addCommand(DiscreteCommand command){
            checkNotBuilt();
            this.commands.add(command);
            return command;
        }


        public void addCommands(Collection<DiscreteCommand> commands){
            checkNotBuilt();
            this.commands.addAll(commands);
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




        public DiscreteTimeMarkovDecisionProcess build() {
            checkNotBuilt();
            built = true;
            variableInitalisations=initialisationBuilder.build();
            return new DiscreteTimeMarkovDecisionProcess(this.commands, this.variables, this.variableInitalisations);
        }


        private void checkNotBuilt() {
            checkState(!built, "A Markov Chain was already built.");
        }

    }
}
