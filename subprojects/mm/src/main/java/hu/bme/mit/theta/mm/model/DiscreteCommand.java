package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkState;

public class DiscreteCommand extends Command<DiscreteUpdate> {
    protected DiscreteCommand(Collection<DiscreteUpdate> discreteUpdates, Expr<BoolType> guard, String action) {
        super(discreteUpdates, guard, action);
    }

    public static Builder builder(){
        return new Builder();
    }


    public static final class Builder{


        private Collection<DiscreteUpdate> updates;

        private Expr<BoolType> guard;

        private String action;

        private boolean built;


        public Builder(){
            updates=new HashSet<>();
            guard=null;
            action=null;
            built=false;
        }

        public DiscreteCommand build(){
            checkNotBuilt();
            built=true;
            return new DiscreteCommand(updates,guard,action);
        }

        public void setAction(String action) {
            checkNotBuilt();
            this.action = action;
        }

        public void setGuard(Expr<BoolType> guard) {
            checkNotBuilt();
            this.guard = guard;
        }

        public DiscreteUpdate createUpdate(DiscreteUpdate.Builder builder){
            checkNotBuilt();
            DiscreteUpdate update=builder.build();
            updates.add(update);
            return update;
        }

        public void addUpdate(DiscreteUpdate update){
            updates.add(update);
        }

        public void addUpdates(Collection<DiscreteUpdate> updates){
            this.updates.addAll(updates);
        }

        private void checkNotBuilt() {
            checkState(!built, "Command was already built.");
        }

    }
}
