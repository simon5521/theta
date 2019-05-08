package hu.bme.mit.theta.mm.analysis.solver.relax;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.ImmutableValuation;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.mm.model.*;

import java.util.Collection;
import java.util.HashSet;

public class RelaxSubstitute {

    private final static RelaxSubstitute relaxSubstitute =new RelaxSubstitute();

    private RelaxSubstitute(){

    }

    public static RelaxSubstitute getInstance(){
        return relaxSubstitute;
    }


    public Collection<Valuation> relax(Command command, ParameterSpace parameterSpace){

        Collection<Valuation> valuations=new HashSet<>();

        Collection<ParamDecl<?>>  parameters=command.getParams();

        if (parameters.isEmpty()){
            valuations.add(ImmutableValuation.builder().build());
            return valuations;
        }

        int actionNumber=pow2(parameters.size());
        int parameterNumber=parameters.size();

        ParamDecl<?>[] paramArray=new ParamDecl[parameterNumber];
        parameters.toArray(paramArray);
        for (long directions=0;directions<actionNumber;directions++){
            ImmutableValuation.Builder builder=ImmutableValuation.builder();

            for (int paramCntr=0; paramCntr<parameterNumber;paramCntr++){
                long mask=1<<paramCntr;
                long dir= ((directions&mask)>>paramCntr);
                ParameterDirection parameterDirection;
                if (dir==0){
                    parameterDirection=ParameterDirection.LOW;
                } else if (dir==1) {
                    parameterDirection=ParameterDirection.UP;
                } else {
                    throw new UnsupportedOperationException("Error occured during the relaxation: can not set the direction.");
                }
                builder.put(paramArray[paramCntr],parameterSpace.getLimit(paramArray[paramCntr],parameterDirection));
            }

            valuations.add(builder.build());
        }
        return valuations;

    }


    public Collection<ContinousCommand> relaxsubstitute(ContinousCommand incommand, ParameterSpace parameterSpace){
        Collection<ContinousCommand> commands=new HashSet<>();
        Collection<Valuation> valuations=relax(incommand,parameterSpace);


        Integer cntr=0;
        for (Valuation valuation: valuations){
            ContinousCommand.Builder builder= new ContinousCommand.Builder();
            builder.setAction(incommand.action+"_"+cntr.toString());
            cntr++;

            builder.setGuard(incommand.guard);

            for (ContinuousUpdate update:incommand.updates){
                ContinuousUpdate newUpdate=new ContinuousUpdate(new Rate(update.getRate(valuation)),update.updateExpr);
                builder.addUpdate(newUpdate);
            }


            commands.add(builder.build());
        }

        return commands;
    }

    public Collection<DiscreteCommand> relaxsubstitute(DiscreteCommand incommand, ParameterSpace parameterSpace){
        Collection<DiscreteCommand> commands=new HashSet<>();
        Collection<Valuation> valuations=relax(incommand,parameterSpace);

        Integer nameCntr=0;
        for (Valuation valuation: valuations){
            DiscreteCommand.Builder builder= new DiscreteCommand.Builder();
            builder.setAction(incommand.action+"_"+nameCntr.toString());
            builder.setGuard(incommand.guard);
            nameCntr++;

            for (DiscreteUpdate update:incommand.updates){
                DiscreteUpdate newUpdate=new DiscreteUpdate(new Probability(update.getRate(valuation)),update.updateExpr);
                builder.addUpdate(newUpdate);
            }


            commands.add(builder.build());
        }

        return commands;
    }

    public ContinuousTimeMarkovDecisionProcess relaxsubstitute(ParametricContinousTimeMarkovChain pctmc, ParameterSpace parameterSpace){
        ContinuousTimeMarkovDecisionProcess.Builder builder=ContinuousTimeMarkovDecisionProcess.builder();

        for (VarDecl varDecl:pctmc.variables){
            builder.createVariable(varDecl, (LitExpr<?>) pctmc.variableInitalisations.eval(varDecl).get());
        }

        for (ContinousCommand command:pctmc.commands){
            builder.addCommands(relaxsubstitute(command,parameterSpace));
        }

        return builder.build();
    }



    public DiscreteTimeMarkovDecisionProcess relaxsubstitute(ParametricDiscreteTimeMarkovChain pdtmc, ParameterSpace parameterSpace){
        DiscreteTimeMarkovDecisionProcess.Builder builder=DiscreteTimeMarkovDecisionProcess.builder();

        for (VarDecl varDecl:pdtmc.variables){
            builder.createVariable(varDecl, (LitExpr<?>) pdtmc.variableInitalisations.eval(varDecl).get());
        }

        for (DiscreteCommand command:pdtmc.commands){
            builder.addCommands(relaxsubstitute(command,parameterSpace));
        }

        return builder.build();
    }


    public MarkovDecisionProcess<?> relaxandsubstitute(ParametricMarkovianModel pmm,ParameterSpace parameterSpace){
        if (pmm instanceof ParametricContinousTimeMarkovChain){
            return relaxsubstitute((ParametricContinousTimeMarkovChain) pmm,parameterSpace);
        } else if (pmm instanceof ParametricDiscreteTimeMarkovChain) {
            return relaxsubstitute((ParametricDiscreteTimeMarkovChain) pmm,parameterSpace);
        } else {
            throw new UnsupportedOperationException("This kind of model is not supported yet.");
        }
    }



    private int pow2(int a){
        int b=1;
        for (int i=0;i<a;i++){
            b*=2;
        }
        return b;
    }
}
