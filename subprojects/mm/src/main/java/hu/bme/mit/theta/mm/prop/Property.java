package hu.bme.mit.theta.mm.prop;

import hu.bme.mit.theta.core.decl.ConstDecl;
import hu.bme.mit.theta.core.model.ImmutableValuation;
import hu.bme.mit.theta.core.type.LitExpr;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkState;

public class Property {

    public final Collection<ConstDecl<?>> constans;
    public final Collection<MultiObjective> multiObjectives;
    public final Collection<Objective> objectives;
    public final ImmutableValuation constInitialisations;

    private Property(Collection<ConstDecl<?>> constans, Collection<MultiObjective> multiObjectives, Collection<Objective> objectives, ImmutableValuation constInitialisations) {
        this.constans = constans;
        this.multiObjectives = multiObjectives;
        this.objectives = objectives;
        this.constInitialisations = constInitialisations;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private Collection<ConstDecl<?>> constans;
        private Collection<MultiObjective> multiObjectives;
        private Collection<Objective> objectives;
        private ImmutableValuation.Builder contValueBuilder;

        private boolean built;

        private Builder(){
            constans=new HashSet<>();
            multiObjectives=new HashSet<>();
            objectives=new HashSet<>();
            contValueBuilder=ImmutableValuation.builder();
            built=false;
        }

        public Property build(){
            checkNotBuilt();
            return new Property(constans,multiObjectives,objectives, contValueBuilder.build());
        }

        public void addConstant(ConstDecl<?> constDecl, LitExpr<?> value){
            checkNotBuilt();
            constans.add(constDecl);
            contValueBuilder.put(constDecl,value);
        }

        public void addMultiObjective(MultiObjective multiObjective){
            checkNotBuilt();
            multiObjectives.add(multiObjective);
        }

        public void addObjective(Objective objective){
            checkNotBuilt();
            objectives.add(objective);
        }


        private void checkNotBuilt() {
            checkState(!built, "Property was already built.");
        }


    }

}
