package hu.bme.mit.theta.mm;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Command {

    public Collection<Update> updates;

    public final int updateNumber;

    public final int parameterNumber;

    public final Expr<BoolType> guard;

    public final Collection<AssignStmt<?>> action;



    public Command(List<Update> updates, Expr<BoolType> guard, Collection<AssignStmt<?>> action){
        this.updateNumber = updates.size();
        this.updates=updates;
        this.guard = guard;
        this.action = action;
        parameterNumber=getParams().size();

    }


    private void collectParams(Collection<ParamDecl<?>> collection){
        updates.forEach(update -> {update.collectParams(collection);});
    }

    private Collection<ParamDecl<?>> getParams(){
        Set<ParamDecl<?>> paramDeclSet=new HashSet<>();
        collectParams(paramDeclSet);
        return paramDeclSet;
    }


}
