package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;

public class StateRewardCommand extends RewardCommand {
    @Override
    public Type getType() {
        return Type.STATE;
    }

    public StateRewardCommand(Expr<BoolType> guard, Expr<RealType> reward) {
        super(guard, reward);
    }
}
