package hu.bme.mit.theta.core.type.templogic;

import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.NullaryExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;

public class TotalRewardExpr extends NullaryExpr<BoolType> implements TemporalLogicExpr{


    private static final int HASH_SEED = 3525;
    private static final String OPERATOR_LABEL = "C";

    private static final TotalRewardExpr Instance=new TotalRewardExpr();


    public static int getHashSeed() {
        return HASH_SEED;
    }

    public static String getOperatorLabel() {
        return OPERATOR_LABEL;
    }

    private TotalRewardExpr(){}

    public static TotalRewardExpr getInstance(){
        return Instance;
    }


    @Override
    public BoolType getType() {
        return BoolType.getInstance();
    }

    @Override
    public LitExpr<BoolType> eval(Valuation val) {
        throw new UnsupportedOperationException("C can not be evaluated");
    }
}
