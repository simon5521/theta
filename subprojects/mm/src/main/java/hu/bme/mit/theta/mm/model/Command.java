package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkState;

public abstract class Command<CommandUpdate extends hu.bme.mit.theta.mm.model.Update>  {

    public Collection<CommandUpdate> updates;

    public final int updateNumber;

    public final int parameterNumber;

    public final Expr<BoolType> guard;

    public final String action;



    protected Command(Collection<CommandUpdate> updates, Expr<BoolType> guard, String action){
        this.updateNumber = updates.size();
        this.updates=updates;
        this.guard = guard;
        this.action = action;
        parameterNumber=getParams().size();

    }


    public void collectParams(Collection<ParamDecl<?>> collection){
        for (CommandUpdate update:updates){
            update.collectParams(collection);
        }

    }

    public Collection<ParamDecl<?>> getParams(){
        Collection<ParamDecl<?>> paramDeclSet=new HashSet<>();
        collectParams(paramDeclSet);
        return paramDeclSet;
    }


}
