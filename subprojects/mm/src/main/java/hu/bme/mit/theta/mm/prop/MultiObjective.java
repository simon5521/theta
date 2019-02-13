package hu.bme.mit.theta.mm.prop;

import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.mm.prop.arithmetic.OperatorArithmetic;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class MultiObjective  {

    public final Collection<OperatorArithmetic<BoolType>> objectives;


    private MultiObjective(Collection<OperatorArithmetic<BoolType>> objectives) {
        this.objectives = objectives;
    }

    public Builder builder(){return new Builder();}

    public static final class Builder{

        private Collection<OperatorArithmetic<BoolType>> objectives;
        private boolean built;

        private Builder(){
            objectives=new HashSet<>();
            built=false;
        }

        public void addObjective(OperatorArithmetic<BoolType> objective){
            checkNotBuilt();
            checkNotNull(objective);
            objectives.add(objective);
        }

        public MultiObjective build(){
            checkNotBuilt();
            built=true;
            return new MultiObjective(objectives);
        }


        private void checkNotBuilt() {
            checkState(!built, "MultiObjective was already built.");
        }


    }


}
