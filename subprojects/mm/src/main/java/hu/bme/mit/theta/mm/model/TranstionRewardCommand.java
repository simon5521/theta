package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;

public class TranstionRewardCommand extends RewardCommand {

    public final String name;

    public TranstionRewardCommand(Expr<BoolType> guard, Expr<RealType> reward, String name) {
        super(guard, reward);
        this.name = name;
    }

    @Override
    public Type getType() {
        return Type.EDGE;
    }
}
