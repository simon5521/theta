package hu.bme.mit.theta.mm;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.core.utils.ExprUtils;

import java.util.Collection;
import java.util.Set;

public class Update {

    public final Expr<RealType> updateExpr;

    public final Rate rate;

    public Update(Expr<RealType> updateExpr, Rate rate){
        this.updateExpr = updateExpr;
        this.rate = rate;
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





}
