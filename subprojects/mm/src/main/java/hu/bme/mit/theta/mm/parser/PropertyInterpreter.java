package hu.bme.mit.theta.mm.parser;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import hu.bme.mit.theta.common.parser.SExpr;
import hu.bme.mit.theta.core.decl.ConstDecl;
import hu.bme.mit.theta.core.parser.CoreInterpreter;
import hu.bme.mit.theta.core.parser.Env;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.arithmetic.OperatorArithmetic;
import hu.bme.mit.theta.mm.prop.MultiObjective;
import hu.bme.mit.theta.mm.prop.Objective;
import hu.bme.mit.theta.mm.prop.Property;

import java.util.List;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static hu.bme.mit.theta.common.Utils.head;
import static hu.bme.mit.theta.common.Utils.tail;
import static hu.bme.mit.theta.core.decl.Decls.Const;

public class PropertyInterpreter{


    private final Env env;
    private final CoreInterpreter interpreter;
    private final Property.Builder builder;


    public PropertyInterpreter(Env env){

        this.env = env;
        this.interpreter=new CoreInterpreter(env);
        initEnv();
        builder=Property.builder();
    }


    private void initEnv(){
        interpreter.defineCommonTypes();
        interpreter.defineCommonExprs();
        interpreter.defineCommonStmts();
        interpreter.defineTempLogicExprs();
        env.define("const",constantCreator());
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

//#todo: implement multi objective parsing

    private Function<List<SExpr>, Property> propertyCreator() {
        return sexprs -> {
            env.push();
            for (final SExpr sexpr : sexprs) {
                final Object object = eval(sexpr);
                if (object instanceof ConstantContext) {
                    final ConstantContext constantContext = (ConstantContext) object;
                    env.define(constantContext.constDecl.getName(),constantContext.constDecl);
                    builder.addConstant(constantContext.constDecl,constantContext.initialExpr);
                } else if (object instanceof ObjectiveContext) {
                    final ObjectiveContext objectiveContext=(ObjectiveContext) object;
                    Objective objective=new Objective(objectiveContext.operatorArithmetic, objectiveContext.name);
                    env.define(objectiveContext.name,objective);
                    builder.addObjective(objective);
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            env.pop();
            return builder.build();
        };
    }

    private Function<List<SExpr>, ConstantContext> constantCreator() {//todo add valuation
        return sexprs -> {
            checkArgument(sexprs.size() == 2);
            final String name = sexprs.get(0).asAtom().getAtom();
            final Type type = interpreter.type(sexprs.get(1));

            final ConstDecl<?> constDecl = Const(name, type);
            ConstantContext constantContext=new ConstantContext(constDecl,(LitExpr<?>) eval(sexprs.get(2).asAtom()));
            return constantContext;

        };
    }

    private Function<List<SExpr>,ObjectiveContext> objectiveCreator(){
        return sexprs -> {

            checkArgument(sexprs.size() == 2);
            final String name = sexprs.get(0).asAtom().getAtom();

            final OperatorArithmetic<?> operatorArithmetic=(OperatorArithmetic<?>) eval(sexprs.get(1));
            return new ObjectiveContext(name,operatorArithmetic);

        };
    }

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


}