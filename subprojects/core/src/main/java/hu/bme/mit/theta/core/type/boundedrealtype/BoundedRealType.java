package hu.bme.mit.theta.core.type.boundedrealtype;

import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;

public final class BoundedRealType extends RealType {

    public LitExpr<RealType> LowerLimits;
    public LitExpr<RealType> UpperLimits;

    private BoundedRealType(){
        super();
    }

}
