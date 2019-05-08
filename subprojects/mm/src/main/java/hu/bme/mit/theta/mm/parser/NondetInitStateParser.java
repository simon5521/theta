package hu.bme.mit.theta.mm.parser;

import hu.bme.mit.theta.common.parser.LispLexer;
import hu.bme.mit.theta.common.parser.LispParser;
import hu.bme.mit.theta.common.parser.SExpr;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.parser.Env;
import hu.bme.mit.theta.mm.model.MarkovianModel;

import java.io.Reader;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

public class NondetInitStateParser {

    private final LispParser lispParser;
    private final MarkovianModelInterpreter markovianModelInterpreter;

    public NondetInitStateParser(Reader reader, Collection<VarDecl<?>> variables){
        checkNotNull(reader);
        final LispLexer lispLexer=new LispLexer(reader);
        lispParser=new LispParser(lispLexer);
        final Env env=new Env();
        for(VarDecl<?> var:variables){
            env.define(var.getName(),var);
        }
        markovianModelInterpreter =new MarkovianModelInterpreter(env);
    }

    public Collection<Valuation> NondetInitialStates(){
        SExpr sExpr=lispParser.sexpr();
        Collection<Valuation> initStates=markovianModelInterpreter.multipleInitialStates(sExpr);
        return initStates;
    }
}
