package hu.bme.mit.theta.mm.parser;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import hu.bme.mit.theta.common.parser.SExpr;
import hu.bme.mit.theta.core.decl.ConstDecl;
import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.parser.CoreInterpreter;
import hu.bme.mit.theta.core.parser.Env;
import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.arithmetic.OperatorArithmetic;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.inttype.IntLitExpr;
import hu.bme.mit.theta.core.type.inttype.IntType;
import hu.bme.mit.theta.core.type.rangetype.RangeType;
import hu.bme.mit.theta.core.type.realtype.RealExprs;
import hu.bme.mit.theta.core.type.realtype.RealLitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.model.*;
import hu.bme.mit.theta.mm.prop.MultiObjective;
import hu.bme.mit.theta.mm.prop.Objective;
import hu.bme.mit.theta.mm.prop.Property;

import java.util.List;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static hu.bme.mit.theta.common.Utils.head;
import static hu.bme.mit.theta.common.Utils.tail;
import static hu.bme.mit.theta.core.decl.Decls.*;

public class MarkovianModelInterpreter {


    private final Env env;
    private final CoreInterpreter interpreter;
    private  final ParametricContinousTimeMarkovChain.Builder pCTMCBuilder;
    private final DiscreteTimeMarkovDecisionProcess.Builder MDPBuilder;
    private final ParameterSpace.Builder paramBuilder;
    private ParameterSpace parameterSpace;

    private final Property.Builder propertyBuilder;

    public MarkovianModelInterpreter(Env env){

        this.env = env;
        this.interpreter=new CoreInterpreter(env);
        initEnv();
        pCTMCBuilder = ParametricContinousTimeMarkovChain.builder();
        MDPBuilder = DiscreteTimeMarkovDecisionProcess.builder();
        paramBuilder=ParameterSpace.builder();
        propertyBuilder =Property.builder();

    }

    public ParametricContinousTimeMarkovChain parametricContiniuousTimeMarkovChain(SExpr sExpr){
        return (ParametricContinousTimeMarkovChain) eval(sExpr);
    }

    public DiscreteTimeMarkovDecisionProcess discreteTimeMarkovDecisionProcess(SExpr sExpr){
        return (DiscreteTimeMarkovDecisionProcess) eval(sExpr);
    }

    private void initEnv(){
        interpreter.defineCommonTypes();
        interpreter.defineCommonExprs();
        interpreter.defineCommonStmts();
        env.define("pctmc", pCTMCCreator());
        env.define("dtmdp", DTMDPCreator());
        env.define("var",variableCreator());
        env.define("command", continuousCommandCreator());
        env.define("dcommand",discreteCommandCreator());
        env.define("update", continuousUpdateCreator());
        env.define("dupdate",discreteUpdateCreator());
        env.define("param",parameterCreator());
        /*
        interpreter.defineTempLogicExprs();
        env.define("const",constantCreator());
        env.define("property",propertyCreator());
        env.define("objective",objectiveCreator());
        */
    }


    private Object eval(final SExpr sexpr) {
        if (sexpr.isAtom()) {
            return evalAtom(sexpr.asAtom());
        } else if (sexpr.isList()) {
            return evalList(sexpr.asList());
        } else {
            throw new AssertionError();
        }
    }

    private Object evalAtom(final SExpr.SAtom satom) {
        final String symbol = satom.getAtom();
        final Integer integer = Ints.tryParse(symbol);
        final Double real = Doubles.tryParse(symbol);
        if (integer != null) {
            return integer;
        } else if (real != null){
            return real;
        }else {
            final Object value = env.eval(symbol);
            return value;
        }
    }

    private Object evalList(final SExpr.SList slist) {
        final List<SExpr> list = slist.getList();
        final SExpr head = head(list);
        final List<SExpr> tail = tail(list);
        if (head.isAtom()) {
            final String symbol = head.asAtom().getAtom();
            final Object object = env.eval(symbol);
            @SuppressWarnings("unchecked")
            final Function<List<SExpr>, ?> interpretation = (Function<List<SExpr>, ?>) object;
            final Object value = interpretation.apply(tail);
            return value;
        } else if (head.isList()) {
            throw new UnsupportedOperationException();
        } else {
            throw new AssertionError();
        }
    }


