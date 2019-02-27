package hu.bme.mit.theta.mm.generator;

import hu.bme.mit.theta.core.decl.ConstDecl;
import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.rangetype.RangeType;
import hu.bme.mit.theta.mm.model.*;
import hu.bme.mit.theta.mm.prop.Objective;
import hu.bme.mit.theta.mm.prop.Property;

import java.util.Collection;

public final class MMPRISMWriter {

    private MMPRISMWriter(){}

    public static MMPRISMWriter instance() {
        return LazyHolder.INSTANCE;
    }

    public String PDTMC2PRISM(ParametricDiscreteTimeMarkovChain pdtmc) {
        StringBuilder prismBuilder=new StringBuilder();

        prismBuilder.append("pCTMC")
                .append('\n')
                .append("//generated by Theta.MM.Generator \n")
                .append("module MM_pCTMC")
                .append('\n');

        //writing out variables
        for (VarDecl<?> varDecl:pdtmc.variables){
            prismBuilder.append(Variable2PRISM(varDecl,pdtmc.variableInitalisations.toMap().get(varDecl)))
                    .append("\n");
        }

        //todo: write out parameters?

        //writing out commands
        for (DiscreteCommand command:pdtmc.commands){
            prismBuilder.append(DiscreteCommand2PRISM(command))
                    .append('\n');
        }

        prismBuilder.append("endmodule");

        return prismBuilder.toString();

    }


    private static class LazyHolder {
        private static MMPRISMWriter INSTANCE = new MMPRISMWriter();
    }




    public String ContiniuousUpdate2PRISM(ContinuousUpdate continuousUpdate){
        StringBuilder prismBuilder=new StringBuilder();
        ExprPRISMWriter exprWriter=ExprPRISMWriter.instance();
        prismBuilder.append(" (")
                .append(exprWriter.write(continuousUpdate.rate.rateExpr))
                .append(") :");

        int i=1;
        for(AssignStmt<?> stmt:continuousUpdate.updateExpr){
            prismBuilder.append("(")
                    .append(stmt.getVarDecl().getName())
                    .append("'=")
                    .append(exprWriter.write(stmt.getExpr()))
                    .append(")");
            if(i<continuousUpdate.updateExpr.size()){
                prismBuilder.append("&");
            }
            i++;
        }
        //if there is no update expression write 'true'
        if (continuousUpdate.updateExpr.isEmpty()){
            prismBuilder.append(" true ");
        }

        return prismBuilder.toString();

    }



    public String DiscreteUpdate2PRISM(DiscreteUpdate discreteUpdate){
        StringBuilder prismBuilder=new StringBuilder();
        ExprPRISMWriter exprWriter=ExprPRISMWriter.instance();
        prismBuilder.append(" (")
                .append(exprWriter.write(discreteUpdate.probability.probExpr))
                .append(") :");

        int i=1;
        for(AssignStmt<?> stmt:discreteUpdate.updateExpr){
            prismBuilder.append("(")
                    .append(stmt.getVarDecl().getName())
                    .append("'=")
                    .append(exprWriter.write(stmt.getExpr()))
                    .append(")");
            if(i<discreteUpdate.updateExpr.size()){
                prismBuilder.append("&");
            }
            i++;
        }
        //if there is no update expression write 'true'
        if (discreteUpdate.updateExpr.isEmpty()){
            prismBuilder.append(" true ");
        }

        return prismBuilder.toString();

    }

    public String ContiniuousCommand2PRISM(ContinousCommand continousCommand){

        StringBuilder prismBuilder=new StringBuilder();
        ExprPRISMWriter exprWriter=ExprPRISMWriter.instance();
        prismBuilder.append("[ ")
                .append(continousCommand.action)
                .append(" ] ")
                .append(exprWriter.write(continousCommand.guard))
                .append(" -> ");

        int i=1;
        for (ContinuousUpdate continuousUpdate:continousCommand.updates){
            prismBuilder.append(' ')
                    .append(ContiniuousUpdate2PRISM(continuousUpdate));

            if (i<continousCommand.updates.size()){
                prismBuilder.append(" + ");
            }
            i++;
        }

        prismBuilder.append(" ;");

        return prismBuilder.toString();

    }


