package hu.bme.mit.theta.core.type.templogic;

import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.UnaryExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;

import static hu.bme.mit.theta.core.type.booltype.BoolExprs.Bool;
import static hu.bme.mit.theta.core.utils.TypeUtils.cast;


//this operator sometimes called eventually
public class FutureExpr  extends UnaryExpr<BoolType,BoolType> implements TemporalLogicExpr {
    private static final int HASH_SEED = 3652;
    private static final String OPERATOR_LABEL = "F";

    public FutureExpr(Expr<BoolType> op){
        super(op);
    }

    public static FutureExpr of(final Expr<BoolType> op){return new FutureExpr(op);}

    public static FutureExpr create(Expr<?> op){
        Expr<BoolType> leftOp1=cast(op,Bool());
        return of(leftOp1);
    }


    @Override
    public UnaryExpr<BoolType, BoolType> with(Expr<BoolType> op) {
        if (op == getOp()) {
            return this;
        } else {
            return FutureExpr.of(op);
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

    @Override
    public BoolType getType() {
        return BoolType.getInstance();
    }

    @Override
    public LitExpr<BoolType> eval(Valuation val) {
        throw new UnsupportedOperationException("Evaluation is not supported for temporal logic.");
    }
}