    private Function<List<SExpr>, ParametricContinousTimeMarkovChain> pCTMCCreator() {
        return sexprs -> {
            env.push();
            for (final SExpr sexpr : sexprs) {
                final Object object = eval(sexpr);
                if (object instanceof VariableContext) {
                    final VariableContext variableContext = (VariableContext) object;
                    if (variableContext.varDecl.getType().toString().contains("Range")){
                        pCTMCBuilder.createVariable(variableContext.varDecl,variableContext.initialExpr);
                    } else if (variableContext.varDecl.getType().equals(BoolType.getInstance())) {
                        pCTMCBuilder.createVariable(variableContext.varDecl,variableContext.initialExpr);
                    } else {
                        throw new UnsupportedOperationException();
                    }
                } else if (object instanceof ContinouosCommandContext) {
                    final ContinousCommand command= pCTMCBuilder.createCommand(((ContinouosCommandContext) object).builder);
                    env.define(command.action, command);
                } else if (object instanceof ParameterContext) {
                    ParameterContext parameterContext=((ParameterContext) object);
                    env.define(parameterContext.paramDecl.getName(),parameterContext.paramDecl);
                    paramBuilder.addParameter(parameterContext.paramDecl,parameterContext.lowerLimit,parameterContext.upperLimit);
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            env.pop();
            parameterSpace=paramBuilder.build();
            return pCTMCBuilder.build();
        };
    }

    private Function<List<SExpr>, DiscreteTimeMarkovDecisionProcess> DTMDPCreator() {
        return sexprs -> {
            env.push();
            for (final SExpr sexpr : sexprs) {
                final Object object = eval(sexpr);
                if (object instanceof VariableContext) {
                    final VariableContext variableContext = (VariableContext) object;
                    if (variableContext.varDecl.getType().toString().contains("Range")){
                        MDPBuilder.createVariable(variableContext.varDecl,variableContext.initialExpr);
                    } else if (variableContext.varDecl.getType().equals(BoolType.getInstance())) {
                        MDPBuilder.createVariable(variableContext.varDecl,variableContext.initialExpr);
                    } else {
                        throw new UnsupportedOperationException();
                    }
                } else if (object instanceof DiscreteCommandContext) {
                    final DiscreteCommand command= MDPBuilder.createCommand(((DiscreteCommandContext) object).builder);
                    env.define(command.action, command);
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            env.pop();
            return MDPBuilder.build();
        };
    }

    public ParameterSpace getParameterSpace(){
        return parameterSpace;
    }



    private Function<List<SExpr>, ContinuousUpdateContext> continuousUpdateCreator() {
        return sexprs -> {
            checkArgument(sexprs.size() == 2);
            ContinuousUpdate.Builder builder=new ContinuousUpdate.Builder();
            Object object=eval(sexprs.get(0));
            if (object instanceof Expr){
                builder.setRate((Expr<RealType>) object);
            } else if(object instanceof Double) {
                builder.setRate(RealLitExpr.of((Double) object));
            } else {
                throw new UnsupportedOperationException();
            }

            List<SExpr> stmtSExprs = List.copyOf(sexprs.subList(1,sexprs.size()));
            //env.push();
            for(final SExpr sexpr :stmtSExprs){
                final Object objecti = eval(sexpr);
                final AssignStmt stmt = (AssignStmt) objecti;
                builder.addStmt(stmt);
            }
            //env.pop();
            return new ContinuousUpdateContext(builder);
        };
    }


    private Function<List<SExpr>, DiscreteUpdateContext> discreteUpdateCreator() {
        return sexprs -> {
            checkArgument(sexprs.size() == 2);
            DiscreteUpdate.Builder builder= DiscreteUpdate.builder();
            Object object=eval(sexprs.get(0));
            if (object instanceof Expr){
                builder.setProbability((Expr<RealType>) object);
            } else if(object instanceof Double) {
                builder.setProbability(RealLitExpr.of((Double) object));
            } else if (object instanceof Integer) {
                builder.setProbability(RealLitExpr.of((Integer) object));
            } else {
                throw new UnsupportedOperationException();
            }

            List<SExpr> stmtSExprs = List.copyOf(sexprs.subList(1,sexprs.size()));
            //env.push();
            for(final SExpr sexpr :stmtSExprs){
                final Object objecti = eval(sexpr);
                final AssignStmt stmt = (AssignStmt) objecti;
                builder.addStmt(stmt);
            }
            //env.pop();
            return new DiscreteUpdateContext(builder);
        };
    }


    private Function<List<SExpr>, ContinouosCommandContext> continuousCommandCreator() {
        return sexprs -> {
            checkArgument(sexprs.size() > 2);
            ContinousCommand.Builder builder=new ContinousCommand.Builder();
            builder.setAction(sexprs.get(0).asAtom().getAtom());
            builder.setGuard((Expr<BoolType>) eval(sexprs.get(1)));
            List<SExpr> updateSExprs=List.copyOf(sexprs.subList(2,sexprs.size()));
            env.push();
            for(final SExpr sexpr :updateSExprs){
                final Object object = eval(sexpr);
                final ContinuousUpdateContext update = (ContinuousUpdateContext) object;
                builder.addUpdate(update.builder.build());
            }
            env.pop();
            return new ContinouosCommandContext(builder);
        };
    }

    private Function<List<SExpr>, DiscreteCommandContext> discreteCommandCreator() {
        return sexprs -> {
            checkArgument(sexprs.size() > 2);
            DiscreteCommand.Builder builder= DiscreteCommand.builder();
            builder.setAction(sexprs.get(0).asAtom().getAtom());
            builder.setGuard((Expr<BoolType>) eval(sexprs.get(1)));
            List<SExpr> updateSExprs=List.copyOf(sexprs.subList(2,sexprs.size()));
            env.push();
            for(final SExpr sexpr :updateSExprs){
                final Object object = eval(sexpr);
                final DiscreteUpdateContext update = (DiscreteUpdateContext) object;
                builder.addUpdate(update.builder.build());
            }
            env.pop();
            return new DiscreteCommandContext(builder);
        };
    }

    private Function<List<SExpr>, VariableContext> variableCreator() {//todo add valuation
        return sexprs -> {
            checkArgument(sexprs.size() >= 2);
            final String name = sexprs.get(0).asAtom().getAtom();
            final Type type = interpreter.type(sexprs.get(1));
            if(type.equals(IntType.getInstance())){ //todo: supervising needed
                final RangeType _type = RangeType.Range((int) evalAtom(sexprs.get(2).asAtom()),(int) evalAtom(sexprs.get(3).asAtom()));
                final VarDecl<RangeType> varDecl = Var(name,_type);
                VariableContext variableContext=new VariableContext(varDecl,IntLitExpr.of((Integer) eval(sexprs.get(4).asAtom()))  );
                env.define(name,Var(name,IntType.getInstance()));
                //todo: supervising needed (Más típussal deklarálom az environment-ben, hogy tisztán int-ként lehessen kezelni és a RangeType-ban ne kelljen a műveleteket újra deffiniálni)
                return variableContext;
            }else{
                final VarDecl<?> varDecl = Var(name, type);
                VariableContext variableContext=new VariableContext(varDecl,(LitExpr<?>) eval(sexprs.get(2).asAtom()));
                return variableContext;
            }
        };
    }

    private Function<List<SExpr>, ParameterContext> parameterCreator() {
        return sexprs -> {
            checkArgument(sexprs.size() == 3);
            final String name = sexprs.get(0).asAtom().getAtom();
            final Type type = RealType.getInstance();
            ParamDecl<?> paramDecl=Param(name,type);
            LitExpr<?> lowerLimit= RealExprs.Real((Double) eval(sexprs.get(1)));
            LitExpr<?> upperLimit= RealExprs.Real((Double) eval(sexprs.get(2)));
            return new ParameterContext(paramDecl,lowerLimit,upperLimit);
        };
    }










//#todo: implement multi objective parsing
/*
    private Function<List<SExpr>, Property> propertyCreator() {
        return sexprs -> {
            env.push();
            for (final SExpr sexpr : sexprs) {
                final Object object = eval(sexpr);
                if (object instanceof ConstantContext) {
                    final ConstantContext constantContext = (ConstantContext) object;
                    env.define(constantContext.constDecl.getName(),constantContext.constDecl);
                    propertyBuilder.addConstant(constantContext.constDecl,constantContext.initialExpr);
                } else if (object instanceof ObjectiveContext) {
                    final ObjectiveContext objectiveContext=(ObjectiveContext) object;
                    Objective objective=new Objective(objectiveContext.operatorArithmetic, objectiveContext.name);
                    env.define(objectiveContext.name,objective);
                    propertyBuilder.addObjective(objective);
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            env.pop();
            return propertyBuilder.build();
        };
    }

    private Function<List<SExpr>, ConstantContext> constantCreator() {//todo add valuation
        return sexprs -> {
            checkArgument(sexprs.size() == 3);
            final String name = sexprs.get(0).asAtom().getAtom();
            final Type type = interpreter.type(sexprs.get(1));

            final ConstDecl<?> constDecl = Const(name, type);
            ConstantContext constantContext=new ConstantContext(constDecl,(LitExpr<?>) eval(sexprs.get(2).asAtom()));
            return constantContext;

        };
    }

    private Function<List<SExpr>, ObjectiveContext> objectiveCreator(){
        return sexprs -> {

            checkArgument(sexprs.size() == 2);
            final String name = sexprs.get(0).asAtom().getAtom();

            final OperatorArithmetic<?> operatorArithmetic=(OperatorArithmetic<?>) eval(sexprs.get(1));
            return new ObjectiveContext(name,operatorArithmetic);

        };
    }



*/

    private final class  VariableContext{
        public final VarDecl<?> varDecl;
        public final LitExpr<?> initialExpr;

        private VariableContext(VarDecl<?> varDecl, LitExpr<?> initialExpr) {
            this.varDecl = varDecl;
            this.initialExpr = initialExpr;
        }
    }

    private final class ParameterContext{

        public final ParamDecl<?> paramDecl;
        public final LitExpr<?> lowerLimit;
        public final LitExpr<?> upperLimit;

        private ParameterContext(ParamDecl<?> paramDecl, LitExpr<?> lowerLimit, LitExpr<?> upperLimit) {
            this.paramDecl = paramDecl;
            this.lowerLimit = lowerLimit;
            this.upperLimit = upperLimit;
        }
    }

    private final class ContinuousUpdateContext {
        public final ContinuousUpdate.Builder builder;

        private ContinuousUpdateContext(ContinuousUpdate.Builder builder) {
            this.builder = builder;
        }
    }

    private final class DiscreteUpdateContext {
        public final DiscreteUpdate.Builder builder;

        private DiscreteUpdateContext(DiscreteUpdate.Builder builder) {
            this.builder = builder;
        }
    }

    private final class ContinouosCommandContext {
        public final ContinousCommand.Builder builder;

        private ContinouosCommandContext(ContinousCommand.Builder builder) {
            this.builder = builder;
        }
    }

    private final class DiscreteCommandContext {
        public final DiscreteCommand.Builder builder;

        private DiscreteCommandContext(DiscreteCommand.Builder builder) {
            this.builder = builder;
        }
    }




/*

    private final class ConstantContext {
        public final ConstDecl<?> constDecl;
        public final LitExpr<?> initialExpr;

        private ConstantContext(ConstDecl<?> constDecl, LitExpr<?> initialExpr) {
            this.constDecl = constDecl;
            this.initialExpr = initialExpr;
        }
    }

    private final class ObjectiveContext {
        public final String name;
        public final OperatorArithmetic operatorArithmetic;

        public ObjectiveContext(String name, OperatorArithmetic operatorArithmetic) {
            this.name = name;
            this.operatorArithmetic = operatorArithmetic;
        }
    }

    private final class MultiobjectiveContext {
        public final MultiObjective.Builder builder;

        private MultiobjectiveContext(MultiObjective.Builder builder) {
            this.builder = builder;
        }
    }

*/


}