    public String DiscreteCommand2PRISM(DiscreteCommand discreteCommand){

        StringBuilder prismBuilder=new StringBuilder();
        ExprPRISMWriter exprWriter=ExprPRISMWriter.instance();
        prismBuilder.append("[ ")
                .append(discreteCommand.action)
                .append(" ] ")
                .append(exprWriter.write(discreteCommand.guard))
                .append(" -> ");

        int i=1;
        for (DiscreteUpdate discreteUpdate:discreteCommand.updates){
            prismBuilder.append(' ')
                    .append(DiscreteUpdate2PRISM(discreteUpdate));

            if (i<discreteCommand.updates.size()){
                prismBuilder.append(" + ");
            }
            i++;
        }

        prismBuilder.append(" ;");

        return prismBuilder.toString();

    }

    public String Variable2PRISM(VarDecl<?> varDecl, LitExpr<?> initalValue){

        ExprPRISMWriter exprWriter=ExprPRISMWriter.instance();
        StringBuilder prismBuilder=new StringBuilder();


        if(varDecl.getType() instanceof RangeType){
            VarDecl<RangeType> var=(VarDecl<RangeType>) varDecl;
            prismBuilder.append(varDecl.getName())
                    .append(": [")
                    .append(var.getType().getLower())
                    .append("..")
                    .append(var.getType().getUpper())
                    .append("] init ")
                    .append(exprWriter.write(initalValue))
                    .append(" ;");
        }else {
            throw new UnsupportedOperationException("Only ranged int types are supported yet. :'(");
        }

        return prismBuilder.toString();

    }


    public String Constant2PRISM(ConstDecl<?> constDecl, LitExpr<?> initalValue){

        ExprPRISMWriter exprWriter=ExprPRISMWriter.instance();
        StringBuilder prismBuilder=new StringBuilder("const ");


        prismBuilder
                .append(exprWriter.write(constDecl.getType()))
                .append(" ")
                .append(constDecl.getName())
                .append(" = ")
                .append(exprWriter.write(initalValue))
                .append(" ;");


        return prismBuilder.toString();
    }



    public String PCTMC2PRISM(ParametricContinousTimeMarkovChain pCTMC){
        StringBuilder prismBuilder=new StringBuilder();

        prismBuilder.append("pCTMC")
                .append('\n')
                .append("//generated by Theta.MM.Generator \n")
                .append("module MM_pCTMC")
                .append('\n');

        //writing out variables
        for (VarDecl<?> varDecl:pCTMC.variables){
            prismBuilder.append(Variable2PRISM(varDecl,pCTMC.variableInitalisations.toMap().get(varDecl)))
                    .append("\n");
        }

        //todo: write out parameters?

        //writing out commands
        for (ContinousCommand continousCommand:pCTMC.commands){
            prismBuilder.append(ContiniuousCommand2PRISM(continousCommand))
                    .append('\n');
        }

        prismBuilder.append("endmodule");

        return prismBuilder.toString();

    }



    public String DTMDP2PRISM(DiscreteTimeMarkovDecisionProcess dtmdp){
        StringBuilder prismBuilder=new StringBuilder();

        prismBuilder.append("mdp")
                .append('\n')
                .append("//generated by Theta.MM.Generator \n")
                .append("module MM_DTMDP")
                .append('\n');

        //writing out variables
        for (VarDecl<?> varDecl:dtmdp.variables){
            prismBuilder.append(Variable2PRISM(varDecl,dtmdp.variableInitalisations.toMap().get(varDecl)))
                    .append("\n");
        }

        //todo: write out parameters?

        //writing out commands
        for (DiscreteCommand discreteCommand:dtmdp.commands){
            prismBuilder.append(DiscreteCommand2PRISM(discreteCommand))
                    .append('\n');
        }

        prismBuilder.append("endmodule");

        return prismBuilder.toString();

    }


    //this implementation does not match to the prism specifications
    public String CTMDP2PRISM(ContinuousTimeMarkovDecisionProcess ctmdp){
        StringBuilder prismBuilder=new StringBuilder();

        prismBuilder.append("mdp")
                .append('\n')
                .append("//generated by Theta.MM.Generator \n")
                .append("module MM_CTMDP")
                .append('\n');

        //writing out variables
        for (VarDecl<?> varDecl:ctmdp.variables){
            prismBuilder.append(Variable2PRISM(varDecl,ctmdp.variableInitalisations.toMap().get(varDecl)))
                    .append("\n");
        }

        //todo: write out parameters?

        //writing out commands
        for (ContinousCommand continousCommand:ctmdp.commands){
            prismBuilder.append(ContiniuousCommand2PRISM(continousCommand))
                    .append('\n');
        }

        prismBuilder.append("endmodule");

        return prismBuilder.toString();

    }


