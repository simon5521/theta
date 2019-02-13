package hu.bme.mit.theta.mm.prop.templogic;


import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.UnaryExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.mm.prop.templogic.TemporalLogicExpr;

public class NextExpr extends UnaryExpr<BoolType,BoolType> implements TemporalLogicExpr {

    private static final int HASH_SEED = 7459;
    private static final String OPERATOR_LABEL = "X";

    private NextExpr(Expr<BoolType> op){
        super(op);
    }

    public static NextExpr of(final Expr<BoolType> op){return new NextExpr(op);}

    @Override
    public UnaryExpr<BoolType, BoolType> with(Expr<BoolType> op) {
        if (op == getOp()) {
            return this;
        } else {
            return NextExpr.of(op);
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
