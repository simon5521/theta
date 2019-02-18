package hu.bme.mit.theta.core.type.templogic;

import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.TernaryExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.inttype.IntType;

import static hu.bme.mit.theta.core.type.booltype.BoolExprs.Bool;
import static hu.bme.mit.theta.core.type.inttype.IntExprs.Int;
import static hu.bme.mit.theta.core.utils.TypeUtils.cast;

public class BoundedUntilExpr extends TernaryExpr<BoolType, IntType,BoolType,BoolType> implements TemporalLogicExpr, BoundedTemporalLogic {


    private static final int HASH_SEED = 7536;
    private static final String OPERATOR_LABEL = "U<=";

    private BoundedUntilExpr(Expr<BoolType> op1, Expr<IntType> op2, Expr<BoolType> op3) {
        super(op1, op2, op3);
    }

    public static BoundedUntilExpr of(Expr<BoolType> op1, Expr<IntType> op2, Expr<BoolType> op3){
        return new BoundedUntilExpr(op1, op2, op3);
    }

    public static BoundedUntilExpr create(Expr<?> op1, Expr<?> op2,Expr<?> op3){
        Expr<BoolType> leftOp1=cast(op1,Bool());
        Expr<IntType> middleOp1=cast(op2,Int());
        Expr<BoolType> rightOp1=cast(op3,Bool());
        return of(leftOp1,middleOp1,rightOp1);
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