    public String MarkovianModel2PRISM(MarkovianModel markovianModel){
        if(markovianModel instanceof DiscreteTimeMarkovDecisionProcess) {
            return DTMDP2PRISM((DiscreteTimeMarkovDecisionProcess) markovianModel);
        } else if (markovianModel instanceof ParametricContinousTimeMarkovChain) {
            return PCTMC2PRISM((ParametricContinousTimeMarkovChain) markovianModel);
        } else {
            throw new UnsupportedOperationException("This kind of Markovian model is not supported for yet.");
        }
    }


    public String Property2PRISM(Property property){
        StringBuilder prismBuilder=new StringBuilder();
        ExprPRISMWriter exprPRISMWriter=ExprPRISMWriter.instance();

        prismBuilder
                .append("//generated by Theta.MM.Generator \n")
                .append('\n');

        //writing out constants
        for (ConstDecl<?> constDecl:property.constans){
            prismBuilder.append(Constant2PRISM(constDecl,property.constInitialisations.toMap().get(constDecl)))
                    .append("\n");
        }

        //todo: write out multiobjectives

        //writing out commands
        for (Objective objective:property.objectives){
            prismBuilder.append(" \"")
                    .append(objective.name)
                    .append("\" : ")
                    .append(exprPRISMWriter.write(objective.operatorArithmetic))
                    .append('\n');
        }


        return prismBuilder.toString();
    }

    //writing out rewards


    public String StateRewardCommand2PRISM(StateRewardCommand command){

        StringBuilder prismBuilder=new StringBuilder();
        ExprPRISMWriter exprWriter=ExprPRISMWriter.instance();
        prismBuilder.append(exprWriter.write(command.guard))
                .append(" : ")
                .append(exprWriter.write(command.reward))
                .append(" ;");

        return prismBuilder.toString();

    }


    public String TransitionRewardCommand2PRISM(TranstionRewardCommand command){

        StringBuilder prismBuilder=new StringBuilder();
        ExprPRISMWriter exprWriter=ExprPRISMWriter.instance();
        prismBuilder.append("[")
                .append(command.name)
                .append("] ")
                .append(exprWriter.write(command.guard))
                .append(" : ")
                .append(exprWriter.write(command.reward))
                .append(" ;");

        return prismBuilder.toString();

    }

    public String RewardCommand2PRISM(RewardCommand command){
        if (command instanceof StateRewardCommand){
            return StateRewardCommand2PRISM((StateRewardCommand) command);
        } else if (command instanceof TranstionRewardCommand){
            return TransitionRewardCommand2PRISM((TranstionRewardCommand) command);
        } else {
            throw new UnsupportedOperationException("Writer can not recognise this kind of reward command.");
        }

    }


    public String Reward2PRISM(Reward<?> reward){

        StringBuilder prismBuilder=new StringBuilder();
        ExprPRISMWriter exprWriter=ExprPRISMWriter.instance();
        prismBuilder.append("rewards ");

        if (reward.name != null ){
            prismBuilder.append("\"").append(reward.name).append("\" ");
        }

        prismBuilder.append("\n");


        for (RewardCommand command:reward.commands){
            prismBuilder.append(RewardCommand2PRISM(command))
                    .append("\n");


        }

        prismBuilder.append("endrewards");

        return prismBuilder.toString();

    }


    public String MMwithRewards2PRISM(MarkovianModel markovianModel, Collection<Reward<?>> rewards){
        StringBuilder prismBuilder=new StringBuilder();
        prismBuilder.append(MarkovianModel2PRISM(markovianModel))
                .append('\n');
        for (Reward reward:rewards){
            prismBuilder.append(Reward2PRISM(reward))
                    .append('\n');
        }
        return prismBuilder.toString();
    }



    public String ParameterSpace2PRISM(ParameterSpace parameterSpace){
        StringBuilder psBuilder=new StringBuilder();
        ExprPRISMWriter exprPRISMWriter=ExprPRISMWriter.instance();

        psBuilder.append("parameterspace\n");

        for (ParamDecl<?> param:parameterSpace.parameters){
            psBuilder.append(param.getName())
                    .append(" ")
                    .append(exprPRISMWriter.write(param.getType()))
                    .append(" [")
                    .append(exprPRISMWriter.write(parameterSpace.getLimit(param,ParameterDirection.LOW)))
                    .append("..")
                    .append(exprPRISMWriter.write(parameterSpace.getLimit(param,ParameterDirection.UP)))
                    .append("];\n");
        }

        psBuilder.append("endparameterspace");

        return psBuilder.toString();

    }

}
