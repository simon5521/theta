package hu.bme.mit.theta.mm.analysis.solver.discrete;

import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.type.realtype.RealExprs;
import hu.bme.mit.theta.mm.model.*;

public class Discretisation {
    private static Discretisation ourInstance = new Discretisation();

    public static Discretisation getInstance() {
        return ourInstance;
    }

    private Discretisation() {
    }


    public DiscreteTimeMarkovDecisionProcess discretisate(ContinuousTimeMarkovDecisionProcess ctmdp, Rate uniformRate){
        DiscreteTimeMarkovDecisionProcess.Builder builder=DiscreteTimeMarkovDecisionProcess.builder();

        for (VarDecl<?> varDecl:ctmdp.variables){
            builder.createVariable(varDecl,ctmdp.variableInitalisations.eval(varDecl).get());
        }

        for (ContinousCommand command:ctmdp.commands){
            builder.addCommand(discretisate(command,uniformRate));
        }

        return builder.build();
    }

    public DiscreteCommand discretisate(ContinousCommand continousCommand,Rate uniformRate){
        DiscreteCommand.Builder builder= new DiscreteCommand.Builder();
        builder.setGuard(continousCommand.guard);
        builder.setAction(continousCommand.action);

        for (ContinuousUpdate continuousUpdate: continousCommand.updates){
            builder.addUpdate(discretisate(continuousUpdate,uniformRate));
        }

        return builder.build();
    }

    public DiscreteUpdate discretisate(ContinuousUpdate continuousUpdate,Rate unformRate){
        DiscreteUpdate.Builder builder=DiscreteUpdate.builder();
        builder.addStmts(continuousUpdate.updateExpr);
        builder.setProbability(RealExprs.Div(continuousUpdate.rate.rateExpr,unformRate.rateExpr));
        return builder.build();
    }

}
