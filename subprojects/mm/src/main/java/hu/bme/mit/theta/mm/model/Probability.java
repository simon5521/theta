package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.realtype.RealLitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;

public class Probability {

    public final Expr<RealType> probExpr;

    public Probability(Expr<RealType> probExpr) {
        if(probExpr instanceof RealLitExpr){
            RealLitExpr expr=(RealLitExpr) probExpr;
            if (expr.getValue()<0) throw new UnsupportedOperationException("prob must greater than 0");
            if (expr.getValue()>1) throw new UnsupportedOperationException("prob must less than 1");
        }
        this.probExpr = probExpr;
    }
}
