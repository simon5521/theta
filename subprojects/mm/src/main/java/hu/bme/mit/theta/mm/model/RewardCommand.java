package hu.bme.mit.theta.mm.model;

import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.realtype.RealType;

public abstract class RewardCommand {

    public final Expr<BoolType> guard;
    public final Expr<RealType> reward;

    public enum Type{
        STATE,EDGE
    }

    public abstract Type getType();

    public RewardCommand(Expr<BoolType> guard, Expr<RealType> reward) {
        this.guard = guard;
        this.reward = reward;
    }
}
