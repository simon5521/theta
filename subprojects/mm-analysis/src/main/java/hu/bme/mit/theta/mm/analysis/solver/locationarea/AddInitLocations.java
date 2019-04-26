package hu.bme.mit.theta.mm.analysis.solver.locationarea;

import hu.bme.mit.theta.core.decl.Decl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.ImmutableValuation;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.booltype.BoolExprs;
import hu.bme.mit.theta.core.type.inttype.IntEqExpr;
import hu.bme.mit.theta.core.type.inttype.IntExprs;
import hu.bme.mit.theta.core.type.inttype.IntLitExpr;
import hu.bme.mit.theta.core.type.inttype.IntType;
import hu.bme.mit.theta.core.type.realtype.RealLitExpr;
import hu.bme.mit.theta.mm.model.*;
import hu.bme.mit.theta.xta.utils.RangeType;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class AddInitLocations {

    /*
     * This transformation class can add multiple nondeterministic initial state to the MDP.
     * Use a Valuation to desrcibe an initial state.
     * You don not have to initialise every PRISM variable.
     * In a collection you can add the set of initial states.
     */


    private static AddInitLocations ourInstance = new AddInitLocations();

    private static final RangeType InitRangeType=RangeType.Range(0,1);

    private static Integer ID=0;

    private static final VarDecl<?> initIndicator=new VarDecl<>("InitVariable3212",InitRangeType);

    private static final VarDecl<IntType> _initIndicator=new VarDecl<>("InitVariable3212",IntType.getInstance());

    public static AddInitLocations getInstance() {
        return ourInstance;
    }

    private AddInitLocations() {
    }

    public ContinuousTimeMarkovDecisionProcess addInitLocations(ContinuousTimeMarkovDecisionProcess markovDecisionProcess,Collection<Valuation> initialStates){
        ContinuousTimeMarkovDecisionProcess.Builder builder=ContinuousTimeMarkovDecisionProcess.builder();
        builder.addCommands(generateContCommads(initialStates));
        builder.addCommands(modifyContCommands(markovDecisionProcess.commands));
        for(Map.Entry<Decl<?>, LitExpr<?>> entry:markovDecisionProcess.variableInitalisations.toMap().entrySet()){
            builder.createVariable((VarDecl<?>) entry.getKey(),entry.getValue());
        }
        builder.createVariable(initIndicator, IntLitExpr.of(0));
        return builder.build();
    }

    public DiscreteTimeMarkovDecisionProcess addInitLocations(DiscreteTimeMarkovDecisionProcess markovDecisionProcess,Collection<Valuation> initialStates){
        DiscreteTimeMarkovDecisionProcess.Builder builder=DiscreteTimeMarkovDecisionProcess.builder();
        builder.addCommands(generateDiscCommads(initialStates));
        builder.addCommands(modifyDiscCommands(markovDecisionProcess.commands));
        for(Map.Entry<Decl<?>, LitExpr<?>> entry:markovDecisionProcess.variableInitalisations.toMap().entrySet()){
            builder.createVariable((VarDecl<?>) entry.getKey(),entry.getValue());
        }
        builder.createVariable(initIndicator, IntLitExpr.of(0));
        return builder.build();
    }


    private Reward<?> generateReward(Reward<?> reward){
        if(reward.getType()== RewardCommand.Type.EDGE){
            Reward<TranstionRewardCommand> reward1=(Reward<TranstionRewardCommand>) reward;
            Reward.Builder<TranstionRewardCommand> builder=reward1.builder();
            for (TranstionRewardCommand command: reward1.commands){
                builder.addCommand(
                        new TranstionRewardCommand(
                                BoolExprs.And(
                                        command.guard,
                                        IntExprs.Eq(
                                                _initIndicator.getRef(),
                                                IntLitExpr.of(1)
                                        )
                                ),
                                command.reward,
                                command.name
                        )
                );
            }
            builder.addCommand(new TranstionRewardCommand(IntExprs.Eq(_initIndicator.getRef(),IntLitExpr.of(0)),RealLitExpr.of(0.0),"nondet_init_command"));
            return builder.build();
        }
        if(reward.getType()== RewardCommand.Type.STATE){
            Reward<StateRewardCommand> reward1=(Reward<StateRewardCommand>) reward;
            Reward.Builder<StateRewardCommand> builder=reward1.builder();
            for (StateRewardCommand command: reward1.commands){
                builder.addCommand(
                        new StateRewardCommand(
                                BoolExprs.And(
                                        command.guard,
                                        IntExprs.Eq(
                                                _initIndicator.getRef(),
                                                IntLitExpr.of(1)
                                        )
                                ),
                                command.reward
                        )
                );
            }
            builder.addCommand(new StateRewardCommand(IntExprs.Eq(_initIndicator.getRef(),IntLitExpr.of(0)),RealLitExpr.of(0.0)));
            return builder.build();
        }
        throw new UnsupportedOperationException();
    }

    private Valuation generateValuation(Valuation valuation){
        ImmutableValuation.Builder builder= ImmutableValuation.builder();
        for(Map.Entry<Decl<?>, LitExpr<?>> entry:valuation.toMap().entrySet()){
            builder.put(entry.getKey(),entry.getValue());
        }
        builder.put(initIndicator, IntLitExpr.of(0));
        return builder.build();
    }

    private Collection<ContinousCommand> generateContCommads(Collection<Valuation> initialStates){
        Collection<ContinousCommand> commands=new HashSet<>();
        for (Valuation state:initialStates){
            commands.add(generateContinousCommand(state));
        }
        return commands;
    }

    private Collection<DiscreteCommand> generateDiscCommads(Collection<Valuation> initialStates){
        Collection<DiscreteCommand> commands=new HashSet<>();
        for (Valuation state:initialStates){
            commands.add(generateDisceteCommand(state));
        }
        return commands;
    }

    private ContinousCommand generateContinousCommand(Valuation initialState){
        ContinousCommand.Builder cbuilder=new ContinousCommand.Builder();
        cbuilder.setAction((ID++).toString());
        cbuilder.setGuard(IntExprs.Eq(_initIndicator.getRef(),IntExprs.Int(0)));
        Collection<ContinuousUpdate> updates=new HashSet<>();
        ContinuousUpdate.Builder ubuilder=ContinuousUpdate.builder();

        ubuilder.setRate(RealLitExpr.of(1.0));
        for(Map.Entry<Decl<?>, LitExpr<?>> entry:initialState.toMap().entrySet()){
            ubuilder.addStmt(AssignStmt.create((VarDecl<?>) entry.getKey(),entry.getValue()));
        }
        ubuilder.addStmt(AssignStmt.create(_initIndicator,IntLitExpr.of(1)));

        cbuilder.addUpdate(ubuilder.build());

        return cbuilder.build();
    }

    private DiscreteCommand generateDisceteCommand(Valuation initialState /*a valuation describe an initial state*/){
        DiscreteCommand.Builder cbuilder=new DiscreteCommand.Builder();
        cbuilder.setAction((ID++).toString());
        cbuilder.setGuard(IntExprs.Eq(_initIndicator.getRef(),IntExprs.Int(0)));
        Collection<DiscreteUpdate> updates=new HashSet<>();
        /* one command describes only one initial state, it has only one update*/
        DiscreteUpdate.Builder ubuilder=DiscreteUpdate.builder();

        ubuilder.setProbability(RealLitExpr.of(1.0));
        for(Map.Entry<Decl<?>, LitExpr<?>> entry:initialState.toMap().entrySet()){
            ubuilder.addStmt(AssignStmt.create((VarDecl<?>) entry.getKey(),entry.getValue()));
        }
        ubuilder.addStmt(AssignStmt.create(_initIndicator,IntLitExpr.of(1)));

        cbuilder.addUpdate(ubuilder.build());

        return cbuilder.build();
    }

    private Collection<ContinousCommand> modifyContCommands(Collection<ContinousCommand> commands){
        Collection<ContinousCommand> commands1=new HashSet<>();
        for (ContinousCommand command:commands){
            ContinousCommand.Builder builder=new ContinousCommand.Builder();
            builder.addUpdates(command.updates);
            builder.setAction(command.action);
            builder.setGuard(BoolExprs.And(
                    command.guard,
                    IntExprs.Eq(
                            _initIndicator.getRef(),
                            IntLitExpr.of(1)
                    )
            ));
            commands1.add(builder.build());
        }
        return commands1;
    }

    private Collection<DiscreteCommand> modifyDiscCommands(Collection<DiscreteCommand> commands){
        Collection<DiscreteCommand> commands1=new HashSet<>();
        for (DiscreteCommand command:commands){
            DiscreteCommand.Builder builder=new DiscreteCommand.Builder();
            builder.addUpdates(command.updates);
            builder.setAction(command.action);
            builder.setGuard(BoolExprs.And(
                    command.guard,
                    IntExprs.Eq(
                            _initIndicator.getRef(),
                            IntLitExpr.of(1)
                    )
            ));
            commands1.add(builder.build());
        }
        return commands1;
    }

}
