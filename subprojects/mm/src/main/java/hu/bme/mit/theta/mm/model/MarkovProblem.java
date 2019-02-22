package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.mm.prop.Property;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkState;

public class MarkovProblem {
    public final MarkovianModel mm;
    public final ParameterSpace parameterSpace;
    public final Property property;
    public final Collection<Reward> rewards;

    public MarkovProblem(MarkovianModel mm, ParameterSpace parameterSpace, Property property, Collection<Reward> rewards) {
        this.mm = mm;
        this.parameterSpace = parameterSpace;
        this.property = property;
        this.rewards = rewards;
    }


    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private boolean built;

        private Collection<Reward> rewards;
        private MarkovianModel mm;
        private ParameterSpace parameterSpace;
        private Property property;


        private Builder(){
            rewards=new HashSet<>();
            mm=null;
            parameterSpace=null;
            property=null;
            built=false;
        }


        public MarkovProblem build(){
            checkNotBuilt();
            built=true;
            return new MarkovProblem(mm,parameterSpace,property,rewards);
        }

        public void setMM(MarkovianModel mm) {
            checkNotBuilt();
            this.mm = mm;
        }

        public void setParameterSpace(ParameterSpace parameterSpace) {
            checkNotBuilt();
            this.parameterSpace = parameterSpace;
        }

        public void setProperty(Property property) {
            checkNotBuilt();
            this.property = property;
        }

        public void addReward(Reward reward){
            checkNotBuilt();
            rewards.add(reward);
        }


        private void checkNotBuilt() {
            checkState(!built, "A Markov Chain was already built.");
        }

    }


}