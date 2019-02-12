package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.type.LitExpr;

import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public abstract class Update {

    public final Collection<AssignStmt<?>> updateExpr;


    protected Update(Collection<AssignStmt<?>> updateExpr){
        this.updateExpr = updateExpr;
    }

    public abstract LitExpr getRate(Valuation valuation);


    public abstract void collectParams(Collection<ParamDecl<?>> collection);

    public abstract  Set<ParamDecl<?>> getParams();




}
