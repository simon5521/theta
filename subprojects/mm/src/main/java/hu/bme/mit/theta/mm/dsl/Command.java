package hu.bme.mit.theta.mm.dsl;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class Command {

    public Collection<Update> updates;

    public final int updateNumber;

    public final int parameterNumber;

    public final Expr<BoolType> guard;

    public final String action;



    public Command(Collection<Update> updates, Expr<BoolType> guard, String action){
        this.updateNumber = updates.size();
        this.updates=updates;
        this.guard = guard;
        this.action = action;
        parameterNumber=getParams().size();

    }


    public void collectParams(Collection<ParamDecl<?>> collection){
        updates.forEach(update -> {update.collectParams(collection);});
    }

    public Collection<ParamDecl<?>> getParams(){
        Set<ParamDecl<?>> paramDeclSet=new HashSet<>();
        collectParams(paramDeclSet);
        return paramDeclSet;
    }

    public static final class Builder{


        private Collection<Update> updates;

        private Expr<BoolType> guard;

        private String action;

        private boolean built;


        public Builder(){
            updates=new HashSet<>();
            guard=null;
            action=null;
            built=false;
        }

        public Command build(){
            checkNotBuilt();
            built=true;
            return new Command(updates,guard,action);
        }

        public void setAction(String action) {
            checkNotBuilt();
            this.action = action;
        }

        public void setGuard(Expr<BoolType> guard) {
            checkNotBuilt();
            this.guard = guard;
        }

        public Update createUpdate(Update.Builder builder){
            checkNotBuilt();
            Update update=builder.build();
            updates.add(update);
            return update;
        }

        public void addUpdate(Update update){
            updates.add(update);
        }

        private void checkNotBuilt() {
            checkState(!built, "Command was already built.");
        }

    }

}
