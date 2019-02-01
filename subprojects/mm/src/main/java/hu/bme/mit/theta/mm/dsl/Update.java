package hu.bme.mit.theta.mm.dsl;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.core.utils.ExprUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

public class Update {

    public final Collection<AssignStmt<?>> updateExpr;

    public final Rate rate;

    public Update(Collection<AssignStmt<?>> updateExpr, Rate rate){
        this.updateExpr = updateExpr;
        this.rate = rate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public LitExpr getRate(Valuation valuation){
        return rate.rateExpr.eval(valuation);
    }


    public void collectParams(Collection<ParamDecl<?>> collection){
        collection.addAll(ExprUtils.getParams(rate.rateExpr));
    }

    public Set<ParamDecl<?>> getParams(){
        return ExprUtils.getParams(rate.rateExpr);
    }



    public static final class Builder{
        private Rate rate;
        private Collection<AssignStmt<?>> updates;
        private boolean built;

        public Builder(){
            rate=null;
            updates=new HashSet<>();
            built=false;
        }


        public Update build(){
            checkNotBuilt();
            built=true;
            return new Update(updates,rate);
        }

        public void setRate(final Expr<RealType> rateExpr) {
            checkNotBuilt();
            this.rate=new Rate(rateExpr);
        }

        public void addStmt(AssignStmt<?> update){
            checkNotBuilt();
            updates.add(update);
        }


        private void checkNotBuilt() {
            checkState(!built, "Update was already built.");
        }

    }


}
