package hu.bme.mit.theta.core.type.realtype;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.abstracttype.*;
import hu.bme.mit.theta.core.type.realtype.RealType;

public final class RealType  implements Additive<RealType>, Multiplicative<RealType>, Equational<RealType>, Ordered<RealType>,
        Castable<RealType> {

    private static final RealType INSTANCE = new RealType();
    private static final int HASH_SEED = 5521;
    private static final String TYPE_LABEL = "Real";


    private RealType(){

    }

    public static RealType getInstance() {
        return INSTANCE;
    }

    @Override
    public int hashCode() {
        return HASH_SEED;
    }

    @Override
    public boolean equals(final Object obj) {
        return (obj instanceof RealType);
    }

    @Override
    public String toString() {
        return TYPE_LABEL;
    }

    ////


    @Override
    public AddExpr<RealType> Add(Iterable<? extends Expr<RealType>> ops) {
        return null;
    }

    @Override
    public SubExpr<RealType> Sub(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return null;
    }

    @Override
    public NegExpr<RealType> Neg(Expr<RealType> op) {
        return null;
    }

    @Override
    public <TargetType extends Type> Expr<TargetType> Cast(Expr<RealType> op, TargetType type) {
        return null;
    }

    @Override
    public EqExpr<RealType> Eq(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return null;
    }

    @Override
    public NeqExpr<RealType> Neq(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return null;
    }

    @Override
    public MulExpr<RealType> Mul(Iterable<? extends Expr<RealType>> ops) {
        return null;
    }

    @Override
    public DivExpr<RealType> Div(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return null;
    }

    @Override
    public LtExpr<RealType> Lt(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return null;
    }

    @Override
    public LeqExpr<RealType> Leq(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return null;
    }

    @Override
    public GtExpr<RealType> Gt(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return null;
    }

    @Override
    public GeqExpr<RealType> Geq(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return null;
    }
}
