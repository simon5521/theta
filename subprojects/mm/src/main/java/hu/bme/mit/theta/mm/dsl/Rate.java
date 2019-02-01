package hu.bme.mit.theta.mm.dsl;


import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.realtype.RealType;

public class Rate {
    public final Expr<RealType> rateExpr;

    public Rate(Expr<RealType> rateExpr) {
        this.rateExpr = rateExpr;
    }
}
