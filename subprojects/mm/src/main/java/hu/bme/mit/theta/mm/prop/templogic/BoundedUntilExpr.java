package hu.bme.mit.theta.mm.prop.templogic;

import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.TernaryExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.inttype.IntType;
import hu.bme.mit.theta.mm.prop.templogic.TemporalLogicExpr;

public class BoundedUntilExpr extends TernaryExpr<BoolType, IntType,BoolType,BoolType> implements TemporalLogicExpr, hu.bme.mit.theta.mm.prop.templogic.BoundedTemporalLogic {


    private static final int HASH_SEED = 7536;
    private static final String OPERATOR_LABEL = "U<=";

    private BoundedUntilExpr(Expr<BoolType> op1, Expr<IntType> op2, Expr<BoolType> op3) {
        super(op1, op2, op3);
    }

    public BoundedUntilExpr of(Expr<BoolType> op1, Expr<IntType> op2, Expr<BoolType> op3){
        return new BoundedUntilExpr(op1, op2, op3);
    }


    @Override
    public TernaryExpr<BoolType, IntType, BoolType, BoolType> with(Expr<BoolType> op1, Expr<IntType> op2, Expr<BoolType> op3) {
        return of(op1, op2, op3);
    }

    @Override
    public TernaryExpr<BoolType, IntType, BoolType, BoolType> withOp1(Expr<BoolType> op1) {
        return of(op1,getOp2(),getOp3());
    }

    @Override
    public TernaryExpr<BoolType, IntType, BoolType, BoolType> withOp2(Expr<IntType> op2) {
        return of(getOp1(),op2,getOp3());
    }

    @Override
    public TernaryExpr<BoolType, IntType, BoolType, BoolType> withOp3(Expr<BoolType> op3) {
        return of(getOp1(),getOp2(),op3);
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
        return null;
    }

    @Override
    public Expr<IntType> getBound() {
        return getOp2();
    }
}
