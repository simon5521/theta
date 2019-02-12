package hu.bme.mit.theta.mm.templogic;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.inttype.IntType;

public interface BoundedTemporalLogic {
    Expr<IntType> getBound();
}
