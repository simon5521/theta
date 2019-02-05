package hu.bme.mit.theta.core.type.realtype;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.abstracttype.*;
import hu.bme.mit.theta.core.type.realtype.RealType;

public class RealType  implements Additive<RealType>, Multiplicative<RealType>, Equational<RealType>, Ordered<RealType>,
        Castable<RealType> {

    private static final RealType INSTANCE = new RealType();
    private static final int HASH_SEED = 5521;
    private static final String TYPE_LABEL = "Real";


    protected RealType(){

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
        return RealExprs.Add(ops);
    }

    @Override
    public SubExpr<RealType> Sub(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return RealExprs.Sub(leftOp,rightOp);
    }

    @Override
    public NegExpr<RealType> Neg(Expr<RealType> op) {
        return RealExprs.Neg(op);
    }

    @Override
    public <TargetType extends Type> Expr<TargetType> Cast(Expr<RealType> op, TargetType type) {
        return null;
    }

    @Override
    public EqExpr<RealType> Eq(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return RealExprs.Eq(leftOp,rightOp);
    }

    @Override
    public NeqExpr<RealType> Neq(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return RealExprs.Neq(leftOp,rightOp);
    }

    @Override
    public MulExpr<RealType> Mul(Iterable<? extends Expr<RealType>> ops) {
        return RealExprs.Mul(ops);
    }

    @Override
    public DivExpr<RealType> Div(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return RealExprs.Div(leftOp,rightOp);
    }

    @Override
    public LtExpr<RealType> Lt(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return RealExprs.Lt(leftOp, rightOp);
    }

    @Override
    public LeqExpr<RealType> Leq(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return RealExprs.Leq(leftOp, rightOp);
    }

    @Override
    public GtExpr<RealType> Gt(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return RealExprs.Gt(leftOp, rightOp);
    }

    @Override
    public GeqExpr<RealType> Geq(Expr<RealType> leftOp, Expr<RealType> rightOp) {
        return RealExprs.Geq(leftOp, rightOp);
    }
}
