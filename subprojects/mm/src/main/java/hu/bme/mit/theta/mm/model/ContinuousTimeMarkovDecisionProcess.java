package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.ImmutableValuation;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

public class ContinuousTimeMarkovDecisionProcess extends MarkovDecisionProcess<ContinousCommand> {
    protected ContinuousTimeMarkovDecisionProcess(Collection<ContinousCommand> continousCommands,
                                                  Collection<VarDecl<?>> variables,
                                                  Valuation lowerVarBound,
                                                  Valuation upperVarBound,
                                                  Valuation variableInitalisations) {
        super(continousCommands, variables,lowerVarBound ,upperVarBound, variableInitalisations);
    }


    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {


        private boolean built;

        private Collection<ContinousCommand> commands;

        private Collection<VarDecl<?>> variables;

        private Valuation variableInitalisations;

        private ImmutableValuation.Builder initialisationBuilder;

        private ImmutableValuation.Builder lowerVarBoundBuilder;

        private ImmutableValuation.Builder upperVarBoundBuilder;



        private Builder(){
            commands=new HashSet<>();
            variables=new HashSet<>();
            variableInitalisations=null;
            initialisationBuilder= ImmutableValuation.builder();
            lowerVarBoundBuilder=ImmutableValuation.builder();
            upperVarBoundBuilder=ImmutableValuation.builder();
        }


        public ContinousCommand createCommand(ContinousCommand.Builder builder){
            checkNotBuilt();
            ContinousCommand command=builder.build();
            commands.add(command);
            return command;
        }

        public ContinousCommand addCommand(ContinousCommand command){
            checkNotBuilt();
            commands.add(command);
            return command;
        }


        public void addCommands(Collection<ContinousCommand> commands){
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


        public VarDecl<?> createVariable(VarDecl<?> variable, LitExpr<?> initialValue,LitExpr<?> lowerBound,LitExpr<?> upperBound){
            checkNotBuilt();
            checkArgument(variable!=null,"Variable must not be null!");
            checkArgument(initialValue!=null,"Initial value must not be null!");
            checkArgument(!variables.contains(variable),"Variable must be declared only once!");
            variables.add(variable);
            initialisationBuilder.put(variable,initialValue);
            lowerVarBoundBuilder.put(variable,lowerBound);
            upperVarBoundBuilder.put(variable,upperBound);
            return  variable;
        }

        public void addVarBounds(Valuation lowerBound,Valuation upperBounds){
            lowerVarBoundBuilder.putAll(lowerBound);
            upperVarBoundBuilder.putAll(upperBounds);
        }


        public ContinuousTimeMarkovDecisionProcess build() {
            checkNotBuilt();
            built = true;
            variableInitalisations=initialisationBuilder.build();
            return new ContinuousTimeMarkovDecisionProcess(this.commands, this.variables, lowerVarBoundBuilder.build(), upperVarBoundBuilder.build(), this.variableInitalisations);
        }


        private void checkNotBuilt() {
            checkState(!built, "A Markov Chain was already built.");
        }

    }
}