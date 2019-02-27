package hu.bme.mit.theta.mm.analysis.solver.paramcheck;

import hu.bme.mit.theta.core.decl.ConstDecl;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.booltype.TrueExpr;
import hu.bme.mit.theta.core.type.oparithmetic.GT;
import hu.bme.mit.theta.core.type.oparithmetic.LT;
import hu.bme.mit.theta.core.type.oparithmetic.OperatorArithmetic;
import hu.bme.mit.theta.core.type.operator.RewardOperator;
import hu.bme.mit.theta.core.type.realtype.RealExprs;
import hu.bme.mit.theta.core.type.realtype.RealLitExpr;
import hu.bme.mit.theta.mm.analysis.solver.external.ExternalSolver;
import hu.bme.mit.theta.mm.analysis.solver.external.StormSolver;
import hu.bme.mit.theta.mm.model.Reward;
import hu.bme.mit.theta.mm.model.StateRewardCommand;
import hu.bme.mit.theta.mm.prop.Objective;
import hu.bme.mit.theta.mm.prop.Property;

import java.util.Collection;
import java.util.HashSet;

public class ExpectedTimeChecker extends MarkovParameterChecker {
    protected ExpectedTimeChecker(ExternalSolver solver) {
        super(solver);
    }

    private final static ExpectedTimeChecker Instance=new ExpectedTimeChecker(new StormSolver());

    public static ExpectedTimeChecker getInstance() {
        return Instance;
    }

    @Override
    protected Property getSatisfyingProperty(Property property) {
        //this algoritm analyse only the first objective
        Objective objective=property.objectives.iterator().next();
        Property.Builder prBuilder=Property.builder();

        OperatorArithmetic<BoolType> opArth;
        if (objective.operatorArithmetic instanceof GT){
            GT gtArithmetic=(GT) objective.operatorArithmetic;
            opArth=GT.create(RewardOperator.getINSTANCE(),gtArithmetic.opExpr,gtArithmetic.pathProp);
            prBuilder.addObjective(new Objective(opArth,"ExpextedTimeMin"));


            for (ConstDecl<?> constant:property.constans){
                prBuilder.addConstant(constant,property.constInitialisations.eval(constant).get());
            }

            return prBuilder.build();
        } else if (objective.operatorArithmetic instanceof LT){
            LT gtArithmetic=(LT) objective.operatorArithmetic;
            opArth=LT.create(RewardOperator.getINSTANCE(),gtArithmetic.opExpr,gtArithmetic.pathProp);
            prBuilder.addObjective(new Objective(opArth,"ExpextedTimeMax"));


            for (ConstDecl<?> constant:property.constans){
                prBuilder.addConstant(constant,property.constInitialisations.eval(constant).get());
            }

            return prBuilder.build();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    protected Property getUnsatisfyingProperty(Property property) {
        //this algoritm analyse only the first objective
        Objective objective=property.objectives.iterator().next();
        Property.Builder prBuilder=Property.builder();

        OperatorArithmetic<BoolType> opArth;
        if (objective.operatorArithmetic instanceof GT){
            LT gtArithmetic=(LT) objective.operatorArithmetic;
            opArth=LT.create(RewardOperator.getINSTANCE(),gtArithmetic.opExpr,gtArithmetic.pathProp);
            prBuilder.addObjective(new Objective(opArth,"ExpextedTimeMax"));


            for (ConstDecl<?> constant:property.constans){
                prBuilder.addConstant(constant,property.constInitialisations.eval(constant).get());
            }

            return prBuilder.build();
        } else if (objective.operatorArithmetic instanceof LT){
            GT gtArithmetic=(GT) objective.operatorArithmetic;
            opArth=GT.create(RewardOperator.getINSTANCE(),gtArithmetic.opExpr,gtArithmetic.pathProp);
            prBuilder.addObjective(new Objective(opArth,"ExpextedTimeMin"));


            for (ConstDecl<?> constant:property.constans){
                prBuilder.addConstant(constant,property.constInitialisations.eval(constant).get());
            }

            return prBuilder.build();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    protected Collection<Reward<?>> getRewards() {
        Reward.Builder<StateRewardCommand> builder=new Reward.Builder<StateRewardCommand>();
        builder.addCommand(new StateRewardCommand(TrueExpr.getInstance(), RealExprs.Div(RealLitExpr.of(1),uniformRate.rateExpr)));

        Collection<Reward<?>> rewards=new HashSet<>();
        rewards.add(builder.build());
        return rewards;
    }
}
