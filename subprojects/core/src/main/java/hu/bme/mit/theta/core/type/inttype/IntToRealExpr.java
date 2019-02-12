package hu.bme.mit.theta.core.type.inttype;

import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.abstracttype.CastExpr;
import hu.bme.mit.theta.core.type.rattype.RatLitExpr;
import hu.bme.mit.theta.core.type.rattype.RatType;
import hu.bme.mit.theta.core.type.realtype.RealLitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;

import static hu.bme.mit.theta.core.type.inttype.IntExprs.Int;
import static hu.bme.mit.theta.core.type.rattype.RatExprs.Rat;
import static hu.bme.mit.theta.core.type.realtype.RealExprs.Real;
import static hu.bme.mit.theta.core.utils.TypeUtils.cast;

public class IntToRealExpr extends CastExpr<IntType, RealType> {

    private static final int HASH_SEED = 1657;
    private static final String OPERATOR_LABEL = "to_real";

    private IntToRealExpr(final Expr<IntType> op) {
        super(op);
    }

    public static IntToRealExpr of(final Expr<IntType> op) {
        return new IntToRealExpr(op);
    }

    public static IntToRatExpr create(final Expr<?> op) {
        final Expr<IntType> newOp = cast(op, Int());
        return IntToRatExpr.of(newOp);
    }

    @Override
    public RealType getType() {
        return Real();
    }

    @Override
    public RealLitExpr eval(final Valuation val) {
        final IntLitExpr opVal = (IntLitExpr) getOp().eval(val);
        return opVal.toReal();
    }

    @Override
    public IntToRealExpr with(final Expr<IntType> op) {
        if (op == getOp()) {
            return this;
        } else {
            return IntToRealExpr.of(op);
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof IntToRatExpr) {
            final IntToRatExpr that = (IntToRatExpr) obj;
            return this.getOp().equals(that.getOp());
        } else {
            return false;
        }
    }

    @Override
    protected int getHashSeed() {
        return HASH_SEED;
    }

    @Override
    protected String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

}
