package hu.bme.mit.theta.core.type.templogic;

import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.NullaryExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;

public class SteadyStateRewardExpr extends NullaryExpr<BoolType> implements TemporalLogicExpr {
    private static SteadyStateRewardExpr ourInstance = new SteadyStateRewardExpr();

    public static SteadyStateRewardExpr getInstance() {
        return ourInstance;
    }

    private SteadyStateRewardExpr() {
    }

    @Override
    public BoolType getType() {
        return BoolType.getInstance();
    }

    @Override
    public LitExpr<BoolType> eval(Valuation val) {
        throw new UnsupportedOperationException("Steady state reward evaluation is not supported yet.");
    }
}
