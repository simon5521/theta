package hu.bme.mit.theta.mm.prop.templogic;

import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.BinaryExpr;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;

public class UntilExpr extends BinaryExpr<BoolType,BoolType> implements hu.bme.mit.theta.mm.prop.templogic.TemporalLogicExpr {

    private static final int HASH_SEED = 4536;
    private static final String OPERATOR_LABEL = "U";

    private UntilExpr(final Expr<BoolType> leftOp,Expr<BoolType> rightOp){
        super(leftOp,rightOp);
    }

    public static UntilExpr of(final Expr<BoolType> leftOp,Expr<BoolType> rightOp){return new UntilExpr(leftOp, rightOp);}

    @Override
    public BinaryExpr<BoolType, BoolType> with(Expr<BoolType> leftOp, Expr<BoolType> rightOp) {
        return of(leftOp, rightOp);
    }

    @Override
    public BinaryExpr<BoolType, BoolType> withLeftOp(Expr<BoolType> leftOp) {
        return with(leftOp, getRightOp());
    }

    @Override
    public BinaryExpr<BoolType, BoolType> withRightOp(Expr<BoolType> rightOp) {
        return with(getLeftOp(), rightOp);
    }

    @Override
    protected int getHashSeed() {
        return HASH_SEED;
    }

    @Override
    protected String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

    @Override
    public BoolType getType() {
        return BoolType.getInstance();
    }

    @Override
    public LitExpr<BoolType> eval(Valuation val) {
        throw new UnsupportedOperationException("Evaluation of temporal logic is not supported.");
    }
}
