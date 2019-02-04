package hu.bme.mit.theta.mm.parser;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import hu.bme.mit.theta.common.parser.SExpr;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.parser.CoreInterpreter;
import hu.bme.mit.theta.core.parser.Env;
import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.inttype.IntType;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.mm.dsl.Command;
import hu.bme.mit.theta.mm.dsl.MarkovianModel;
import hu.bme.mit.theta.mm.dsl.Update;
import hu.bme.mit.theta.xta.utils.RangeType;

import java.util.List;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static hu.bme.mit.theta.common.Utils.head;
import static hu.bme.mit.theta.common.Utils.tail;
import static hu.bme.mit.theta.core.decl.Decls.Var;

public class MarkovianModelInterpreter {


    private final Env env;
    private final CoreInterpreter interpreter;
    private  final MarkovianModel.Builder mmBuilder;

    public MarkovianModelInterpreter(Env env){

        this.env = env;
        this.interpreter=new CoreInterpreter(env);
        initEnv();
        mmBuilder= MarkovianModel.builder();


    }

    public MarkovianModel markovianModel(SExpr sExpr){
        return (MarkovianModel) eval(sExpr);
    }

    private void initEnv(){
        interpreter.defineCommonTypes();
        interpreter.defineCommonExprs();
        interpreter.defineCommonStmts();
        env.define("pctmc",markovianModelCreator(MarkovianModel.Type.ContinuousTimeParametricMarkovChain));
        env.define("var",variableCreator());
        env.define("command",commandCreator());
        env.define("update",updateCreator());
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


    private Function<List<SExpr>, MarkovianModel> markovianModelCreator(MarkovianModel.Type type) {
        return sexprs -> {
            env.push();
            mmBuilder.setType(type);
            for (final SExpr sexpr : sexprs) {
                final Object object = eval(sexpr);
                if (object instanceof VarDecl) {
                    final VarDecl<?> varDecl = (VarDecl<?>) object;
                    env.define(varDecl.getName(), varDecl);
                } else if (object instanceof CommandContext) {
                    final Command command=mmBuilder.createCommand(((CommandContext) object).builder);
                    env.define(command.action, command);
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            env.pop();
            return mmBuilder.build();
        };
    }

    /*
    todo: add argument check to creators
     */


    private Function<List<SExpr>, UpdateContext> updateCreator() {
        return sexprs -> {
            checkArgument(sexprs.size() > 2);
            Update.Builder builder=new Update.Builder();
            builder.setRate((Expr<RealType>) eval(sexprs.get(0).asAtom()));
            env.push();
            for(final SExpr sexpr :sexprs){
                final Object object = eval(sexpr);
                final AssignStmt stmt = (AssignStmt) object;
                builder.addStmt(stmt);
            }
            env.pop();
            return new UpdateContext(builder);
        };
    }


    private Function<List<SExpr>, CommandContext> commandCreator() {
        return sexprs -> {
            checkArgument(sexprs.size() > 2);
            Command.Builder builder=new Command.Builder();
            builder.setAction(sexprs.get(0).asAtom().getAtom());
            builder.setGuard((Expr<BoolType>) eval(sexprs.get(1).asAtom()));
            env.push();
            for(final SExpr sexpr :sexprs){
                final Object object = eval(sexpr);
                final UpdateContext update = (UpdateContext) object;
                builder.addUpdate(update.builder.build());
            }
            env.pop();
            return new CommandContext(builder);
        };
    }

    private Function<List<SExpr>, VariableContext> variableCreator() {//todo add valuation
        return sexprs -> {
            checkArgument(sexprs.size() >= 2);
            final String name = sexprs.get(0).asAtom().getAtom();
            final Type type = interpreter.type(sexprs.get(1));
            if(type.equals(IntType.class)){ //todo: supervising needed
                final RangeType _type = RangeType.Range((int) evalAtom(sexprs.get(2).asAtom()),(int) evalAtom(sexprs.get(3).asAtom()));
                final VarDecl<RangeType> varDecl = Var(name,_type);
                VariableContext variableContext=new VariableContext(varDecl,(LitExpr<RangeType>) eval(sexprs.get(4).asAtom()) );
                return variableContext;
            }else{
                final VarDecl<?> varDecl = Var(name, type);
                VariableContext variableContext=new VariableContext(varDecl,(LitExpr<?>) eval(sexprs.get(2).asAtom()));
                return variableContext;
            }
        };
    }

    private final class  VariableContext{
        public final VarDecl<?> varDecl;
        public final LitExpr<?> initialExpr;

        private VariableContext(VarDecl<?> varDecl, LitExpr<?> initialExpr) {
            this.varDecl = varDecl;
            this.initialExpr = initialExpr;
        }
    }

    private final class UpdateContext{
        public final Update.Builder builder;

        private UpdateContext(Update.Builder builder) {
            this.builder = builder;
        }
    }

    private final class CommandContext{
        public final Command.Builder builder;

        private CommandContext(Command.Builder builder) {
            this.builder = builder;
        }
    }


}
