package hu.bme.mit.theta.mm.prop;

import hu.bme.mit.theta.core.decl.ConstDecl;
import hu.bme.mit.theta.mm.prop.arithmetic.OperatorArithmetic;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkState;

public class Property {

    public final Collection<ConstDecl<?>> constans;
    public final Collection<MultiObjective> multiObjectives;
    public final Collection<OperatorArithmetic<?>> objectives;

    private Property(Collection<ConstDecl<?>> constans, Collection<MultiObjective> multiObjectives, Collection<OperatorArithmetic<?>> objectives) {
        this.constans = constans;
        this.multiObjectives = multiObjectives;
        this.objectives = objectives;
    }

    public Builder builder(){
        return new Builder();
    }

    public static class Builder{

        private Collection<ConstDecl<?>> constans;
        private Collection<MultiObjective> multiObjectives;
        private Collection<OperatorArithmetic<?>> objectives;

        private boolean built;

        private Builder(){
            constans=new HashSet<>();
            multiObjectives=new HashSet<>();
            objectives=new HashSet<>();
            built=false;
        }

        public Property build(){
            checkNotBuilt();
            return new Property(constans,multiObjectives,objectives);
        }

        public void addConstant(ConstDecl<?> constDecl){
            checkNotBuilt();
            constans.add(constDecl);
        }

        public void addMultiObjective(MultiObjective multiObjective){
            checkNotBuilt();
            multiObjectives.add(multiObjective);
        }

        public void addOpetorArithmetic(OperatorArithmetic operatorArithmetic){
            checkNotBuilt();
            objectives.add(operatorArithmetic);
        }


        private void checkNotBuilt() {
            checkState(!built, "Property was already built.");
        }


    }

}
