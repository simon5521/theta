package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkState;

public class ContinousCommand extends Command<ContinuousUpdate> {

    protected ContinousCommand(Collection<ContinuousUpdate> continuousUpdates, Expr<BoolType> guard, String action) {
        super(continuousUpdates, guard, action);
    }

    public static final class Builder{


        private Collection<ContinuousUpdate> updates;

        private Expr<BoolType> guard;

        private String action;

        private boolean built;


        public Builder(){
            updates=new HashSet<>();
            guard=null;
            action=null;
            built=false;
        }

        public ContinousCommand build(){
            checkNotBuilt();
            built=true;
            return new ContinousCommand(updates,guard,action);
        }

        public void setAction(String action) {
            checkNotBuilt();
            this.action = action;
        }

        public void setGuard(Expr<BoolType> guard) {
            checkNotBuilt();
            this.guard = guard;
        }

        public ContinuousUpdate createUpdate(ContinuousUpdate.Builder builder){
            checkNotBuilt();
            ContinuousUpdate update=builder.build();
            updates.add(update);
            return update;
        }

        public void addUpdate(ContinuousUpdate update){
            updates.add(update);
        }

        public void addUpdates(Collection<ContinuousUpdate> updates){
            this.updates.addAll(updates);
        }

        private void checkNotBuilt() {
            checkState(!built, "Command was already built.");
        }

    }
}
