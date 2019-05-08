package hu.bme.mit.theta.mm.analysis.solver.uniform;

import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.realtype.RealExprs;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.model.*;

import java.util.Collection;
import java.util.HashSet;

public class Uniformisation {
    private static Uniformisation ourInstance = new Uniformisation();

    public static Uniformisation getInstance() {
        return ourInstance;
    }

    private Uniformisation() {
    }

    public ParametricContinousTimeMarkovChain uniformisate(ParametricContinousTimeMarkovChain pctmc, Rate uniformRate){
        ParametricContinousTimeMarkovChain.Builder modelBuilder=ParametricContinousTimeMarkovChain.builder();
        modelBuilder.addVarBounds(pctmc.lowerVarBound,pctmc.upperVarBound);
        for (VarDecl varDecl:pctmc.variables){
            modelBuilder.createVariable(varDecl, (LitExpr<?>) pctmc.variableInitalisations.eval(varDecl).get());
        }

        for (ContinousCommand command:pctmc.commands){
            Collection<Expr<RealType>> rateSumColl=new HashSet<>();
            ContinousCommand.Builder commandBuilder=new ContinousCommand.Builder();
            commandBuilder.setAction(command.action);
            commandBuilder.setGuard(command.guard);
            for (ContinuousUpdate update:command.updates){
                rateSumColl.add(update.rate.rateExpr);
                commandBuilder.addUpdate(update);
            }
            Expr<RealType> rateSum=RealExprs.Add(rateSumColl);
            ContinuousUpdate.Builder updateBuilder=ContinuousUpdate.builder();
            updateBuilder.setRate(RealExprs.Sub(uniformRate.rateExpr,rateSum));
            commandBuilder.addUpdate(updateBuilder.build());
            modelBuilder.createCommand(commandBuilder);
        }

        return modelBuilder.build();
    }

}
