package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.realtype.RealType;

public class Probability {

    public final Expr<RealType> probExpr;

    public Probability(Expr<RealType> probExpr) {
        this.probExpr = probExpr;
    }
}
