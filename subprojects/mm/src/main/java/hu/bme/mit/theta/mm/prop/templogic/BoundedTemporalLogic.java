package hu.bme.mit.theta.mm.prop.templogic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.inttype.IntType;

public interface BoundedTemporalLogic {
    Expr<IntType> getBound();
}
